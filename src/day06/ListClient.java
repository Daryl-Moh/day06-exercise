package day06;

import java.net.Socket;

public class ListClient {

    public static void main(String[] args) throws Exception {
        
        Integer nums = Integer.parseInt(args[0]); // Get the list size

        Integer range = Integer.parseInt(args[1]); // Get the number range

        String host = args[2];  // Get the host 

        Integer port = Integer.parseInt(args[3]); // Get the port 
        
        // Create the socket
        Socket socket = new Socket(host, port);

        System.out.printf("Connected to %s:%d\n", host, port);

        IOUtils.write(socket, "%d %d".formatted(nums, range));

        String response = IOUtils.read(socket); 

        String[] values = response.split(":");
        Integer sum = 0;
        float avg = 0f;

        for (Integer i=0; i<nums; i++) {

            Integer temp = Integer.parseInt(values[i]);
            sum += temp;
        }

        avg = sum / nums;

        System.out.printf("The average of the random numbers is: %.2f\n", avg);
        
        socket.close();

    } 
    
}
