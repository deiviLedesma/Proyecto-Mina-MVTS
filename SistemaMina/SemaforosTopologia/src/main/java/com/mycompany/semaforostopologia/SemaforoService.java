package com.mycompany.semaforostopologia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class SemaforoService {

    @Inject
    SemaforoDao repository;

    @Transactional
    public List<SemaforoDto> listarSemaforos() {
        return repository.listAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public SemaforoDto obtenerPorId(String id) {
        SemaforoEntity entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("No existe el semaforo con id " + id);
        }
        return toDto(entity);
    }

    @Transactional
    public SemaforoDto guardar(SemaforoRequest request) {
        validar(request);

        SemaforoEntity entity = repository.findById(request.id);
        if (entity == null) {
            entity = new SemaforoEntity();
            entity.id = request.id;
        }

        entity.latitud = request.latitud;
        entity.longitud = request.longitud;
        entity.sentido = request.sentido.trim().toUpperCase();

        repository.persist(entity);
        return toDto(entity);
    }

    private void validar(SemaforoRequest request) {
        if (request == null) {
            throw new BadRequestException("La solicitud del semaforo no puede venir vacia.");
        }
        if (request.id == null || request.id.isBlank()) {
            throw new BadRequestException("El id del semaforo es obligatorio.");
        }
        if (request.latitud == null) {
            throw new BadRequestException("La latitud del semaforo es obligatoria.");
        }
        if (request.longitud == null) {
            throw new BadRequestException("La longitud del semaforo es obligatoria.");
        }
        if (request.sentido == null || request.sentido.isBlank()) {
            throw new BadRequestException("El sentido del semaforo es obligatorio.");
        }
    }

    private SemaforoDto toDto(SemaforoEntity entity) {
        return new SemaforoDto(entity.id, entity.latitud, entity.longitud, entity.sentido);
    }
}
