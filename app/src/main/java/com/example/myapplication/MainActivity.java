package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.controladores.Constantes.Fisica;
import com.example.myapplication.controladores.Constantes.Mensajes;
import com.example.myapplication.controladores.ControladorLogin;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ControladorLogin cl;
    private Button btns[];
    private TextView contador;
    private TextView txtRonda;
    private Handler handler;
    private static int tiempo;
    private static int rondas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cl = new ControladorLogin();

        btns = new Button[3];
        btns[0] =(Button)findViewById(R.id.button);
        //btns[0].setText("Uno");
        btns[0].setOnClickListener(this);
        btns[1] =(Button)findViewById(R.id.button2);
        //btns[1].setText("Dos");
        btns[1].setOnClickListener(this);
        btns[2] =(Button)findViewById(R.id.button3);
        //btns[2].setText("Tres");
        btns[2].setOnClickListener(this);

        contador = (TextView)findViewById(R.id.contador);
        txtRonda = (TextView)findViewById(R.id.textView2);
        handler = new Handler();
        /*
        Timer temp = new Timer();
        tiempo = 60;

        //Your approach doesn't work because you are trying to update UI from non-UI thread. This is not allowed.

        temp.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //System.out.println(tiempo);

                contador.setText(tiempo);
                tiempo = tiempo>=0?tiempo-1:60;
            }
        },1000,1000);
        */

    }
    public void actualizarTiempo(int tiempo)
    {
        contador.setText(tiempo);
    }

    public void ControlTiempo(){

        new Thread(new Runnable(){
            @Override
            public void run() {
                tiempo = 30;
                boolean flag = true;
                while(flag)
                {
                    try {

                        Thread.sleep(1000);

                        if(tiempo--==0)
                        {
                            rondas--;
                            flag = rondas>0;
                            tiempo = flag?30:0;
                        }

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            actualizarInterfaz(rondas);
                            contador.setText(""+tiempo);
                        }
                    });
                }
            }
        }).start();
    }

    public void actualizarInterfaz(int valor){
        final int actualizar_valor = valor;
        new Thread(new Runnable(){
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        txtRonda.setText("Ronda 1 de "+actualizar_valor);
                    }
                });

            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(getBaseContext(),""+view.getId(),Toast.LENGTH_LONG);

        switch(view.getId())
        {
            case R.id.button:
                //System.out.println("Presiono el boton 1");
                actualizarInterfaz(++rondas);
                break;
            case R.id.button2:
                actualizarInterfaz(rondas-1>=1?rondas--:1);
                //Toast.makeText(getBaseContext(),"Mensaje de prueba",Toast.LENGTH_LONG).show();
                break;
            case R.id.button3:
                ControlTiempo();
                /*final Snackbar make = Snackbar.make(view, "Presiono el boton 3", Snackbar.LENGTH_INDEFINITE);
                make.setAction("boton", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       make.dismiss(); 
                    }
                });
                make.show();*/
                break;
            default: System.out.println("Error valor invalido");
                    break;
        }
    }

    public void ocultarBotones(){
        for(int i=0;i<btns.length;i++)
        {
            //btns[i].
            //...
        }
    }
}