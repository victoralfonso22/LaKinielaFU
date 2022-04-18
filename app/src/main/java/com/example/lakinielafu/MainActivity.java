package com.example.lakinielafu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText usuario;
    EditText password;
    Button ingresar;
    ProgressBar progressBar;
   // Dialog loader = new Dialog(this);

    WebService webService = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.usuario);
        password = (EditText) findViewById(R.id.pass);
        ingresar = (Button) findViewById(R.id.botonLogin);
        progressBar = (ProgressBar) findViewById(R.id.indeterminateBar);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.onEditorAction(EditorInfo.IME_ACTION_DONE);
                password.onEditorAction(EditorInfo.IME_ACTION_DONE);
                progressBar.setVisibility(View.VISIBLE);
                ingresar.setEnabled(false);
                String mensaje = "Error : ";
                boolean bandera = true;
                if(usuario.getText().toString().isEmpty()){
                    bandera = false;
                    mensaje += " Usuario";
                }

                if(password.getText().toString().isEmpty()){
                    if(bandera){
                        mensaje += " Password";
                    }else{
                        mensaje += " / Password";
                    }
                    bandera = false;

                }
                mensaje += " vacio(s)";

                if(bandera) {

                    Thread hilo = new Thread() {
                        @Override
                        public void run() {
                            super.run();

                            final String datos = webService.consumirWS(usuario.getText().toString(), password.getText().toString(), 1, 0);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    int objetosWS = webService.objetoJSon(datos);
                                    if (objetosWS > 0) {
                                        Intent principal = new Intent(getApplicationContext(), Principal.class);
                                        principal.putExtra("nombreCompleto", webService.nombreUsuario(datos));

     /*                               ImageView gif = new ImageView(getApplicationContext());
                                    gif.setImageDrawable(getResources().getDrawable(R.drawable.loader));
                                    loader.addContentView(gif,null);*/
                                        startActivity(principal);
                                        //finish();
                                    } else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        ingresar.setEnabled(true);
                                        usuario.setText("");
                                        password.setText("");
                                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                        }
                    };
                    hilo.start();
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    ingresar.setEnabled(true);
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        progressBar.setVisibility(View.INVISIBLE);
       usuario.setText("");
       password.setText("");
    }
}
