package org.paolo.drumkit_.dto.response;

import lombok.Data;

@Data
public class MessaggioResponseDTO {
	private String mittente;
	private String testo;
	private String data;
}
