package br.com.ls.comanda_api.service;

import br.com.ls.comanda_api.model.Comanda;
import br.com.ls.comanda_api.repository.ComandaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComandaService {

    private final ComandaRepository comandaRepository;

    public ComandaService(ComandaRepository comandaRepository) {
        this.comandaRepository = comandaRepository;
    }

    public List<Comanda> listarComandas() {
        return comandaRepository.findAll();
    }

    public Optional<Comanda> buscarPorId(Long id) {
        return comandaRepository.findById(id);
    }

    public Comanda criarComanda(Comanda comanda) {
        return comandaRepository.save(comanda);
    }

    public Comanda atualizarComanda(Comanda comanda) {
        return comandaRepository.save(comanda);
    }

    public void deletarComanda(Long id) {
        comandaRepository.deleteById(id);
    }
}
