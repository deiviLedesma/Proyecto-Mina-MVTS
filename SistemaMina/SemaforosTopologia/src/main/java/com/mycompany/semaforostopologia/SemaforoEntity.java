package com.mycompany.semaforostopologia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "semaforos")
public class SemaforoEntity {

    @Id
    @Column(length = 20, nullable = false, unique = true)
    public String id;

    @Column(nullable = false)
    public double latitud;

    @Column(nullable = false)
    public double longitud;

    @Column(length = 30, nullable = false)
    public String sentido;
}
