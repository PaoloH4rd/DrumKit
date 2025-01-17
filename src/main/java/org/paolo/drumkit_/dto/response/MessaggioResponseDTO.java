package org.paolo.drumkit_.dto.response;

import lombok.Data;
@Data
public class MessaggioResponseDTO {
	private String emailMittente;
	private String emailDestinatario;
	private String testo;
	private String data;

	private	MessaggioResponseDTO(String emailMittente, String emailDestinatario, String testo, String data) {
		this.emailMittente = emailMittente;
		this.emailDestinatario = emailDestinatario;
		this.testo = testo;
		this.data = data;
	}
	public static class Builder {
		private String emailMittente;
		private String emailDestinatario;
		private String testo;
		private String data;

		public Builder setEmailMittente(String emailMittente) {
			this.emailMittente = emailMittente;
			return this;
		}
		public Builder setEmailDestinatario(String emailDestinatario) {
			this.emailDestinatario = emailDestinatario;
			return this;
		}
		public Builder setTesto(String testo) {
			this.testo = testo;
			return this;
		}
		public Builder setData(String data) {
			if (data != null && data.contains(":")) {
				// Rimuove i secondi dal formato `yyyy-MM-dd HH:mm:ss` -> `yyyy-MM-dd HH:mm`
				this.data = data.substring(0, data.lastIndexOf(":"));
			} else {
				// Mantieni il valore originale se non contiene ":"
				this.data = data;
			}
			return this;
		}
		public MessaggioResponseDTO build() {
			return new MessaggioResponseDTO(emailMittente, emailDestinatario, testo, data);
		}
	}
}


