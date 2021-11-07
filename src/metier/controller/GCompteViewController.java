package metier.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import metier.IBanqueLocal;
import metier.entities.Compte;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

public class GCompteViewController implements Initializable {
	@FXML
	private TableView<Compte> tableCompte;
	@FXML
	private TableColumn<Compte, Long> colCode;
	@FXML
	private TableColumn<Compte, Double> colSalaire;
	@FXML
	private TableColumn<Compte, Date> colDate;
	
	private ObservableList<Compte> observables =FXCollections.observableArrayList();

	// Event Listener on Button.onAction
	@FXML
	public void onAddCompte(ActionEvent event) {
		Parent root;
        try {
        	((Node)event.getSource()).getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("metier/view/AddCompte.fxml"));
            Stage stage = new Stage();
            stage.setTitle("List Of ADD ACCOUNT");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	// Event Listener on Button.onAction
	@FXML
	public void onVerserMontant(ActionEvent event) {
		Parent root;
        try {
        	((Node)event.getSource()).getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("metier/view/VerserMontant.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Verser Your Amount");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	// Event Listener on Button.onAction
	@FXML
	public void onRetirerMontant(ActionEvent event) {
		Parent root;
        try {
        	((Node)event.getSource()).getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("metier/view/RetierMontant.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Draw up Your amount");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	// Event Listener on Button.onAction
	@FXML
	public void onVirementClicked(ActionEvent event) {
		Parent root;
        try {
        	((Node)event.getSource()).getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("metier/view/VirementMontant.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Send Your Money");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	public void loadDate() {
		tableCompte.getItems().clear();
		try {
			Hashtable<Object, Object> jndiProperties = new Hashtable<Object, Object>();

			jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
			jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
			final Context ctx = new InitialContext(jndiProperties);
			String appName="";
			String moduleName="BanqueCompteEjb";
			String beanName="BK";
			String remoteInterface=metier.IBanqueRemote.class.getName();
			String name ="ejb:"+appName+"/"+moduleName+"/"+beanName+"!"+remoteInterface;
			
			metier.IBanqueRemote proxy = (metier.IBanqueRemote) ctx.lookup(name);
			
	        
			for(Compte m:proxy.findListCompte()) {
				observables.add(new Compte(m.getCode(),m.getSolde(),m.getDateCreation()));
			}
			colCode.setCellValueFactory(new PropertyValueFactory<Compte,Long>("code"));
			colSalaire.setCellValueFactory(new PropertyValueFactory<Compte,Double>("solde"));
			colDate.setCellValueFactory(new PropertyValueFactory<Compte,Date>("dateCreation"));
			tableCompte.setItems(observables);
		}catch (NamingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loadDate();
		
	}
}
