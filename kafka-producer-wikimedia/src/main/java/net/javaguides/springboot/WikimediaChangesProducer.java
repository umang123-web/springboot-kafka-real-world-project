package net.javaguides.springboot;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.StreamException;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private static final Logger logger = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private KafkaTemplate<String , String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws StreamException, InterruptedException {


        //to read real time stream data from wikimedia we use event source

        BackgroundEventHandler eventHandler = new WikimediachangesHandler(kafkaTemplate , topicName);
        String url ="https://stream.wikimedia.org/v2/stream/recentchange";

        EventSource.Builder builder = new EventSource.Builder(URI.create(url));
        BackgroundEventSource.Builder builder1 = new BackgroundEventSource.Builder(eventHandler , builder);

        BackgroundEventSource backgroundEventSource = builder1.build();
        backgroundEventSource.start();

        TimeUnit.MINUTES.sleep(10);
    }
}
