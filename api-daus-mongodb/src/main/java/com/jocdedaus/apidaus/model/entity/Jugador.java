package com.jocdedaus.apidaus.model.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "jugadors")
public class Jugador {
	
	public Jugador() {}
	
	public Jugador(String nom) {
		this.nom = nom.toUpperCase();
	}
	
	@Transient
	public static final String SEQUENCE_NAME = "jugadors_sequence";
	
	@Id
	private Integer idJugador;
	
	@Field(name = "Nom")
	private String nom;

	@Field(name = "DataRegistre")
	private LocalDateTime dataRegistre;
	
	@Field(name = "PercentatgeExit")
	private double percentatgeExit;
	
	public Integer getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(Integer idJugador) {
		this.idJugador = idJugador;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LocalDateTime getDataRegistre() {
		return dataRegistre;
	}

	public void setDataRegistre(LocalDateTime dataRegistre) {
		this.dataRegistre = dataRegistre;
	}

	public double getPercentatgeExit() {
		return percentatgeExit;
	}

	public void setPercentatgeExit(double percentatgeExit) {
		this.percentatgeExit = percentatgeExit;
	}

	@Override
	public String toString() {
		return "Usuari [idJugador=" + idJugador + ", nom=" + nom + ", dataRegistre=" + dataRegistre
				+ ", percentatgeExit=" + percentatgeExit + "]";
	}

}
