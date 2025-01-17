package org.paolo.drumkit_.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.paolo.drumkit_.dto.request.InviaMessaggioRequestDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.boot.json.JsonParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class ListenerMessaggi implements MessageListener {
    @Override
    public void onMessage(Message message) {
        //mi prendo il json dalla notifica

        byte[] risposta = message.getBody();

        //stampa il body del messaggio
        System.out.println("STAMPA DEL BODY");
        System.out.println(Arrays.toString(risposta));
        //lo converto in testo
        System.out.println("STAMPA DEL BODY CONVERTITO IN STRINGA");
        String json = new String(risposta);
        System.out.println(json);
        try {
            //lo converto in oggetto
            System.out.println("entro nel try");
            InviaMessaggioRequestDTO m = new ObjectMapper().readValue(json, InviaMessaggioRequestDTO.class);
            //creo il path del file di testo dove scriverò tutto
            Path p = Paths.get("C:/Users/paolo/Desktop/messaggi.txt");
            //se non esiste il file
            if (Files.notExists(p)) {
                //lo creo
                Files.createFile(p);
            }
            //mi formatto il testo in modo che sia su una sola riga
            String testoDaScrivere ="email mittente:" +m.getEmailMittente()+"\n email destinatario: "+ m.getEmailDestinatario() + "\n testo: " + m.getTesto() + "\n";
            //lo vado a scrivere sul file creato (o aperto) in precedenza con metodologia APPEND, ovvero,
            //non cancellare quello che c'è scritto ma scrivi sotto
            Files.writeString(p, testoDaScrivere, StandardOpenOption.APPEND);
            System.out.println("scritto");
        } catch (IOException | JsonParseException e) {
            e.printStackTrace();
        }
    }
}
