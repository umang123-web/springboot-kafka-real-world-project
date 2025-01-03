package net.javaguides.springboot;
import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;


public class WikimediachangesHandler implements BackgroundEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private KafkaTemplate<String , String>kafkaTemplate;
    private String topic;

    public WikimediachangesHandler(KafkaTemplate<String, String> kafkaTemplate , String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }


    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) throws Exception {
        logger.info(String.format("event data -> %s" , messageEvent.getData()));

        kafkaTemplate.send(topic , messageEvent.getData());

    }

    @Override
    public void onComment(String comment) throws Exception {

    }

    @Override
    public void onError(Throwable t) {

    }
}
