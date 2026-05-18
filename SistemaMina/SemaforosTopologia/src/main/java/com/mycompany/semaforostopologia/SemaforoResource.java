package com.mycompany.semaforostopologia;

import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/semaforos")
@Blocking
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SemaforoResource {

    @Inject
    SemaforoService service;

    @GET
    public List<SemaforoDto> listar() {
        return service.listarSemaforos();
    }

    @GET
    @Path("/{id}")
    public SemaforoDto obtener(@PathParam("id") String id) {
        return service.obtenerPorId(id);
    }

    @POST
    public SemaforoDto guardar(SemaforoRequest request) {
        return service.guardar(request);
    }
}
