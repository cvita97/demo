package score.alarm.demo.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import score.alarm.demo.db.repositories.SquadRepository;
import score.alarm.demo.kafka.models.*;
import score.alarm.demo.mappers.impl.KafkaMongoMapper;
import score.alarm.demo.kafka.serdes.CustomSerdes;

import java.time.Duration;
import java.util.*;

@Configuration
public class KafkaStreamsConfig {

    @Value("${application.id}")
    private String appName;

    @Value("${player.topic.name}")
    private String playerTopic;

    @Value("${player.topic.partitions}")
    private int playerTopicPartitions;

    @Value("${player.topic.replication.factor}")
    private short playerTopicRepFactor;

    @Value("${team.topic.name}")
    private String teamTopic;

    @Value("${team.topic.partitions}")
    private int teamTopicPartitions;

    @Value("${team.topic.replication.factor}")
    private short teamTopicRepFactor;

    @Value("${squad.topic.name}")
    private String squadTopic;

    @Value("${squad.topic.partitions}")
    private int squadTopicPartitions;

    @Value("${squad.topic.replication.factor}")
    private short squadTopicRepFactor;

    @Value("${players.topic.name}")
    private String playersTopic;

    @Value("${players.topic.partitions}")
    private int playersTopicPartitions;

    @Value("${players.topic.replication.factor}")
    private short playersTopicRepFactor;

    @Value("${teams.topic.name}")
    private String teamsTopic;

    @Value("${teams.topic.partitions}")
    private int teamsTopicPartitions;

    @Value("${teams.topic.replication.factor}")
    private short teamsTopicRepFactor;

    @Value("${squads.topic.name}")
    private String squadsTopic;

    @Value("${squads.topic.partitions}")
    private int squadsTopicPartitions;

    @Value("${squads.topic.replication.factor}")
    private short squadsTopicRepFactor;

    @Value("${players.enriched.with.team.topic.name}")
    private String playersEnrichedWithTeamTopic;

    @Value("${players.enriched.with.team.topic.partitions}")
    private int playersEnrichedWithTeamPartitions;

    @Value("${players.enriched.with.team.topic.replication.factor}")
    private short playersEnrichedWithTeamRepFactor;

    @Value("${enriched.players.topic.name}")
    private String enrichedPlayersTopic;

    @Value("${enriched.players.topic.partitions}")
    private int enrichedPlayersPartitions;

    @Value("${enriched.players.topic.replication.factor}")
    private short enrichedPlayersRepFactor;

    private final SquadRepository squadRepository;
    private final KafkaMongoMapper mapper;

    @Autowired
    public KafkaStreamsConfig(SquadRepository squadRepository, KafkaMongoMapper mapper) {
        this.squadRepository = squadRepository;
        this.mapper = mapper;
    }

    @Bean
    @Primary
    public KafkaStreams kafkaStreams(KafkaProperties kafkaProperties) {
        Properties props = new Properties();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, appName);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, CustomSerdes.FullPlayerInfo().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // used for testing

        createTopics(kafkaProperties.getBootstrapServers());
        Topology topology = buildTopology(new StreamsBuilder());

        final KafkaStreams kafkaStreams = new KafkaStreams(topology, props);
        kafkaStreams.start();

        return kafkaStreams;
    }

    private Topology buildTopology(StreamsBuilder builder) {
        builder.stream(playerTopic, Consumed.with(Serdes.Long(), CustomSerdes.Player()))
                .selectKey((key, value) -> value.id)
                .groupByKey()
                .reduce((p1, p2) -> p1.version >= p2.version ? p1 : p2) // remove duplicates
                .toStream()
                .to(playersTopic); // sink to another topic with 1 partition

        builder.stream(teamTopic, Consumed.with(Serdes.Long(), CustomSerdes.Team()))
                .selectKey((key, value) -> value.id)
                .groupByKey()
                .reduce((t1, t2) -> t1.version >= t2.version ? t1 : t2) // remove duplicates
                .toStream()
                .to(teamsTopic); // sink to another topic with 1 partition

        builder.stream(squadTopic, Consumed.with(Serdes.Long(), CustomSerdes.Squad()))
                .selectKey((key, value) -> value.team_id)
                .groupByKey()
                .reduce((s1, s2) -> s1.version >= s2.version ? s1 : s2) // remove duplicates
                .toStream()
                .to(squadsTopic); // sink to another topic with 1 partition

        builder.stream(squadsTopic, Consumed.with(Serdes.Long(), CustomSerdes.Squad()))
                .flatMapValues(squad -> {  // break squad into players
                    squad.players.forEach(player -> player.team_id = squad.team_id);
                    return squad.players;
                })
                .selectKey((key, player) -> player.player_id)
                .to(enrichedPlayersTopic);


        builder.stream(enrichedPlayersTopic, Consumed.with(Serdes.Long(), CustomSerdes.FullPlayerInfo()))
                .leftJoin(builder.table(playersTopic, Consumed.with(Serdes.Long(), CustomSerdes.Player())), // enrich player with stats
                        (fullPlayerInfo, player) -> {
                            if (fullPlayerInfo.stats == null) {
                                fullPlayerInfo.stats = new ArrayList<>();
                            }
                            if (player != null && player.names != null) {
                                fullPlayerInfo.stats.addAll(player.names.stats);
                            }
                            return fullPlayerInfo;
                        })
                .selectKey((key, player) -> player.team_id)
                .groupByKey()
//                .windowedBy(TimeWindows.of(Duration.ofMillis(500))) // every 500 milliseconds emit aggregated players
                .aggregate(() -> new SquadTeamPlayer(new ShortTeamInfo(0L, null), new ArrayList<>()), // aggregate enriched players
                        (key, value, aggregate) -> {
                            aggregate.team.id = key;

                            if (!aggregate.players.contains(value)) {
                                aggregate.players.add(value);
                            }

                            return aggregate;
                        }, Materialized.with(Serdes.Long(), CustomSerdes.SquadTeamPlayer()))
//                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                .toStream()
                .to(playersEnrichedWithTeamTopic); // here we have all players with their full information

        builder.stream(playersEnrichedWithTeamTopic, Consumed.with(Serdes.Long(), CustomSerdes.SquadTeamPlayer()))
                .leftJoin(builder.table(teamsTopic, Consumed.with(Serdes.Long(), CustomSerdes.Team())), // combine aggregated players with their team
                        (squad, team) -> {
                            if (team != null && team.names != null && squad != null && squad.team != null) {
                                squad.team.stats = team.names.stats;
                            }
                            return squad;
                        })
                .foreach((k, v) -> {
//                    System.out.println(k + " -> " + v);
                    squadRepository.save(mapper.mapTo(v)); // save data to Mongo DB
                });

        return builder.build();
    }

    private void createTopics(List<String> bootstrapServers) {
        Map<String, Object> config = new HashMap<>();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        AdminClient client = AdminClient.create(config);

        List<NewTopic> topics = new ArrayList<>();

        topics.add(new NewTopic(playerTopic, playerTopicPartitions, playerTopicRepFactor));
        topics.add(new NewTopic(teamTopic, teamTopicPartitions, teamTopicRepFactor));
        topics.add(new NewTopic(squadTopic, squadTopicPartitions, squadTopicRepFactor));

        topics.add(new NewTopic(enrichedPlayersTopic, enrichedPlayersPartitions, enrichedPlayersRepFactor));
        topics.add(new NewTopic(playersTopic, playersTopicPartitions, playersTopicRepFactor));
        topics.add(new NewTopic(teamsTopic, teamsTopicPartitions, teamsTopicRepFactor));
        topics.add(new NewTopic(squadsTopic, squadsTopicPartitions, squadsTopicRepFactor));
        topics.add(new NewTopic(playersEnrichedWithTeamTopic, playersEnrichedWithTeamPartitions, playersEnrichedWithTeamRepFactor));

        client.createTopics(topics);
        client.close();
    }

}
