package com.mycompany.gerencia;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Gerencia {

    public static void main(String[] args) {
        pedirHistorialCongestiones();
    }

    public static void pedirHistorialCongestiones() {
        try {
            HttpClient cliente = HttpClient.newHttpClient();

            // petición GET a puerto 8085
            HttpRequest peticion = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8085/api/historial/congestiones"))
                    .GET()
                    .build();

            // esperamos un JSON
            HttpResponse<String> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());

            if (respuesta.statusCode() == 200) {
                System.out.println(" [✓] Datos recibidos: " + respuesta.body());
            } else {
                System.out.println(" [X] Error del servidor. Código HTTP: " + respuesta.statusCode());
            }

        } catch (Exception e) {
            System.err.println(" [X] No se pudo conectar al servidor Quarkus.");
            e.printStackTrace();
        }
    }
}
