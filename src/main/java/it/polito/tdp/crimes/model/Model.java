package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private SimpleWeightedGraph <Distretto, DefaultWeightedEdge> graph;
	private Map <Integer, Distretto> idMap;
	private EventsDao dao;
	private Integer year;
	private Simulatore sim;
	
	public Model(){
		idMap= new HashMap  <Integer, Distretto> ();
		dao= new EventsDao();
	}
	
	public void creaGrafo(Integer year) {
		this.year=year;
		graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		dao.loadAllVertici(idMap);
		Graphs.addAllVertices(graph, idMap.values());
		
		//eventi per ogni vertice
		//calcolo centro
		for(Distretto d: graph.vertexSet()) {
			dao.loadEvents(d,year);
			d.calcola_avg_lat();
			d.calcola_avg_lon();
		}
		
		for(Distretto d1 : this.graph.vertexSet()) {
			for(Distretto d2 : this.graph.vertexSet()) {
				if(!d1.equals(d2)) {
					if(this.graph.getEdge(d1, d2) == null) {
						
						Double distanzaMedia = LatLngTool.distance(new LatLng(d1.getAvgLat(),d2.getAvgLat()), 
																	new LatLng(d1.getAvgLon(),d2.getAvgLon()), 
																	LengthUnit.KILOMETER);
						
						Graphs.addEdgeWithVertices(this.graph, d1, d2, distanzaMedia);
						
					}
				}
			}
		}
		
		
	}
	
	public Integer getNVertici() {
		return graph.vertexSet().size();
	}
	
	public Integer getNArchi() {
		return graph.edgeSet().size();
	}
	
	public String getViciniTOT() {
		String result="";
		//aggiungo alla stringa risultato la stringa relativa ai vicini di quel distretto
		for(Distretto d: graph.vertexSet())
			result+="Vicini del distretto "+d.getID()+"\n"+this.getVicini(d);
		return result;
	}
	
	private String getVicini(Distretto d){
		String vicini="";
		DefaultWeightedEdge e;
		List <VicinoDistanza> vd = new ArrayList <VicinoDistanza>();
		
		for(Distretto di: Graphs.neighborListOf(graph, d)) {
			if(graph.getEdge(di, d)!=null)
				e=graph.getEdge(di, d);
			else
				e=graph.getEdge(d, di);
			vd.add(new VicinoDistanza(di,graph.getEdgeWeight(e)));
		}
		
		Collections.sort(vd);
		
		for(VicinoDistanza vdi: vd)
			vicini+=vdi.getVicino()+" - "+vdi.getDistanza()+"\n";
		return vicini;
	}
	
	
	public void simula(Integer N) {
		sim=new Simulatore();
		sim.run(N, this.getBestDistretto(),Map <Integer, Distretto> idMap);
		
	}
	
	
	private Distretto getBestDistretto() {
		Distretto best=new Distretto(0);
		Integer minCrimes=300000;
		
		for(Distretto di: idMap.values()) {
			if(di.getEventi().size()<minCrimes)
				minCrimes=di.getEventi().size();
				best=di;
		}
		return best;
	}
}
