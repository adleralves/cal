package com.adler.apical.domain.service;

import com.adler.apical.domain.model.Acessos;
import com.adler.apical.domain.repository.AcessosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author adler
 */

@Service
public class AcessoService {
    
    @Autowired
    private AcessosRepository acessosRepository;
    
    //Método edita usuario(pode ser para novo acesso, ou editar)
    public Acessos salvar(Acessos acesso) {
        return acessosRepository.save(acesso);
    }
    
    //Método deleta o acesso com o id selecionado
    public void excluir(Long acessoId) {
        acessosRepository.deleteById(acessoId);
    }
}
