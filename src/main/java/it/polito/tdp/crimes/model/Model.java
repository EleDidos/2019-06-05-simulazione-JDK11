package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private SimpleWeightedGraph <Distretto, DefaultWeightedEdge> graph;
	private Map <Integer, Distretto> idMap;
	private EventsDao dao;
	private Integer year;
	private List <Distretto> vertici;
	
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
		
		//per ogni coppia vertici trovo distanza centri
		vertici=new ArrayList <Distretto>(idMap.values());
		for(int i=0;i<vertici.size()-1;i++) {
			for(int j=i+1;j<vertici.size();j++) {
				double distanza = 
			}
		}
		
		
	}
	
	public Integer getNVertici() {
		return graph.vertexSet().size();
	}
	
	public Integer getNArchi() {
		return graph.edgeSet().size();
	}
}
