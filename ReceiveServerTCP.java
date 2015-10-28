import java.io.*;
import java.net.*;


public class ReceiveServerTCP
{
    public static void main(String args[])
    {
        try {
System.out.println("Porta inserita "+args[0]);
            ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));

            while(true) {
                Socket ns = ss.accept();
                BufferedReader networkIn = new BufferedReader(new InputStreamReader(ns.getInputStream(), "UTF-8"));
                
                OutputStreamWriter osw = new OutputStreamWriter(ns.getOutputStream(), "UTF-8");;
		BufferedWriter networkOut= new BufferedWriter(osw);

		

                String line;
                while ((line = networkIn.readLine()) != null) {
                    System.out.println("Ricevuto: " + line);
           
                }

                ns.close();
            }
        }
        catch(IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }
}



