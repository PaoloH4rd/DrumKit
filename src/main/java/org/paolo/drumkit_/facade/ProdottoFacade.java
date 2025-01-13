package org.paolo.drumkit_.facade;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.response.ProdottoInVenditaResponseDTO;
import org.paolo.drumkit_.dto.response.UtenteVenditoreResponseDTO;
import org.paolo.drumkit_.mapper.ProdottoMapper;
import org.paolo.drumkit_.service.def.ProdottoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdottoFacade {
    private final ProdottoService prodottoService;
    private final ProdottoMapper mapper;

    public void aggiungiProdottoVendita(String nome, String descrizione, double prezzo, int quantita) {
        prodottoService.creaProdotto(nome, descrizione, prezzo, quantita);
    }


    public List<ProdottoInVenditaResponseDTO> getProdotti(Long idProprietario) {
        return prodottoService.getAllProdottiAltriClienti(idProprietario).stream()
                .map(mapper::toProdottoInVenditaResponseDTO)
                .collect(Collectors.toList());
    }
    public UtenteVenditoreResponseDTO getVenditore(Long idProdotto) {
        return mapper.toUtenteVenditoreResponseDTO(prodottoService.getProprietario(idProdotto));
    }
    public void rifiutaProdotto(Long idProdotto) {
        prodottoService.setStatoProdotto(idProdotto);
    }

}
