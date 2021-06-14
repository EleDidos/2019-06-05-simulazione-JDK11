package it.polito.tdp.crimes.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;
import it.polito.tdp.crimes.model.EventoCoda.EventType;

public class Simulatore {
	
	//output
	private Integer malGestiti;
	
	//dati
	private Integer N;//agenti
	private PriorityQueue <EventoCoda>queue;
	private Distretto best;//minor criminalità --> ha la centrale
	private List<Agente> agenti=new ArrayList<Agente>();
	private Map <Integer, Distretto> idMap;
	private SimpleWeightedGraph <Distretto, DefaultWeightedEdge> graph;
	private EventsDao dao;
	private Integer year;
	private Long gestioneIntervento;
	private double distanzaMIN; //km ????
	
	public void run(Integer year, EventsDao dao, Integer N, Distretto best,Map <Integer, Distretto> idMap,SimpleWeightedGraph <Distretto, DefaultWeightedEdge> graph) {
		this.N=N;
		this.year=year;
		this.dao=dao;
		this.graph=graph;
		malGestiti=0;
		this.best=best;
		
		this.idMap=idMap;
		
		//tutti gli agenti nel distretto migliore della centrale
		//tutti liberi
		for(int i=1;i<=N;i++) {
			Agente a = new Agente(i,best);
			agenti.add(a);
		}
		
		//riempi la coda di crimini di quell'anno
		for(Event e: dao.loadEventsYear(year)) {
			LocalDateTime ldt = e.getReported_date();
			LocalTime lt = LocalTime.of(ldt.getHour(), ldt.getMinute(), ldt.getSecond());
			EventoCoda nuovo1 = new EventoCoda(EventType.CRIMINE,lt,e);
			queue.add(nuovo1);
			System.out.println(nuovo1);
		}
		
		
		while(!this.queue.isEmpty()) {
				EventoCoda ec= this.queue.poll();
				processEvent(ec);
		}//while
		
	}//run

	
	private void processEvent(EventoCoda ec) {
		switch(ec.getType()) {
			case CRIMINE:
				//scegli agente
				Agente scelto= this.scegliAgente(ec);
				
				//tutti gli agenti occupati
				if(scelto==null) {
					malGestiti++;
					break;
				}
				
				//quanto dura la gestione dell'evento?
				double prob;
				if(ec.getEvent().getOffense_category_id().equals("all_other_crimes")) {
						prob=Math.random();
						if(prob<=0.5) 
							gestioneIntervento=(long)3600; //1 ora
						else
							gestioneIntervento=(long)7200;//2ore
					
				}else {
					gestioneIntervento=(long)7200;//2ore
				}
				
				//tempo dell'agente di raggiungere il luogo
				//velocità=60km/s
				
				long seconds = (long) (distanzaMIN/60);
			
				//evento mal gestito, ci mette troppo tempo ad arrivare sul posto
				if(seconds/60>=15) {
					malGestiti++;
				}
				
				long tempoDiGestione=seconds+gestioneIntervento;
				
				//gestione dell'evento in tempo o no
				EventoCoda nuovo2 = new EventoCoda(EventType.GESTITO,ec.getTime().plus(tempoDiGestione,ChronoUnit.SECONDS),ec.getEvent());
				queue.add(nuovo2);
				System.out.println(nuovo2);
				nuovo2.setAgente(scelto);
				
				break;
				
				
			case GESTITO:
				Agente a = ec.getAgente();
				a.setFree(true);
				break;
			
			default:
				break;
				
		}//switch
		
		
	}//process
	
	
	/**
	 * Restituisce l'agente più vicino e libero all'ora dell'evento
	 */
	private Agente scegliAgente(EventoCoda ec) {
		boolean found=false;
		Agente scelto=new Agente(null,null);
		distanzaMIN=Integer.MAX_VALUE;
		Distretto distrettoCrimine = idMap.get(ec.getEvent().getDistrict_id());
		
		for(Agente a:agenti) {
			if(a.isFree()) { //trovo arco che unisce "a" al distretto del crimine per vederne la distanza
				found=true;
				DefaultWeightedEdge e= graph.getEdge(a.getDistretto(), distrettoCrimine);
				if(graph.getEdgeWeight(e)<distanzaMIN) {
					distanzaMIN=graph.getEdgeWeight(e);
					scelto=a;
				}
			}//if
				
		}//for
		
		scelto.setDistretto(distrettoCrimine);
		scelto.setFree(false);
		
		if(found==false)//sono tutti occupati
			return null;
		
		return scelto;
	}
	
	
	public Integer getMalGestiti() {
		return malGestiti;
	}

}
