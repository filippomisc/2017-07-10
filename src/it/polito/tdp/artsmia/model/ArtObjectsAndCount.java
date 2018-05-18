package it.polito.tdp.artsmia.model;

public class ArtObjectsAndCount {
	
	private int artObjectIDp;
	private int artObjectIDa;
	private int count;
	
	public ArtObjectsAndCount() {
	}
	
	
	public ArtObjectsAndCount(int artObjectIDp, int artObjectIDa, int count) {
		super();
		this.artObjectIDp = artObjectIDp;
		this.artObjectIDa = artObjectIDa;
		this.count = count;
	}


	public int getArtObjectIDp() {
		return artObjectIDp;
	}


	public void setArtObjectIDp(int artObjectIDp) {
		this.artObjectIDp = artObjectIDp;
	}


	public int getArtObjectIDa() {
		return artObjectIDa;
	}


	public void setArtObjectIDa(int artObjectIDa) {
		this.artObjectIDa = artObjectIDa;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	@Override
	public String toString() {
		return "ArtObjectsAndCount [artObjectIDp=" + artObjectIDp + ", artObjectIDa=" + artObjectIDa + ", count="
				+ count + "]";
	}
	
	
	
	

}
