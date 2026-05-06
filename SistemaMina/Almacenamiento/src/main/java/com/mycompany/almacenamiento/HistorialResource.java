package com.mycompany.almacenamiento;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/historial")
@Produces(MediaType.APPLICATION_JSON)
public class HistorialResource {

    // Inyectamos clase de almacenamiento que contiene las listas en memoria
    @Inject
    Almacenamiento almacenamiento;

    @GET
    @Path("/congestiones")
    public List<String> obtenerCongestiones() {
        return almacenamiento.getHistorialCongestiones();
    }

    @GET
    @Path("/productos")
    public List<String> obtenerProductos() {
        return almacenamiento.getHistorialProductos();
    }
}
