package com.example.luca.androidapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    Global global;
    private LocationManager locationManager;
    private String provider;
    private ArrayAdapter<String> listviewAdapter;
    private ArrayAdapter<String> spinnerAdapter;

    TextView lat, longit;
    Spinner spinner;
    Button button;
    EditText ip, port, my_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        global=((Global)getApplicationContext());

        //receive username
        Intent myIntent = getIntent(); // gets the previously created intent
        String nome_param = myIntent.getStringExtra("username_param");

        //set port and ip from global
        ip=(EditText)findViewById(R.id.ip_spf_controller);
        port=(EditText) findViewById(R.id.port_spf_controller);
        my_ip=(EditText) findViewById(R.id.my_ip_address);
        my_ip.setText(Global.getIP());
        ip.setText(global.get_ip_spf_controller());
        port.setText(""+global.get_port_spf_controller());

        //get Your Current Location
        lat=(TextView) findViewById(R.id.latitude_label);
        longit=(TextView) findViewById(R.id.longitude_label);
        
        locationManager=    (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        MyCurrentLoctionListener locationListener = new MyCurrentLoctionListener(lat, longit);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) locationListener);

        //populate spinner (select menu )
        spinner = (Spinner)findViewById(R.id.request_spinner);
        String[] operations=null;
        if(nome_param.equals("participant"))
            operations= new String[]{"Find Water", "Listen Song"};
        else if(nome_param.equals("police"))
            operations= new String[]{"Count"};
        else if(nome_param.equals("ems"))
            operations= new String[]{"Emergency"};
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,operations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //gestione pulsante request to spf controller
        button=(Button) findViewById(R.id.send_request_button);
        button.setOnClickListener(this);

    }


    @Override
    public void onClick(View view)
    {
        // getId() returns this view's identifier.
        if(view.getId() == R.id.send_request_button)
        {
            //prelevo variabili utili da passare
            String my_ip= (ip.getText()).toString();
            String my_port= (port.getText()).toString();
            String my_lat=(lat.getText()).toString(); my_lat=my_lat.replace("Lat: ","");
            String my_longit=(longit.getText()).toString(); my_longit=my_longit.replace("Long: ","");
            String my_request= (spinner.getSelectedItem()).toString();
            System.out.println("***Premuto bottone: *"+my_ip+"*"+my_port+"*"+my_lat+"*"+my_longit+"*"+my_request+"*");

            String line=my_request+" "+my_lat+" "+my_longit;
            SendToSPF send= new SendToSPF(my_ip, Integer.parseInt(my_port), line, this);
            send.execute();
            System.out.println("Arrivato fine premuto send to controller");

        }

    }

    public void openResultPage()
    {
        System.out.println("**********Apro nuova pagina risultati, dopo aver inviato request al spf controller");
        // definisco l'intenzione
        Intent openResultPage = new Intent(MainActivity.this, ResultActivity.class);
        // passo all'attivazione dell'activity Result activity.java
        //openResultPage.putExtra("parametro1", "Vengo da Home");
        //openResultPage.putExtra("parametro2", "!");
        startActivity(openResultPage);
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
