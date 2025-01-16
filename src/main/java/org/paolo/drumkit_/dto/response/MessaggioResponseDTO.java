package org.paolo.drumkit_.dto.response;

import lombok.Data;

@Data
public class MessaggioResponseDTO {
	private String emailMittente;
	private String emailDestinatario;
	private String testo;
	private String data;
}
