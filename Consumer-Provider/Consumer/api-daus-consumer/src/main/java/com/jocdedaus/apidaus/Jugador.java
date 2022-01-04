package com.jocdedaus.apidaus;

import java.time.LocalDateTime;

public class Jugador {
	
	public Jugador() {}
	
	public Jugador(String nom) {
		this.nom = nom.toUpperCase();
	}
	
	private Integer idJugador;

	private String nom;

	private LocalDateTime dataRegistre;

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
