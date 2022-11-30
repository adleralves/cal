package com.adler.apical.domain.repository;

import com.adler.apical.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author adler
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
