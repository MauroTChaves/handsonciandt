package com.handson.mauro.repository;

import java.util.List;

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
	
	List<Musica> findByNomeLikeIgnoreCase(String nome); 
	
	List<Musica> findByNomeLikeIgnoreCaseAndArtista(String nome, Long id);
}
