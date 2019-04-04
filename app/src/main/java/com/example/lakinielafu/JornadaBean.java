package com.example.lakinielafu;

public class JornadaBean {

    private int id;
    private String local;
    private String visitante;
    private  int jornada;

    public JornadaBean(){

    }

    public JornadaBean(int id, String local, String visitante, int jornada){
        this.id = id;
        this.local = local;
        this.visitante = visitante;
        this.jornada = jornada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVisitante() {
        return visitante;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    public int getJornada() {
        return jornada;
    }

    public void setJornada(int jornada) {
        this.jornada = jornada;
    }
}
