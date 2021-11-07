package metier.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import metier.IBanqueRemote;
import metier.entities.Compte;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

public class AddCompteController {
	@FXML
	private TextField tfsalary;
	@FXML
	private DatePicker dpdate;

	// Event Listener on Button.onAction
	@FXML
	public void onSave(ActionEvent event) {
		Parent root;
		if(tfsalary.getText()!="" && dpdate.getValue() !=null) {
			LocalDate localDate = dpdate.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			Date date = Date.from(instant);
			try {
				lookUpBanqueRemote().createCompte(new Compte(Double.parseDouble(tfsalary.getText()),date));
				tfsalary.setText("");
				dpdate.setValue(LocalDate.now());
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.showAndWait();
				((Node)event.getSource()).getScene().getWindow().hide();
				 root = FXMLLoader.load(getClass().getClassLoader().getResource("metier/view/GCompteView.fxml"));
		            Stage stage = new Stage();
		            stage.setTitle("Accuelle");
		            stage.setScene(new Scene(root));
		            stage.show();
			} catch (NamingException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static IBanqueRemote lookUpBanqueRemote() throws NamingException {
		Hashtable<Object, Object> jndiProperties = new Hashtable<Object, Object>();

		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context ctx = new InitialContext(jndiProperties);
		String appName="";
		String moduleName="BanqueCompteEjb";
		String beanName="BK";
		String remoteInterface=metier.IBanqueRemote.class.getName();
		String name ="ejb:"+appName+"/"+moduleName+"/"+beanName+"!"+remoteInterface;

		return  (metier.IBanqueRemote) ctx.lookup(name);

	}
}
