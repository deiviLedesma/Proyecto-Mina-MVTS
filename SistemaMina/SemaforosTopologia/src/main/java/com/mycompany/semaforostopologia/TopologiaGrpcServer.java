package com.mycompany.semaforostopologia;

import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;
import org.mina.topologia.grpc.TopologiaProto;
import org.mina.topologia.grpc.TopologiaServiceGrpc;

@GrpcService
public class TopologiaGrpcServer extends TopologiaServiceGrpc.TopologiaServiceImplBase {

    // Inyectamos el gestor para poder leer la lista de semáforos
    @Inject
    GestorTopologia gestor;

    @Override
    public void getSemaforos(TopologiaProto.EmptyRequest request, 
                             io.grpc.stub.StreamObserver<TopologiaProto.SemaforoListResponse> responseObserver) {
        
        TopologiaProto.SemaforoListResponse.Builder responseBuilder = TopologiaProto.SemaforoListResponse.newBuilder();
        
        // Llamamos al nuevo método público del gestor
        for (GestorTopologia.SemaforoDto dto : gestor.obtenerTodosLosSemaforos()) {
            responseBuilder.addSemaforos(TopologiaProto.Semaforo.newBuilder()
                    .setId(dto.id)
                    .setLatitud(dto.lat)
                    .setLongitud(dto.lon)
                    .setSentido(dto.sentido)
                    .build());
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
        
        System.out.println(" [gRPC] Petición resuelta exitosamente.");
        gestor.actualizarSemaforo("01", 2, 2, "SUR");
    }
}
