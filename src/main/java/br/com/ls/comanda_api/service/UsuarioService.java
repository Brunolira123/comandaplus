package br.com.ls.comanda_api.service;

import br.com.ls.comanda_api.enuns.EPerfil;
import br.com.ls.comanda_api.model.Usuario;
import br.com.ls.comanda_api.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(String username, String senha, EPerfil role) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(senha));
        usuario.setRole(role);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public String encodePassword(String senha) {
        return passwordEncoder.encode(senha);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
