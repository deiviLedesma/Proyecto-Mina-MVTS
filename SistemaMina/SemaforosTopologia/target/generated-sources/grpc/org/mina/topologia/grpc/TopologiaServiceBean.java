package org.mina.topologia.grpc;

import io.grpc.BindableService;
import io.quarkus.grpc.GrpcService;
import io.quarkus.grpc.MutinyBean;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: topologia.proto")
public class TopologiaServiceBean extends MutinyTopologiaServiceGrpc.TopologiaServiceImplBase implements BindableService, MutinyBean {

    private final TopologiaService delegate;

    TopologiaServiceBean(@GrpcService TopologiaService delegate) {
        this.delegate = delegate;
    }

    @Override
    public io.smallrye.mutiny.Uni<org.mina.topologia.grpc.TopologiaProto.SemaforoListResponse> getSemaforos(org.mina.topologia.grpc.TopologiaProto.EmptyRequest request) {
        try {
            return delegate.getSemaforos(request);
        } catch (UnsupportedOperationException e) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }
    }
}
