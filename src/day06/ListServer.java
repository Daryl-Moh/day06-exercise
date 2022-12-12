package day06;

import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ListServer {

    public static void main(String[] args) throws Exception {

        ExecutorService thrpool = Executors.newFixedThreadPool(2);

        Runnable run = () -> {
            System.out.printf("Process now running on Thread: %s\n", Thread.currentThread().getName());
            
        };

        thrpool.submit(run);

        Integer port = Integer.parseInt(args[0]); // Get the port from client

        Random rnd = new SecureRandom(); // Random number generator
        
        ServerSocket server = new ServerSocket(port); // Create a ServerSocket and bind to the port
        System.out.printf("Listening on port %d\n", port);

        while (true) {

            System.out.println("Waiting for connections...");
            Socket socket = server.accept();

            System.out.printf("New Connection on port: %d\n", socket.getPort());
            
            String payload = IOUtils.read(socket);
            String[] values = payload.split(" ");

            Integer count = Integer.parseInt(values[0]);
            Integer range = Integer.parseInt(values[1]);

            List<Integer> randNums = new LinkedList<>();
            for (Integer i=0; i<count; i++) 
            randNums.add(rnd.nextInt(range));

            String response = randNums.stream() // Lambda function!! Looping through entire string 
                .map(v -> v.toString())         // and converting them into a string and joining them all together
                .collect(Collectors.joining(":"));

            IOUtils.write(socket, response);

            System.out.println("Random numbers have been generated! \n");

            socket.close();
        }
        
    }

}