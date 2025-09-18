package br.com.ls.comanda_api.service;

import br.com.ls.comanda_api.model.ItemPedido;
import br.com.ls.comanda_api.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public List<ItemPedido> listarItens() {
        return itemPedidoRepository.findAll();
    }

    public Optional<ItemPedido> buscarPorId(Long id) {
        return itemPedidoRepository.findById(id);
    }

    public ItemPedido salvarItem(ItemPedido item) {
        return itemPedidoRepository.save(item);
    }

    public void deletarItem(Long id) {
        itemPedidoRepository.deleteById(id);
    }
}
