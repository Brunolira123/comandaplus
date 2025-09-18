package br.com.ls.comanda_api.service;

import br.com.ls.comanda_api.model.Mesa;
import br.com.ls.comanda_api.repository.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    public Mesa criarMesa(int numero) {
        Mesa mesa = new Mesa();
        mesa.setNumero(numero);
        mesa.setAtiva(true);
        return mesaRepository.save(mesa);
    }

    public List<Mesa> listarMesas() {
        return mesaRepository.findAll();
    }

    public Optional<Mesa> buscarPorId(Long id) {
        return mesaRepository.findById(id);
    }

    public Mesa atualizarMesa(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    public void deletarMesa(Long id) {
        mesaRepository.deleteById(id);
    }
}
