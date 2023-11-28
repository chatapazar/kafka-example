package kafkaExample;

public class Runner {
    public static void main(String[] args) throws InterruptedException {

        System.out.println();
        long begin = System.currentTimeMillis();
        System.out.println("start at: " + begin);
        System.out.println("topic: "+args[0]);
        System.out.println("no. of messages: "+args[1]);
        System.out.println("thread: "+args[2]);
        System.out.println("connection: "+ args[3]);
        System.out.println("bootstrap: "+ args[4]);
        for (int i = 0; i < Integer.parseInt(args[2]); i++) {
            new RunnableProducer( "Thread-"+i, begin, args).start();
        }


        // if (args[0].equals("producer")) {
        //     Producer.main(args);
        // } else if (args[0].equals("producerconpool")) {
        //     ProducerConPool.main(args);
        // } else if (args[0].equals("consumer")) {
        //     Consumer.main(args);
        // } else if (args[0].equals("stream")) {
        //     Stream.main(args);
        // } else {
        //     throw new IllegalArgumentException("Don't know how to do " + args[0]);
        // }
    }
}
