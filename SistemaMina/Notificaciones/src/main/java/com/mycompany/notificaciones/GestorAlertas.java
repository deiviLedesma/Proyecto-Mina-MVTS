package com.mycompany.notificaciones;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class GestorAlertas {

    @Inject
    @Channel("alertas-out")
    Emitter<String> alertasEmitter;

    @Inject
    ObjectMapper mapper;

    public void emitirAlertaCentral(String mensaje, String severidad, String idSemaforo) {
        try {
            Map<String, String> alertaMap = new HashMap<>();
            alertaMap.put("mensaje", mensaje);
            alertaMap.put("severidad", severidad);
            alertaMap.put("semaforo", idSemaforo);
            alertaMap.put("timestamp", LocalDateTime.now().toString());

            String jsonAlerta = mapper.writeValueAsString(alertaMap);

            alertasEmitter.send(jsonAlerta).whenComplete((success, error) -> {
                if (error != null) {
                    System.err.println(" [X] FALLO CRÍTICO: RabbitMQ rechazó el mensaje: " + error.getMessage());
                } else {
                    System.out.println(" [✓] CONFIRMADO: El mensaje llegó sano y salvo al Exchange de RabbitMQ.");
                }
            });
            System.out.println(" [RabbitMQ] Alerta enviada al Exchange Principal.");

        } catch (Exception e) {
            System.err.println(" [X] Error al procesar JSON de alerta.");
            e.printStackTrace();
        }
    }
}
