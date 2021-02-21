package score.alarm.demo.mappers.impl;

import org.springframework.stereotype.Component;
import score.alarm.demo.db.models.Player;
import score.alarm.demo.db.models.Squad;
import score.alarm.demo.db.models.Stats;
import score.alarm.demo.db.models.Team;
import score.alarm.demo.kafka.models.SquadTeamPlayer;
import score.alarm.demo.mappers.Mapper;

/**
 * Mapper that converts Kafka object to Mongo document.
 * @see Mapper
 */
@Component
public class KafkaMongoMapper implements Mapper<SquadTeamPlayer, Squad> {

    @Override
    public Squad mapTo(SquadTeamPlayer kafka) {
        Squad.SquadBuilder squadBuilder = new Squad.SquadBuilder(kafka.team.id);

        Team.TeamBuilder teamBuilder = new Team.TeamBuilder(kafka.team.id);

        kafka.team.stats.forEach(s -> {
            Stats.StatsBuilder statsBuilder = new Stats.StatsBuilder();

            statsBuilder.setName(s.name);
            statsBuilder.setLang(s.lang);

            teamBuilder.addStat(statsBuilder.build());
        });

        squadBuilder.setTeam(teamBuilder.build());

        kafka.players.forEach(p -> {
            Player.PlayerBuilder playerBuilder = new Player.PlayerBuilder(p.player_id)
                    .setWeight(p.weight)
                    .setHeight(p.height)
                    .setShirtNumber(p.shirt_number)
                    .setBirthDate(p.birth_date)
                    .setFirstName(p.first_name)
                    .setLastName(p.last_name)
                    .setFullName(p.full_name)
                    .setCountryCode(p.country_code)
                    .setPosition(p.position)
                    .setPreferredFoot(p.preferred_foot);

            p.stats.forEach(s -> {
                Stats.StatsBuilder statsBuilder = new Stats.StatsBuilder();

                statsBuilder.setName(s.name);
                statsBuilder.setLang(s.lang);

                playerBuilder.addStat(statsBuilder.build());
            });

            squadBuilder.addPlayer(playerBuilder.build());

        });

        return squadBuilder.build();
    }

}
