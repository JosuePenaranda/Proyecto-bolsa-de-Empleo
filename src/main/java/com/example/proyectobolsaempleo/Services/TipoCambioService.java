package com.example.proyectobolsaempleo.Services;

import com.example.proyectobolsaempleo.logic.TipoCambio;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TipoCambioService {

    public static TipoCambio obtenerTipoCambio() {

        try {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.hacienda.go.cr/indicadores/tc/dolar"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            String body = response.body();

            // PARSEO LIMPIO
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(body);

            double venta = root.get("venta").get("valor").asDouble();
            double compra = root.get("compra").get("valor").asDouble();

            return new TipoCambio(venta, compra, "CRC");

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo tipo de cambio", e);
        }
    }
}