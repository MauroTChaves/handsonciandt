package com.handson.mauro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handson.mauro.entity.Musica;

/**
 * Repositório de Músicas
 * @author mauro
 *
 */
@Repository
public interface MusicasRepository extends JpaRepository<Musica, Long> {

}
