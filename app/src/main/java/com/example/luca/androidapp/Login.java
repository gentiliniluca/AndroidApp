package com.example.luca.androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.view.View;
import android.widget.*;
import android.content.*;

public class Login extends AppCompatActivity implements AsyncResponse {

    String user="", password="";
    EditText User, Pass;
    LoginBackground login_background;
    AsyncResponse ar=this;    //Attenzione, creo un'interfaccia a this, perchè nella classe LoginBackground posso eseguire il metodo di questa classe che implementa tale interfaccia

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button buttonLogin = (Button) findViewById(R.id.login_button);
        final Button buttonReset = (Button) findViewById(R.id.reset_button);

        User = (EditText) findViewById(R.id.username_editText);
        Pass = (EditText) findViewById(R.id.password_editText);


        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                user="";
                password="";

                Pass.setText("");
                User.setText("");

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                user= User.getText().toString();
                password= Pass.getText().toString();

                login_background = new LoginBackground(user,password, ar);
                login_background.execute();


            }
        });
    }

    public void processFinish (Object output)
    {
        //this you will received result fired from async class of onPostExecute(result) method.
        System.out.println("Sono in processFinish e il valore vale: " + (output).toString());
        if((output).toString().equals("false"))
        {
            Toast.makeText(getApplicationContext(), "Non allowed", Toast.LENGTH_LONG).show();
        }
        if((output).toString().equals("true"))
        {
            Toast.makeText(getApplicationContext(), "Welcome "+user+ ", opening app", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Procedo con activity MainActivity");
            // definisco l'intenzione
            Intent mainPage = new Intent(Login.this, MainActivity.class);
            // passo all'attivazione dell'activity Pagina.java
            mainPage.putExtra("username_param", user);
            finish();
            startActivity(mainPage);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit_button:
                System.out.println("exit");
                AppExit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void AppExit()
    {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
    }
}
