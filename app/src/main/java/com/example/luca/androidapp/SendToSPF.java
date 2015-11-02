package com.example.luca.androidapp;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.*;
import java.net.*;

public class SendToSPF extends AsyncTask<Void, Void, Void>
{
    String ip;
    int port;
    String line;
    Socket sock;
    MainActivity m;

    boolean esito=false;

    SendToSPF(String ip, int port, String line, MainActivity m)
    {
        this.ip=ip;
        this.port=port;
        this.line=line;

        this.m=m;
    }


    @Override
    protected Void doInBackground(Void... voids)
    {
        try {
            System.out.println("***********************provo a creare socket");
            sock = new Socket(ip, port);
            System.out.println("**************************socket creata");
            OutputStreamWriter osw = new OutputStreamWriter(sock.getOutputStream(), "UTF-8");
            BufferedWriter networkOut = new BufferedWriter(osw);

            networkOut.write(line + "\n");
            networkOut.flush();
            esito=true;
        } catch (IOException e) {
            System.out.println("Errore: " + e);
        }
        finally {
            if (sock != null) {
                try {
                    sock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(esito) {
            m.openResultPage();
            esito = false;
        }else {
            Toast.makeText(m, "Unable to send the request", Toast.LENGTH_SHORT).show();
        }
    }
}
