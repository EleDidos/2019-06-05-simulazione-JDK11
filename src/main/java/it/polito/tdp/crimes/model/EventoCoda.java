package it.polito.tdp.crimes.model;

import java.time.LocalTime;

import javafx.event.EventType;

public class EventoCoda implements Comparable <EventoCoda>{
	
	//Ogni Event può:
	//1. accadere - quando succede il crimine
	//2. crimine gestito e poliziotto torna free
	
	public enum EventType{
		CRIMINE,
		
		GESTITO
	}
	
	private LocalTime time; //ora e minuti
	private EventType type;
	private Event event;
	private Agente agente; 
	
	public EventoCoda(EventType type,LocalTime time, Event event) {
		super();
		this.time = time;
		this.type = type;
		this.event=event;
	}
	
	


	public Agente getAgente() {
		return agente;
	}




	public void setAgente(Agente agente) {
		this.agente = agente;
	}




	@Override 
	public int compareTo(EventoCoda other) {
		return this.time.compareTo(other.time);
	}


	public LocalTime getTime() {
		return time;
	}


	public EventType getType() {
		return type;
	}


	public Event getEvent() {
		return event;
	}
	
	public String toString() {
		return time+" - "+type;
	}
	

}
