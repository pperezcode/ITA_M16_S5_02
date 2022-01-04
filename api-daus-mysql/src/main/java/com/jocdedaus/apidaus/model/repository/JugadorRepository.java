package com.jocdedaus.apidaus.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jocdedaus.apidaus.model.entity.Jugador;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

	boolean existsByNom(String nom);
	
	Jugador findFirst1ByOrderByPercentatgeExitDesc();
	
	Jugador findFirst1ByOrderByPercentatgeExitAsc();

	List<Jugador> findAllByOrderByPercentatgeExitDesc();
}
