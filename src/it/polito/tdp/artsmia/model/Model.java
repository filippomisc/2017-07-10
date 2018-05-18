package it.polito.tdp.artsmia.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;


public class Model {

	Graph<ArtObject, DefaultWeightedEdge> graph;
	List<ArtObject> artObjects;
	
	
	/**
	 * popola la lista artObjects (leggendo dal DB) e crea il grafo.
	 */
	public void creaGrafo() {
		
		//leggi la lista degli oggetti dal DB
		ArtsmiaDAO dao = new ArtsmiaDAO();
		this.artObjects = dao.listObjects();
		
		//creare il grafo
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		
		//aggiungere tutti i vertici
		Graphs.addAllVertices(this.graph, this.artObjects);
		
		//aggiungere gli archi pesati
		
		
		/* VERSIONE 1 - MENO EFFICIENTE
		for(ArtObject aop : this.artObjects) {
			for(ArtObject aoa : this.artObjects) {
				if(!aop.equals(aoa) && aop.getId()<aoa.getId()) {//con la prima condizione escludo coppie ao ao per escludere i loop
																 //con la seconda condizione evito che le coppie vengano ripetute due volte (da fare quando gli archi non sono orientati)
					int peso = calcolaPeso(aop,aoa);
					
					
					if(peso!=0) {
//						DefaultWeightedEdge e = this.graph.addEdge(aop, aoa);//verra eseguita 7 miliardi di volte (85.000^2)
//						graph.setEdgeWeight(e, peso);

						//facciamo un testo da stampare
//						System.out.format("(%d,%d) peso %d\n", aop.getId(), aoa.getId(), peso);
						System.out.println("peso: " + aop.getId() +" " + aoa.getId() + " " + peso);

						
						Graphs.addEdge(this.graph, aop, aoa, peso);
					}
					
				}
			}
		}*/
		
		
		
		//VERSIONE 2 - PIU EFFICIENTE
		
		for(ArtObject ao : this.artObjects) {
			
			List<ArtObjectsAndCount> connessi = dao.ListObjectAndCount(ao);
			
			for(ArtObjectsAndCount aoc : connessi) {
				
				ArtObject sorg = new ArtObject(aoc.getArtObjectIDp(), null, null, null, 0, null, null, null, null, null, 0, null, null, null, null, null);

				ArtObject dest = new ArtObject(aoc.getArtObjectIDa(), null, null, null, 0, null, null, null, null, null, 0, null, null, null, null, null);
				
				Graphs.addEdge(this.graph, sorg, dest, aoc.getCount());
				System.out.format("(%d,%d) peso %d\n", sorg.getId(), dest.getId(), aoc.getCount());
			}
		}
		
	}


	private int calcolaPeso(ArtObject aop, ArtObject aoa) {

		ArtsmiaDAO dao = new ArtsmiaDAO();
		int weight = dao.caloclaExhibitionComuni(aop,aoa);
		return weight;
	}
}
