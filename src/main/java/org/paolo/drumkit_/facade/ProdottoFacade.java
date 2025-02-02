package org.paolo.drumkit_.facade;

import lombok.RequiredArgsConstructor;
import org.paolo.drumkit_.dto.response.ProdottoInVenditaResponseDTO;
import org.paolo.drumkit_.dto.response.UtenteVenditoreResponseDTO;
import org.paolo.drumkit_.mapper.ProdottoMapper;
import org.paolo.drumkit_.service.def.ProdottoService;
import org.paolo.drumkit_.service.def.UtenteService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdottoFacade {
    private final ProdottoService prodottoService;
    private final ProdottoMapper mapper;
    private final UtenteService utenteService;

    public void aggiungiProdottoVendita(String nome, String descrizione, double prezzo, int quantita, Long idVenditore,String immagine) {
        prodottoService.creaProdotto(nome, descrizione, prezzo, quantita,utenteService.getById(idVenditore),immagine);
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

    public String saveImage(MultipartFile file) {
        try {
            // Definisci il percorso dove salvare l'immagine
            String directoryPath = "C:\\Users\\paolo\\IdeaProjects\\DrumKit\\src\\main\\resources\\static\\styles\\images\\"; // Modifica il percorso secondo le tue necessità
            // Crea la directory se non esiste
            File directory = new File(directoryPath);

            String originalFileName = file.getOriginalFilename();
            //estrae l'estensione del file originale
            String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : null;
            //genera un nome univoco
            String fileName = System.currentTimeMillis() + fileExtension;
            String filePath = directoryPath + fileName;
            // Salva il file su disco
            file.transferTo(new File(filePath));
            return fileName;
        } catch (IOException e) {
            return null;
        }
    }
    //get all prodotti da approvare di un venditore
    public List<ProdottoInVenditaResponseDTO> getAllProdottiDaApprovareVenditore(Long idVenditore) {
        return prodottoService.getAllProdottiApprovareById(idVenditore).stream()
                .map(mapper::toProdottoInVenditaResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProdottoInVenditaResponseDTO> getAllProdottiApprovatiVenditore(String email) {
        return prodottoService.getAllApprovatiByVenditoreEmail(email).stream()
                .map(mapper::toProdottoInVenditaResponseDTO)
                .collect(Collectors.toList());
    }



}
