package org.paolo.drumkit_.service.def;

import org.paolo.drumkit_.dto.response.MessaggioResponseDTO;

public interface CustomSenderMessaggioService {
    public void inviaNotifica(MessaggioResponseDTO m, String topic);

}
