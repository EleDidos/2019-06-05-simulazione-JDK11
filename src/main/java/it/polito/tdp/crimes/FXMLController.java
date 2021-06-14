/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    LocalDate ld;

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	Integer year;
    	try {
    		year=boxAnno.getValue();
    	}catch(NullPointerException npe) {
    		txtResult.setText("Scegliere un anno");
    		return;
    	}
    	
    	model.creaGrafo(year);
    	
    	txtResult.appendText("Caratteristiche del GRAFO:\n#VERTICI = "+model.getNVertici()+"\n#ARCHI = "+model.getNArchi());
    	
    	txtResult.appendText("\nVICINI:\n"+model.getViciniTOT());
    	
    	//MESE
    	Integer[]mese = {1,2,3,4,5,6,7,8,9,10,11,12};
    	boxMese.getItems().addAll(mese);
    	
    	Integer month;
    	try {
    		month=boxMese.getValue();
    	}catch(NullPointerException npe) {
    		txtResult.setText("Scegliere un mese");
    		return;
    	}
    	
    	//GIORNO
    	ArrayList <Integer> giorno=new ArrayList<Integer>();
    	if(month==1||month==3||month==5||month==7||month==8||month==10||month==12) {
    		for(int i=1;i<=31;i++)
    			giorno.add(i);
    	}else if(year%4==0 & month==2) {
    		for(int i=1;i<=29;i++)
    			giorno.add(i);
    	}else if(year%4!=0 & month==2) {
        		for(int i=1;i<=28;i++)
        			giorno.add(i);
    	}else {
    		for(int i=1;i<=30;i++)
    			giorno.add(i);
    	}
    	boxGiorno.getItems().addAll(giorno);
    	Integer day;
    	try {
    		day=boxGiorno.getValue();
    	}catch(NullPointerException npe) {
    		txtResult.setText("Scegliere un giorno");
    		return;
    	}
    	
    	ld = LocalDate.of(year, month+1, day);
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	Integer N;
    	
    	try {
    		N=Integer.parseInt(txtN.getText());
    	}catch(NumberFormatException nfe) {
    		txtResult.setText("Scegliere un numero intero tra 1 e 10");
    		return;
    	}
    	catch(NullPointerException npe) {
    		txtResult.setText("Scegliere un mese");
    		return;
    	}
    	if(N<1||N>10) {
    		txtResult.setText("Scegliere un numero intero tra 1 e 10");
    		return;
    	}
    	
    	Integer malGestiti = model.simula(N);
    	txtResult.appendText("Il numero di interventi malgestiti è: "+malGestiti);
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	Integer[] anni = {2014,2015,2016, 2017};
    	boxAnno.getItems().addAll(anni);
    }
}
