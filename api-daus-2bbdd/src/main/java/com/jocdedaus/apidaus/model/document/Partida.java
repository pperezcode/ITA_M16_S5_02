package com.jocdedaus.apidaus.model.document;

import java.util.Random;

import javax.persistence.Id;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jocdedaus.apidaus.model.entity.Jugador;

@Document(collection = "Partides")
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
	
	@Transient
	public static final String SEQUENCE_NAME = "partides_sequence";
		
	@Id
	private Integer idPartida;
	
	@Field(name = "Dau1")
	private int dau1;
	
	@Field(name = "Dau2")
	private int dau2;
	
	@Field(name = "Resultat")
	private int resultat;
	
	@Field(name = "Victoria")
	private boolean victoria;
	
	@Field(name = "Jugador")
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
