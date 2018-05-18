package it.polito.tdp.artsmia.model;

import java.util.*;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();
		
		m.creaGrafo();
		
		List<ArtObject> list = m.getArtObjects();
		for(ArtObject ao :list) {
			int numComp = m.trovaComponenteConnessa(ao.getId());
			System.out.println("la componente connessa che contiene il vertice " + ao.getId() +" ha " + numComp + " vertici");
		}
	}

}
