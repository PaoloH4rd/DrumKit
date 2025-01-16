package org.paolo.drumkit_.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URI;
import java.net.URISyntaxException;

public class Util {
    // Metodo per estrarre la view dal referer
    private String extractViewFromReferer(String referer) {
        if (referer == null || referer.isEmpty()) {
            return null; // Se il referer non è disponibile, restituisci null
        }

        // Supponendo che il referer contenga un URL completo del tipo "http://localhost:8080/viewName"
        try {
            // Estrai il path dal referer
            URI uri = new URI(referer);
            String path = uri.getPath();

            // Rimuovi il prefisso iniziale "/" e restituisci il nome della view
            return path.startsWith("/") ? path.substring(1) : path;

        } catch (URISyntaxException e) {
            return null; // Se il referer non è valido, restituisci null
        }
    }
}
