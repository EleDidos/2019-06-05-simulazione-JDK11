package it.polito.tdp.crimes.model;

import java.time.LocalTime;

public class Agente {
	
	private Integer agenteID;
	private Distretto distretto;
	private LocalTime arrivo;
	private Event e;
	private boolean free;
	
	public Agente(Integer agenteID, Distretto distretto) {
		super();
		this.agenteID = agenteID;
		this.distretto = distretto;
		free=true;
	}

	public Integer getAgenteID() {
		return agenteID;
	}

	public Distretto getDistretto() {
		return distretto;
	}

	public LocalTime getArrivo() {
		return arrivo;
	}

	public Event getE() {
		return e;
	}

	public boolean isFree() {
		return free;
	}

	public void setDistretto(Distretto distretto) {
		this.distretto = distretto;
	}

	public void setArrivo(LocalTime arrivo) {
		this.arrivo = arrivo;
	}

	public void setE(Event e) {
		this.e = e;
	}

	public void setFree(boolean free) {
		this.free = free;
	}
	
	
	
	

}
