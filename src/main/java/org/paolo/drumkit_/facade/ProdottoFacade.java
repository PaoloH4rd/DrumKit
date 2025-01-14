package org.paolo.drumkit_.facade;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.response.ProdottoInVenditaResponseDTO;
import org.paolo.drumkit_.dto.response.UtenteVenditoreResponseDTO;
import org.paolo.drumkit_.mapper.ProdottoMapper;
import org.paolo.drumkit_.service.def.ProdottoService;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdottoFacade {
    private final ProdottoService prodottoService;
    private final ProdottoMapper mapper;
    private final UtenteService utenteService;

    public void aggiungiProdottoVendita(String nome, String descrizione, double prezzo, int quantita, Long idVenditore) {

        prodottoService.creaProdotto(nome, descrizione, prezzo, quantita,utenteService.getById(idVenditore));
    }


    public List<ProdottoInVenditaResponseDTO> getAllProdottiDaApprovare() {
        return prodottoService.getAllProdottiDaApprovare().stream()
                .map(mapper::toProdottoInVenditaResponseDTO)
                .collect(Collectors.toList());
    }
    public UtenteVenditoreResponseDTO getVenditore(Long idProdotto) {
        return mapper.toUtenteVenditoreResponseDTO(prodottoService.getProprietario(idProdotto));
    }
    public void rifiutaProdotto(Long idProdotto) {
        prodottoService.setStatoProdottoRifiutato(idProdotto);
    }

    public List<ProdottoInVenditaResponseDTO> getAllProdottiApprovatiNonDiUtenteLoggato(Long idUtente) {
        return prodottoService.getAllProdottiApprovatiNonDiUtenteLoggato(idUtente).stream()
                .map(mapper::toProdottoInVenditaResponseDTO)
                .collect(Collectors.toList());
    }
    public void approvaProdotto(Long idProdotto) {
        prodottoService.setStatoProdottoApprovato(idProdotto);
    }
}
