import java.io.*;
import java.net.*;

 
public class SendClientTCP
{
    

    public static void main(String args[])
    {
	while(true){
		try {
		    
		    //String hostname = "192.168.1.1";
		    String hostname = "127.0.0.1";
		    int porta = 56487;
		    System.out.println("Valori: "+hostname+":"+porta);
		    Socket theSocket = new Socket(hostname, porta);
		    System.out.println("socket creata! ");
		    BufferedReader networkIn = new BufferedReader(new InputStreamReader(theSocket.getInputStream(), "UTF-8"));
		    //BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
		    
		    OutputStreamWriter osw =  new OutputStreamWriter(theSocket.getOutputStream(), "UTF-8");
	      	    BufferedWriter networkOut = new BufferedWriter(osw);
		    
		    System.out.println("Connesso al server");
		                   
		    String theLine="Messaggio di risposta dal pig verso cell using disservice"; 
		    networkOut.write(theLine+"\n");
		    networkOut.flush();
		   
		        
		    
		theSocket.close();
		}
		catch (Exception e) {
		    System.err.println("Errore: " + e);
		}
		try{
		    Thread.sleep(5000);
		}catch(Exception ee){
		    System.out.println("doppio errore");
		}
	}
    }
}
