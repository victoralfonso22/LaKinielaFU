package com.example.lakinielafu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText usuario;
    EditText password;
    Button ingresar;

    WebService webService = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.usuario);
        password = (EditText) findViewById(R.id.pass);
        ingresar = (Button) findViewById(R.id.botonLogin);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread hilo = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        final String datos = webService.consumirWS(usuario.getText().toString(),password.getText().toString(),1,0);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int objetosWS = webService.objetoJSon(datos);
                                if(objetosWS > 0){
                                    Intent principal = new Intent(getApplicationContext(),Principal.class);
                                    principal.putExtra("nombreCompleto", webService.nombreUsuario(datos));
                                    startActivity(principal);
                                }else{
                                    Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecto",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                };
                hilo.start();

            }
        });
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();

       usuario.setText("");
       password.setText("");
    }
}
