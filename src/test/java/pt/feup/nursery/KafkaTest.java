package pt.feup.nursery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import pt.feup.nursery.kafka.consumer.Receiver;
import pt.feup.nursery.kafka.producer.Sender;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {KafkaTest.HELLOWORLD_TOPIC})
public class KafkaTest {

    static final String HELLOWORLD_TOPIC = "helloworld.t";

    @Autowired
    private Receiver receiver;

    @Autowired
    private Sender sender;

    /*@Test
    public void testReceive() throws Exception {
        sender.send("Hello Spring Kafka!");

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }*/

}
