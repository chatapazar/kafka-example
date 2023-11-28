package kafkaExample;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SslConfigs;

public class RunnableProducer implements Runnable {
    
    private Thread t;
    private String threadName;
    private long begintime;
    private String[] var;
    //private final String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";"; 
    //private final String prodJaasCfg = String.format(jaasTemplate, "admin", "secret@Password!"); 
    
    RunnableProducer(String name, long begin, String[] args){
        threadName = name;
        var = args;
        begintime = begin;
        System.out.println("Creating " +  threadName );
    }

    public void run(){
        System.out.println("Running " +  threadName );
        begintime = System.currentTimeMillis();
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, var[4]);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        if(var[5].equals("saslssl")){
            props.put("security.protocol","SASL_SSL");
            props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, var[6]);
            props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, var[7]); 
            props.put("sasl.mechanism", "SCRAM-SHA-512");
        }else if(var[5].equals("saslplain")){
            props.put("security.protocol","SASL_PLAINTEXT"); 
            props.put("sasl.mechanism", "SCRAM-SHA-512");
        }
        
        //props.put("sasl.jaas.config", prodJaasCfg); 
        
        String message = "DTXCASH|1:;20210616|test1|58664|DMJ|0|91000||41025200";

        if(var[3].equals("pool")){
            KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
            try {
                for (int i = 0; i < Integer.parseInt(var[1]); i++) {
                    long begin = System.currentTimeMillis();
                    ProducerRecord<String, String> data = new ProducerRecord<String, String>(var[0], String.valueOf(i), message);
                    producer.send(data);
                    long end = System.currentTimeMillis(); 
                    long time = end-begin;
                    System.out.println();
                    System.out.println("Thread " +  threadName + " Elapsed Time: "+time +" milli seconds");
                }
                producer.close();

            } catch (Exception e) {
            System.out.println("Thread " +  threadName + " error.");
            }
            long duration = System.currentTimeMillis() - begintime;
            System.out.println("Thread " +  threadName + " exiting, duration is " + duration + " ms. of " + Integer.parseInt(var[1])*Integer.parseInt(var[2]));
            
        }else{
            try {
                for (int i = 0; i < Integer.parseInt(var[1]); i++) {
                    long begin = System.currentTimeMillis();
                    KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
                    ProducerRecord<String, String> data = new ProducerRecord<String, String>(var[0], String.valueOf(i), message);
                    producer.send(data);
                    producer.close();
                    long end = System.currentTimeMillis(); 
                    long time = end-begin;
                    System.out.println();
                    System.out.println("Thread " +  threadName + " Elapsed Time: "+time +" milli seconds");
                }
            } catch (Exception e) {
            System.out.println("Thread " +  threadName + " error.");
            }
            long duration = System.currentTimeMillis() - begintime;
            System.out.println("Thread " +  threadName + " exiting, duration is " + duration + " ms. of " + Integer.parseInt(var[1])*Integer.parseInt(var[2]));
        }
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
           t = new Thread (this, threadName);
           t.start ();
        }
     }
}
