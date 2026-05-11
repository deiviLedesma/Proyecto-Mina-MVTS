package com.mycompany.semaforostopologia;

public class SemaforoDto {

    public String id;
    public double latitud;
    public double longitud;
    public String sentido;

    public SemaforoDto() {
    }

    public SemaforoDto(String id, double latitud, double longitud, String sentido) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.sentido = sentido;
    }
}
