package kafkaExample;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Producer {

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        for (int i = 0; i < Integer.parseInt(args[2]); i++) {
            long begin = System.currentTimeMillis();
            sendMessage(props, args[1], i);    
            long end = System.currentTimeMillis(); 
            long time = end-begin;
            System.out.println();
            System.out.println("Elapsed Time: "+time +" milli seconds");
        }
        
    }

    private static void sendMessage(Properties props, String topicName, int i){
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        ProducerRecord<String, String> data = new ProducerRecord<String, String>(topicName, String.format("%d is data", i));
        producer.send(data);
        producer.close();
    }

}
