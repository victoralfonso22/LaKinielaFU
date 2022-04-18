package com.example.lakinielafu;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.transition.Slide;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Jornada.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Jornada#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Jornada extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Spinner spinJornada;
    View vistaJornada;
    WebService webService = new WebService();


    private OnFragmentInteractionListener mListener;

    public Jornada() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Jornada newInstance(String param1, String param2) {
        Jornada fragment = new Jornada();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        vistaJornada = inflater.inflate(R.layout.fragment_jornada, container, false);

        spinJornada = (Spinner) vistaJornada.findViewById(R.id.spJornadas);
        List listaSpin = new ArrayList();
        listaSpin.add("Jornada 1");
        listaSpin.add("Jornada 2");
        listaSpin.add("Jornada 3");
        listaSpin.add("Jornada 4");
        listaSpin.add("Jornada 5");
        listaSpin.add("Jornada 6");
        listaSpin.add("Jornada 7");
        listaSpin.add("Jornada 8");
        listaSpin.add("Jornada 9");
        listaSpin.add("Jornada 10");
        listaSpin.add("Jornada 11");
        listaSpin.add("Jornada 12");
        listaSpin.add("Jornada 13");
        listaSpin.add("Jornada 14");
        listaSpin.add("Jornada 15");
        listaSpin.add("Jornada 16");
        listaSpin.add("Jornada 17");



        ArrayAdapter adapterSpin = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_dropdown_item_1line,listaSpin);
        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinJornada.setAdapter(adapterSpin);


        spinJornada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {


                Thread hilo = new Thread(){
                    @Override
                    public void run() {
                        super.run();

                        final int suma= spinJornada.getSelectedItemPosition() + 1;
                        final String datos =  webService.consumirWS("","",2,suma);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final ArrayList<RadioGroup> radioGroups = new ArrayList<>();
                                int objetosWS = webService.objetoJSon(datos);
                                ScrollView scrollPrincipal = (ScrollView) vistaJornada.findViewById(R.id.scrollPrincipal);
                                final TableLayout tablaPartidos = (TableLayout) vistaJornada.findViewById(R.id.tablaJornada);

                                int count = tablaPartidos.getChildCount();
                                for (int i = 2; i < count; i++) {
                                    View child = tablaPartidos.getChildAt(i);
                                    if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                                }

                                if(objetosWS > 0){

                                    TableRow.LayoutParams encaParam = new TableRow.LayoutParams();
                                    encaParam.height= TableRow.LayoutParams.WRAP_CONTENT;
                                    encaParam.width= TableRow.LayoutParams.MATCH_PARENT;
                                    encaParam.topMargin=10;

                                    TableRow.LayoutParams textParam = new TableRow.LayoutParams();
                                    textParam.height= TableRow.LayoutParams.WRAP_CONTENT;
                                    textParam.width= dpToPx(20);
                                    textParam.weight=1;
                                    //textParam.span=3;
                                    textParam.gravity=Gravity.CENTER_HORIZONTAL;
                                    textParam.setLayoutDirection(Gravity.CENTER_HORIZONTAL);

                                    textParam.setMargins(10,20,10,20);

                                    ArrayList<JornadaBean> listaJornada = webService.regresaJornada(datos);
                                   // Toast.makeText(getActivity().getApplicationContext(),"mensaje"+datos,Toast.LENGTH_LONG).show();

                                    for(int x = 0; x< listaJornada.size();x++){

                                        TableRow rowPartidos = new TableRow(getActivity().getApplicationContext());


                                        RadioGroup radio = new RadioGroup(getActivity());

                                        RadioButton local1 = new RadioButton(getActivity().getApplicationContext());
                                        RadioButton visitante1 = new RadioButton(getActivity().getApplicationContext());

                                        RadioButton empate1 = new RadioButton(getActivity().getApplicationContext());

                                        local1.setText(listaJornada.get(x).getLocal());
                                        local1.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                                        local1.setLayoutParams(textParam);
                                        local1.setGravity(Gravity.CENTER);
                                        //local1.setId(Integer.valueOf(listaJornada.get(x).getId() + "1"));
                                        local1.setId(Integer.valueOf(110));

                                        empate1.setLayoutParams(textParam);
                                        empate1.setGravity(Gravity.CENTER);
                                        empate1.setText("Empate");
                                        //empate1.setId(Integer.valueOf(listaJornada.get(x).getId() + "2"));
                                        empate1.setId(Integer.valueOf(112));

                                        visitante1.setText(listaJornada.get(x).getVisitante());
                                        visitante1.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                                        visitante1.setLayoutParams(textParam);
                                        visitante1.setGravity(Gravity.CENTER);
                                        //visitante1.setId(Integer.valueOf(listaJornada.get(x).getId() + "3"));
                                        visitante1.setId(Integer.valueOf(113));


                                        radio.setOrientation(LinearLayout.HORIZONTAL);
                                        radio.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

                                        radio.addView(local1);
                                        radio.addView(empate1);
                                        radio.addView(visitante1);

                                        radio.setId(listaJornada.get(x).getId());
                                        radioGroups.add(radio);
                                        radio.setLayoutParams(textParam);
                                        rowPartidos.addView(radio);
                                        tablaPartidos.addView(rowPartidos);

                                        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(RadioGroup group, int checkedId) {

                                                int count = group.getChildCount();
                                                for (int i = 0; i < count; i++) {
                                                    View child = group.getChildAt(i);
                                                    if (child instanceof RadioButton){
                                                        //RadioButton sel = (RadioButton) child;
                                                        if(((RadioButton) child).isChecked()) {
                                                            group.setBackgroundColor(getColorBackActivity(vistaJornada));
                                                             Toast.makeText(getActivity(),"mensaje : "+((RadioButton) child).getText()+" grupo "+group.getId()+" id "+((RadioButton) child).getId(),Toast.LENGTH_LONG).show();
                                                            ((RadioButton) child).setBackgroundColor(getResources().getColor(R.color.seleccion));
                                                            ((RadioButton) child).setTextColor(getResources().getColor(R.color.black));
                                                            ((RadioButton) child).setTypeface(null, Typeface.BOLD);

                                                        }else{
                                                            System.out.println("No están "+((RadioButton) child).getId());
                                                            ((RadioButton) child).setBackgroundColor(getColorBackActivity(vistaJornada));
                                                            ((RadioButton) child).setTextColor(getResources().getColor(R.color.black));
                                                            ((RadioButton) child).setTypeface(null,Typeface.NORMAL);
                                                        }
                                                    }
                                                }


                                            }
                                        });

                                    }
                                    Button btnGuardar = new Button(getActivity());
                                    btnGuardar.setText(getResources().getString(R.string.guardar));
                                    btnGuardar.setLayoutParams(textParam);

                                    TableRow rowBoton = new TableRow(getActivity().getApplicationContext());
                                    rowBoton.addView(btnGuardar);

                                    tablaPartidos.addView(rowBoton);
                                    scrollPrincipal.removeAllViews();
                                    scrollPrincipal.addView(tablaPartidos);

                                    btnGuardar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            final ArrayMap<Integer, Integer> resultados = new ArrayMap<>();
                                                       int countTableRow = radioGroups.size();
                                                       boolean bandera = true;
                                                       for (int y=0;y<countTableRow;y++){

                                                           int count = radioGroups.get(y).getChildCount();

                                                           if(radioGroups.get(y).getCheckedRadioButtonId()== -1){
                                                               bandera=false;
                                                               radioGroups.get(y).setBackgroundColor(getResources().getColor(R.color.rojo));
                                                           }else{



                                                               View child = radioGroups.get(y).getChildAt(y);
                                                               if (child instanceof RadioButton){

                                                                   if(((RadioButton) child).isChecked()) {

                                                                       resultados.put(radioGroups.get(y).getId(),  ((RadioButton) child).getId()  );

                                                                   }
                                                               }


                                                           }

                                                       }

                                                       if(bandera){
                                                           AlertDialog.Builder alertaGuardar = new AlertDialog.Builder(getActivity());
                                                           alertaGuardar.setMessage("¿Estás seguro?").setCancelable(false)
                                                           .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog, int which) {

                                                                webService.guardarJornada(suma,resultados);
                                                               }
                                                           })
                                                           .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog, int which) {

                                                               }
                                                           });

                                                           alertaGuardar.show();

                                                       }else{

                                                           Toast.makeText(getActivity(),"Debes seleccinar todos los partidos",Toast.LENGTH_LONG).show();
                                                       }

                                        }
                                    });

                                }else{

                                    Toast.makeText(getActivity().getApplicationContext(),"No existen partidos para esta jornada",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                };
                hilo.start();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return vistaJornada;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public int getColorBackActivity(View view) {
        int color = Color.TRANSPARENT;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        return color;
    }
}
