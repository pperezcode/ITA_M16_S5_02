package com.jocdedaus.apidaus.model.entity;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "Partides")
@Entity
@Table(name = "Partides")
public class Partida {

	public Partida() {
		this.dau1 = (new Random()).nextInt(6-1+1) + 1;
		this.dau2 = (new Random()).nextInt(6-1+1) + 1;
		this.resultat = dau1 + dau2;
		this.victoria = false;
		if (this.resultat == 7) {
			this.victoria = true;
		}	
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdPartida")
	private Integer idPartida;
	
	@Column(name = "Dau1")
	private int dau1;
	
	@Column(name = "Dau2")
	private int dau2;
	
	@Column(name = "Resultat")
	private int resultat;
	
	@Column(name = "Victoria")
	private boolean victoria;
	
	@ManyToOne
	@JoinColumn(name = "IdJugador", nullable = false)
	@JsonIgnore
	private Jugador jugador;

	public Integer getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}

	public int getDau1() {
		return dau1;
	}

	public void setDau1(int dau1) {
		this.dau1 = dau1;
	}

	public int getDau2() {
		return dau2;
	}

	public void setDau2(int dau2) {
		this.dau2 = dau2;
	}

	public int getResultat() {
		return resultat;
	}

	public void setResultat(int resultat) {
		this.resultat = resultat;
	}

	public boolean isVictoria() {
		return victoria;
	}

	public void setVictoria(boolean victoria) {
		this.victoria = victoria;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	@Override
	public String toString() {
		return "Partida [idPartida=" + idPartida + ", dau1=" + dau1 + ", dau2=" + dau2 + ", resultat=" + resultat
				+ ", victoria=" + victoria + ", jugador=" + jugador + "]";
	}

}
