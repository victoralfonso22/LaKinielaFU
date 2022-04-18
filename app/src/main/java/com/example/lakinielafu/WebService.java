package com.example.lakinielafu;

import android.icu.text.IDNA;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static android.support.constraint.Constraints.TAG;

public class WebService {

    public String consumirWS(String usuario, String password, int tipo, int jornada) {

        String parametros = "usu=" + usuario + "&pass=" + password + "&tipo=" + tipo + "&jornada=" + jornada;

        HttpURLConnection connection = null;

        String response = "";

        try {
            URL url = new URL("https://www.fundacionuv.org/WSKFUV/validaWS.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Length", "" + Integer.toString(parametros.getBytes().length));

            connection.setDoOutput(true);

            DataOutputStream datosSalientes = new DataOutputStream(connection.getOutputStream());

            datosSalientes.writeBytes(parametros);
            datosSalientes.close();

            Scanner scanner = new Scanner(connection.getInputStream());

            while (scanner.hasNext()) {
                response += (scanner.nextLine());
            }

        } catch (Exception e) {

        }
        return response.toString();
    }


    public int objetoJSon(String respuesta) {
        int numRegistros = 0;
        try {
            JSONArray jsonArray = new JSONArray(respuesta);

            if (jsonArray.length() > 0) {
                numRegistros = 1;
            }
        } catch (Exception e) {

        }
        return numRegistros;
    }

    public String nombreUsuario(String respuesta) {
        String nombre = "";
        try {
            JSONArray jsonArray = new JSONArray(respuesta);

            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                nombre = jsonObject.get("nombre").toString();
            }
        } catch (Exception e) {

        }
        return nombre;
    }

    public ArrayList<JornadaBean> regresaJornada(String respuesta) {
        ArrayList<JornadaBean> listaJornada = new ArrayList<>();


        try {
            JSONArray jsonArray = new JSONArray(respuesta);

            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    JornadaBean jornadaBean = new JornadaBean();

                    jornadaBean.setId(Integer.valueOf(jsonObject.get("id").toString()));
                    // System.out.println(jsonObject.get("local").toString()+" respuesta7");
                    jornadaBean.setLocal(jsonObject.get("local").toString());
                    jornadaBean.setVisitante(jsonObject.get("visitante").toString());
                    jornadaBean.setJornada(Integer.valueOf(jsonObject.get("jornada").toString()));
                    listaJornada.add(jornadaBean);
                    //System.out.println(listaJornada.get(i).getLocal()+" respuesta8");

                }
            }
        } catch (Exception e) {

        }

        return listaJornada;
    }


    public boolean guardarJornada(int jornada, ArrayMap<Integer, Integer> resultados) {

        String parametros = "resultados=" + resultados + "&jornada=" + jornada + "&tipo=" + 3;

        HttpURLConnection connection = null;

        String response = "";

        try {
            URL url = new URL("https://www.fundacionuv.org/WSKFUV/validaWS.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Length", "" + Integer.toString(parametros.getBytes().length));

            connection.setDoOutput(true);

            DataOutputStream datosSalientes = new DataOutputStream(connection.getOutputStream());

            datosSalientes.writeBytes(parametros);
            datosSalientes.close();

            Scanner scanner = new Scanner(connection.getInputStream());

            while (scanner.hasNext()) {
                response += (scanner.nextLine());
            }

        } catch (Exception e) {

        }
        if(response.toString() == "1"){
            return true;
        }else{
            return false;
        }


    }

}