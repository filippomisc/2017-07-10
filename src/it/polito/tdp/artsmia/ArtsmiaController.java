/**
 * Sample Skeleton for 'Artsmia.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model m;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxLUN"
	private ChoiceBox<?> boxLUN; // Value injected by FXMLLoader

	@FXML // fx:id="btnCalcolaComponenteConnessa"
	private Button btnCalcolaComponenteConnessa; // Value injected by FXMLLoader

	@FXML // fx:id="btnCercaOggetti"
	private Button btnCercaOggetti; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalizzaOggetti"
	private Button btnAnalizzaOggetti; // Value injected by FXMLLoader

	@FXML // fx:id="txtObjectId"
	private TextField txtObjectId; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doAnalizzaOggetti(ActionEvent event) {
		this.txtObjectId.setDisable(false);
		this.btnCalcolaComponenteConnessa.setDisable(false);
		
		
		m.creaGrafo();
		
		this.txtResult.setText("grafo creato: " + m.getGraphNumVertices() + " vertici, " + m.getGraphNumEdges() + " archi\n");
		
//		txtResult.setText(String.format("Grafo creato: %d vertici, %s archi\n", model.getGraphNumVertices(), model.getGraphNumEdges()));
	}

	@FXML
	void doCalcolaComponenteConnessa(ActionEvent event) {
		//ricordarsi di fare i controlli nel controller! (ad esempio se l'id esiste)
		
		String idObjString = this.txtObjectId.getText();//da convertire in int  
		int idObj;
		
		try {
			
			idObj = Integer.parseInt(idObjString);	
			
		}catch(NumberFormatException e){
			this.txtResult.appendText("il formato dell'id inserito non è corretto\n");
			return;
			//break;
		}
		
		
		if(!m.idIsValid(idObj)) {
				this.txtResult.appendText(String.format("l'oggetto con quell'ID %d non è presente nel DataBase\n", idObj));
				return;
			}	
		
	
			int numComponentiConnesse = this.m.trovaComponenteConnessa(idObj);
			
			this.txtResult.appendText(String.format("La componente connessa che contiene il vertice %d ha %d vertici\n", idObj, numComponentiConnesse));
			
	}

	@FXML
	void doCercaOggetti(ActionEvent event) {
		txtResult.setText("doCercaOggetti");
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

	}
	
	public void setModel(Model model) {
		this.m = model;
		}
}
