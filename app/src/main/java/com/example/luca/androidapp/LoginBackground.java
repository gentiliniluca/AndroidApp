package com.example.luca.androidapp;

import android.os.AsyncTask;

public class LoginBackground extends AsyncTask<Void, Void, Void> {

    String user, password;
    Boolean esito=false;

    //creo interfaccia per demandare l'esecuzione all'activity chiamante del metodo onPost
    public AsyncResponse delegate;

    LoginBackground (String u, String p, AsyncResponse ar)
    {
        user=u;
        password=p;
        esito=false;
        delegate=ar;
    }
    @Override
    protected Void doInBackground(Void... arg0)
    {
        System.out.println("User: *"+user+"* Pass: *"+password+"*");

        if( (user.equals("participant") && password.equals("participant")) ||
                (user.equals("police") && password.equals("police")) ||
                (user.equals("ems") && password.equals("ems"))) {
            System.out.println("Entrato, OKKK");
            esito=true;
        }
        else {
            System.out.println("NON entrato|");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        System.out.println("richiamo il metodo nell'activity e gli passo: "+esito);
        delegate.processFinish((Object)esito);
    }

    public Boolean getEsito()
    {
        return esito;
    }
}


