import java.io.*;
import java.net.*;

 
public class SendClientTCP
{
    

    public static void main(String args[])
    {
        try {
            
            String hostname = "188.194.171.113";
	    //String hostname = "127.0.0.1";
            int porta = 56487;
	    System.out.println("Valori: "+hostname+":"+porta);
            Socket theSocket = new Socket(hostname, porta);
            System.out.println("socket creata! ");
            BufferedReader networkIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream(), "UTF-8"));
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            
            OutputStreamWriter osw =  new OutputStreamWriter(theSocket.getOutputStream(), "UTF-8");
      	    BufferedWriter networkOut = new BufferedWriter(osw);
            
            System.out.println("Connesso al server");
            while (true) {
                System.out.println("Inserisci str: ");
                String theLine = userIn.readLine();
                if (theLine.equals("fine")) break;
                
            networkOut.write(theLine+"\n");
            networkOut.flush();
           
                
            }
	theSocket.close();
        }
        catch (IOException e) {
            System.err.println("Errore: " + e);
            System.exit(2);
        }
    }
}
