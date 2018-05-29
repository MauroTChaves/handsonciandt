package com.handson.mauro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handson.mauro.entity.Artista;

/**
 * Repositório de Artistas
 * @author mauro
 *
 */
@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {

}
