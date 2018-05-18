package it.polito.tdp.artsmia.model;

public class ArtObjectsAndCount {
	
	private int artObjectID;
	private int count;
	
	public ArtObjectsAndCount() {
	}
	
	
	public ArtObjectsAndCount(int artObjectID, int count) {
		super();
		this.artObjectID = artObjectID;
		this.count = count;
	}



	public int getArtObjectID() {
		return artObjectID;
	}


	public void setArtObjectID(int artObjectID) {
		this.artObjectID = artObjectID;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	@Override
	public String toString() {
		return "ArtObjectsAndCount [artObjectID=" + artObjectID + ", count="
				+ count + "]";
	}
	
	
	
	

}
