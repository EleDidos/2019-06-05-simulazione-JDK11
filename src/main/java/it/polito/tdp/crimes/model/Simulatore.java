package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Simulatore {
	
	//output
	private Integer malGestiti;
	
	//dati
	private Integer N;//agenti
	private PriorityQueue <Event>queue;
	private Distretto best;//minor criminalità --> ha la centrale
	private List<Agente> agenti=new ArrayList<Agente>();
	private Map <Integer, Distretto> idMap;
	private SimpleWeightedGraph <Distretto, DefaultWeightedEdge> graph;
	
	public void run(Integer N, Distretto best,Map <Integer, Distretto> idMap,SimpleWeightedGraph <Distretto, DefaultWeightedEdge> graph) {
		this.N=N;
		this.graph=graph;
		malGestiti=0;
		this.best=best;
		queue=new PriorityQueue<Event>();
		this.idMap=idMap;
		
		//tutti gli agenti nel distretto migliore della centrale
		//tutti liberi
		for(int i=1;i<=N;i++) {
			Agente a = new Agente(i,best);
			agenti.add(a);
		}
		
		while(!this.queue.isEmpty()) {
				Event e = this.queue.poll();
				processEvent(e);
		}//while
		
	}//run

	
	private void processEvent(Event e) {
		//scegli agente
		Agente scelto= this.scegliAgente(idMap.get(e.getDistrict_id()), e.getReported_date());
		scelto.setE(e);
		//quanto dura la gestione dell'evento?
		double prob;
		switch(e.getOffense_category_id()) {
			case "all_other_crimes":
				prob=Math.random();
				if(prob<=0.5) {
					
				}
		}
		
	}//process
	
	
	/**
	 * Restituisce l'agente più vicino e libero all'ora dell'evento
	 */
	private Agente scegliAgente(Distretto daRaggiungere, LocalDateTime ldt) {
		Agente scelto=new Agente(null,null);
		double distanzaMIN=Integer.MAX_VALUE;
		DefaultWeightedEdge e;
		
		LocalTime lt= LocalTime.of(ldt.getHour(),ldt.getMinute() );
		for(Agente a:agenti) {
			if(a.isFree()) { //trovo arco che unisce "a" al distretto per vederne la distnza
				if(graph.getEdge(a.getDistretto(), daRaggiungere)!=null)
					e= graph.getEdge(a.getDistretto(), daRaggiungere);
				else
					e= graph.getEdge(daRaggiungere, a.getDistretto());
				if(graph.getEdgeWeight(e)<distanzaMIN) {
					distanzaMIN=graph.getEdgeWeight(e);
					scelto=a;
				}
			}//if
				
		}//for
		
		scelto.setDistretto(daRaggiungere);
		scelto.setFree(false);
		
		return scelto;
	}

}
