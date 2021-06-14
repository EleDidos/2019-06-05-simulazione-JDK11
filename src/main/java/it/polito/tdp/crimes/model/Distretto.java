package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

public class Distretto implements Comparable<Distretto>{
	
	private Integer ID;
	private List <Event> eventi;
	private double avg_lon;
	private double avg_lat;
	
	public void calcola_avg_lon() {
		double tot=0.0;
		for(Event e: eventi)
			tot+=e.getGeo_lon();
		avg_lon=tot/eventi.size();
	}
	public double getAvgLon() {
		return avg_lon;
	}
	
	public void calcola_avg_lat() {
		double tot=0.0;
		for(Event e: eventi)
			tot+=e.getGeo_lat();
		avg_lat=tot/eventi.size();
	}
	public double getAvgLat() {
		return avg_lat;
	}
	
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public List<Event> getEventi() {
		return eventi;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Distretto other = (Distretto) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}
	public Distretto(Integer iD) {
		super();
		ID = iD;
		eventi= new ArrayList<Event>();
	}
	
	public void addEvent(Event e) {
		eventi.add(e);
	}
	
	@Override
	public int compareTo(Distretto other) {
		return this.ID-other.ID;
	}
	
	public String toString() {
		return "Distretto "+ID;
	}
	

}
