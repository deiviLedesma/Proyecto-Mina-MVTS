package com.mycompany.almacenamiento;

import jakarta.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class Almacenamiento {

    private final List<String> historialCongestiones = new CopyOnWriteArrayList<>();
    private final List<String> historialProductos = new CopyOnWriteArrayList<>();

    @Incoming("congestiones-in")
    public CompletionStage<Void> procesarCongestion(Message<String> mensaje) {
        String payloadCrudo = mensaje.getPayload();
        
        historialCongestiones.add(payloadCrudo);
        System.out.println("[Congestión - Raw]: " + payloadCrudo);

        return mensaje.ack();
    }

    @Incoming("productos-in")
    public CompletionStage<Void> procesarProducto(Message<byte[]> mensaje) {
        byte[] payloadBytes = mensaje.getPayload();
        String payloadCrudo = new String(payloadBytes, StandardCharsets.UTF_8);

        historialProductos.add(payloadCrudo);
        System.out.println("[Producto - Raw]: " + payloadCrudo);

        return mensaje.ack();
    }
    
    public List<String> getHistorialCongestiones() {
        return historialCongestiones;
    }

    public List<String> getHistorialProductos() {
        return historialProductos;
    }
}
