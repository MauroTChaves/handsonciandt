package com.handson.mauro.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.handson.mauro.entity.Musica;
import com.handson.mauro.repository.ArtistaRepository;
import com.handson.mauro.repository.MusicasRepository;

/**
 * Controller da entidade de musicas
 * 
 * @author mauro.chaves
 *
 */
@RestController
@RequestMapping("api/musicas")
public class MusicaController {

	private MusicasRepository repository;

	private ArtistaRepository artistaRepository;

	public MusicaController(MusicasRepository repository) {
		this.repository = repository;
	}

	/**
	 * Retorna todos as musicas cadastradas na database
	 * 
	 * @return lista de Musicas
	 */
	@GetMapping
	public Iterable<Musica> getAll() {
		return this.repository.findAll();
	}

	/**
	 * Método que retorna uma musica pssando com parametro o id
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/{id}")
	public ResponseEntity findOne(@PathVariable Long id) {
		Optional<Musica> byId = this.repository.findById(id);
		return orElseReturn(byId, id);
	}

	/**
	 * Método que salvar uma musica e por boas praticas retorna o status 200 qnd
	 * sucesso e 401 qnd erro.
	 * 
	 * @param musica
	 * @throws SQLException
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody @Valid Musica musica) {
		this.repository.save(musica);
	}

	/**
	 * Método que exclui uma musica da database
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		Optional<Musica> byId = this.repository.findById(id);
		if (byId == null) {
			return ResponseEntity.notFound().build();
		} else if (byId.isPresent()) {
			this.repository.delete(byId.get());
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	/**
	 * Método auxiliar para retornar status ok ou not found
	 * 
	 * @param optional
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private <T> ResponseEntity orElseReturn(Optional<T> optional, Long id) {
		if (optional == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Musica não encontrada");
		} else if (optional.isPresent()) {
			return optional.map(ResponseEntity::ok).get();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Musica não encontrada");
		}
	}
	
	/**
	 * Retorna todos as musicas cadastradas na database
	 * 
	 * @return lista de Musicas
	 */
	@GetMapping("filtro/{nomeMusica}")
	public @ResponseBody ResponseEntity buscaMusicasUsandoFiltro1(@PathVariable String nomeMusica) {
		try{
			
			if(nomeMusica.length() < 3) {
				return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body("Favor digitar mais que 3 caracteres");
			}
			
			List<Musica> musicas = repository.findByNomeLikeIgnoreCase(nomeMusica);
			if(musicas.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Musica não encontrada");
			}
			
			return ResponseEntity.ok().body(musicas);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

}
