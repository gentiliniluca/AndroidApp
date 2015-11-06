package com.example.luca.androidapp;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class PIGResponse extends AsyncTask<Void, Void, Void>
{
    boolean esito=false, server_continium;
    ResultActivity rs;
    int port;
    String response;

    PIGResponse(ResultActivity rs, int port)
    {
        this.rs =rs;
        this.port=port;
        System.out.println("--------------------IP: " + Global.getIP() + " porta: " + port);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                CountDownTimer cdt = new CountDownTimer(7000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        server_continium=false;
                        System.out.println("BASTA RICEVEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                    }
                }.start();
            }
        });
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        esito=false;
        rs.updateResult("wait...");
        response="";
        server_continium=true;

        receive_via_TCP_from_DS();
        //invia_e_ricezione_Prova();

        return null;
    }


    public void receive_via_TCP_from_DS()
    {
        esito=false;
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("----------------pronto ricevere accept su porta " + port + "->");

            while (server_continium) {
                try {
                    Socket ns = ss.accept();
                    System.out.println("----------------ricevuta! accept da " + ns.getInetAddress());
                    BufferedReader networkIn = new BufferedReader(new InputStreamReader(ns.getInputStream(), "UTF-8"));

                    //OutputStreamWriter osw = new OutputStreamWriter(ns.getOutputStream(), "UTF-8");;
                    //BufferedWriter networkOut= new BufferedWriter(osw);

                    String line;
                    while ((line = networkIn.readLine()) != null) {
                        response = response +line+"\n";
                        System.out.println("**************************Ricevuto: " + response);
                    }
                    esito = true;
                    //server_continium=false;
                    ns.close();
                } catch (Exception r) {
                    System.out.println("Errore singola ricezione nel ciclo while "+r);
                }
            }
            ss.close();
        }
        catch(Exception e) {
            System.out.println(e);
            response=("Errore parte ricezione socket: "+e);
        }

        System.out.println("Arrivato alla fine di do in background");

    }


    //prova!
    public void prova_sola_ricezione()
    {
        //ricevo pkt da udp

        try {

        /*Preparo un DatagramPacket che userò per leggere i messaggi dalla socket*/
            DatagramSocket sock = new DatagramSocket(port);
            System.out.println("*****************Creata la socket");
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
        /*Ricevo pacchetto e ne leggo il contenuto*/
            System.out.println("**********ricevo ora");
            sock.receive(packet);
            System.out.println("**********subito dopo ricezione");

        /*Leggo la stringa che dovro stampare*/
            String response = new String(packet.getData(), "UTF-8");
            System.out.println("Ricevuta richiesta da " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " ('" + response + "')");
            esito=true;
        }catch (Exception e){
            response=("Errore parte ricezione socket: "+e);
        }

        System.out.println("*******Fine ricezione");
    }

    //prova!
    private void invia_e_ricezione_Prova() {
        String ip="10.0.2.2";
        int port=60000;

        try{
            esito=false;
            System.out.println("invio la stringa da inviare: ");
            String msg = "*********************INVIO ANCHIO";

			/*Levo spazi biachi all'inizio e alla fine della stringa*/
            msg=msg.trim();

            /*Creo la socket*/
            DatagramSocket sock = new DatagramSocket();

            /*Preparo la richiesta*/
            byte[] buffer = msg.getBytes("UTF-8");
            InetAddress remoteAddr = InetAddress.getByName(ip);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, remoteAddr , port);

            /*Invio messaggio al server*/
            sock.send(packet);
            System.out.println("--str inviata");

            // Ricezione citazione
            byte[] buf = new byte[512];
            packet = new DatagramPacket(buf, buf.length);
            System.out.println("--inizio ricezione ***************");
            sock.receive(packet);System.out.println("--termino ricezione ***************");

            buf = packet.getData();
            response=new String(buf);
            System.out.println("**********************"+response);

            esito=true;

            sock.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }

    }

    @Override
    protected void onPostExecute(Void result) {
        System.out.println("*******entro in postExecute");
        super.onPostExecute(result);
        if(esito) {
            rs.updateResult(response);
            esito = false;
        }else {
            Toast.makeText(rs, "Unable to receive packet", Toast.LENGTH_SHORT).show();
            rs.updateResult("Unable to receive!");
        }
    }

}
