package it.polito.tdp.crimes.model;

public class VicinoDistanza implements Comparable <VicinoDistanza>{
	
	private Distretto vicino;
	private double distanza;
	public Distretto getVicino() {
		return vicino;
	}
	public void setVicino(Distretto vicino) {
		this.vicino = vicino;
	}
	public double getDistanza() {
		return distanza;
	}
	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}
	public VicinoDistanza(Distretto vicino, double distanza) {
		super();
		this.vicino = vicino;
		this.distanza = distanza;
	}
	
	@Override
	public int compareTo(VicinoDistanza other) {
		return (int)(this.distanza-other.distanza);
	}

}
