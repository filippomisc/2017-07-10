package it.polito.tdp.artsmia.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

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
		addEdges();
		System.out.format("Grafo creato: %d vertici, %d archi\n", graph.vertexSet().size(), graph.edgeSet().size());
	}
		
		
		
		
		/* VERSIONE 1 - MENO EFFICIENTE
		private void addEdges(){
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
		}
		}*/
		
		
		
		//VERSIONE 2 - PIU EFFICIENTE
		private void addEdges() {
			
			ArtsmiaDAO dao = new ArtsmiaDAO();
			
			for(ArtObject ao : this.artObjects) {
				
				List<ArtObjectsAndCount> connessi = dao.ListObjectAndCount(ao);
				
				for(ArtObjectsAndCount aoc : connessi) {

				ArtObject dest = new ArtObject(aoc.getArtObjectID(), null, null, null, 0, null, null, null, null, null, 0, null, null, null, null, null);

				Graphs.addEdge(this.graph, ao, dest, aoc.getCount());
//				System.out.format("(%d,%d) peso %d\n", ao.getId(), dest.getId(), aoc.getCount());
				}
			}
		}


	private int calcolaPeso(ArtObject aop, ArtObject aoa) {

		ArtsmiaDAO dao = new ArtsmiaDAO();
		int weight = dao.caloclaExhibitionComuni(aop,aoa);
		return weight;
	}




	public int getGraphNumVertices() {
		return this.graph.vertexSet().size();
	}




	public Object getGraphNumEdges() {
		return this.graph.edgeSet().size();
	}



	//da compleatare
	public int trovaComponenteConnessa(int idObj) {
		//trova il vertice di partenza
		ArtObject start = null;
		for(ArtObject ao : this.artObjects) {
			if(ao.getId()==idObj) {
				start = ao;
				break;
			}
		}
		if(start==null)
			throw new IllegalArgumentException("vertice " + idObj + "non esistente");
		//visita il grafo
		Set<ArtObject> visitati = new HashSet<>();
		
		//imposto la visita in ampiezza (o in profondità)
		DepthFirstIterator<ArtObject, DefaultWeightedEdge> dfv = new DepthFirstIterator<>(this.graph, start);//imposto quale grafo visitare (this.graph) e da quale vertice iniziare (start)
		while(dfv.hasNext())//lo stesso che c'è nel DAO
			visitati.add(dfv.next());
		
		//conta gli elementi
		int result = visitati.size();

		return result;
	}

	//da completare (verificare se serva veramente)
//	public artObjcet getArtObjectByID(int id) {
//		return null;
//		}

	//da completare
	public boolean idIsValid(int idObj) {
		if(this.artObjects==null)
			return false;
		
		for(ArtObject ao : this.artObjects) {
			if(ao.getId()==idObj)
				return true;
		}
		return false;
		
	}


	public List<ArtObject> getArtObjects() {
		return artObjects;
	}


}
