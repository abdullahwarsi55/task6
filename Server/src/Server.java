import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.SECONDS;
  
// Server class
class Server {
    public static void main(String[] args)
    {
        ServerSocket server = null;
        CountDownLatch Latch =new CountDownLatch(1);
  
        try {
  
            
            server = new ServerSocket(8081);
            server.setReuseAddress(true);
  
            
            while (true) {
  
                
                Socket client = server.accept();
  
                
                System.out.println("IAM ALIVE"
                                   + client.getInetAddress()
                                         .getHostAddress());
  
                
               
  
                
                new Thread(new ClientHandler(client, Latch)).start();

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
  

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final CountDownLatch Latch;
  
        // Constructor
        public ClientHandler(Socket socket,CountDownLatch Latch)
        {
            this.clientSocket = socket;
            this.Latch = Latch;
        }
       
        public void run()
        {
            PrintWriter out = null;
            BufferedReader in = null;
            boolean exitStatus = false;
            try {
                    
                 
                out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
  
                  
                in = new BufferedReader(
                    new InputStreamReader(
                        clientSocket.getInputStream()));
  
                String line;
                while ((line = in.readLine()) != null) {
                 
                    
                    System.out.printf(
                        " Sent from the client: %s\n",
                        line);
                    out.println(line);
                    Scanner myObj = new Scanner(System.in); 
                    System.out.println("response back");
                    String response = myObj.nextLine();
                   try{
                       
                       if (in.readLine().equalsIgnoreCase("exit")) {
                         Latch.await(5 ,TimeUnit.SECONDS);
                        out.println("Goodbye!");
                        exitStatus = false;
                    } else {
                          Latch.await(5 ,TimeUnit.SECONDS);
                        out.println("Response Received!");
                        System.out.println("Respose is sent after 5 seconds!");
                    }
                   }
                   catch(InterruptedException ex){
                       
                   }         
                    System.out.println("Sent by server: " + response);
    
    
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
