package score.alarm.demo;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.test.TestRecord;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import score.alarm.demo.kafka.models.Player;
import score.alarm.demo.kafka.serdes.CustomSerdes;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class SquadAPIApplicationTests {

    private static final String INPUT = "br_domain_player";
    private static final String OUTPUT = "output";

    /*
        I wrote this one test to check my hypothesis about removing duplicates from stream.
    */
    @Test
    void testRemovingDuplicates() {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<Long, Player> stream = builder.stream(INPUT, Consumed.with(Serdes.Long(), CustomSerdes.Player()));

        stream.selectKey((key, value) -> value.id)
                .groupByKey()
                .reduce((p1, p2) -> p1.version >= p2.version ? p1 : p2)
                .toStream()
                .to(OUTPUT);

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, CustomSerdes.Player().getClass());
        TopologyTestDriver driver = new TopologyTestDriver(builder.build(), config);

        TestInputTopic<Long, Player> inputTopic = driver.createInputTopic(INPUT, Serdes.Long().serializer(), CustomSerdes.Player().serializer());

        inputTopic.pipeValueList(TestUtils.getSamePlayer());

        TestOutputTopic<Long, Player> outputTopic = driver.createOutputTopic(OUTPUT, Serdes.Long().deserializer(), CustomSerdes.Player().deserializer());
        TestRecord<Long, Player> result = outputTopic.readRecord();

        assertThat(result.key()).isEqualTo(133704L);
        assertThat(result.value().id).isEqualTo(133704L);
        assertThat(result.value().version).isEqualTo(3);
        assertThat(result.value().names.stats.get(0).name).isEqualTo("John Norman");

        driver.close();
    }

}
