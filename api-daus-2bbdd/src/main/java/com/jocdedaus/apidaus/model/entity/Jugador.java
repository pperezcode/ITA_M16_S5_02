package com.jocdedaus.apidaus.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Jugadors")
@Entity
@Table(name = "Jugadors")
public class Jugador {
	
	public Jugador() {}
	
	public Jugador(String nom) {
		this.nom = nom.toUpperCase();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdJugador")
	private Integer idJugador;
	
	@Column(name = "Nom")
	private String nom;

	
	@Column(name = "DataRegistre")
	private LocalDateTime dataRegistre;
	
	@Column(scale = 2, name = "PercentatgeExit")
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
