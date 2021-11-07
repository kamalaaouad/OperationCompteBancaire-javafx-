package metier.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import metier.IBanqueRemote;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.event.ActionEvent;

public class VirementMontantController {
	@FXML
	private TextField tfCodeEm;
	@FXML
	private TextField tfCodeRc;
	@FXML
	private TextField tfMontant;

	// Event Listener on Button.onAction
	@FXML
	public void onSnd(ActionEvent event) {
		Parent root;
		if(tfCodeEm.getText()!=null && tfCodeRc.getText()!=null && tfMontant.getText()!=null) {
			try {
				lookUpBanqueRemote().verement(Long.parseLong(tfCodeEm.getText()), Long.parseLong(tfCodeRc.getText()), Double.parseDouble(tfMontant.getText()));
				tfCodeEm.setText("");
				tfCodeRc.setText("");
				tfMontant.setText("");
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.showAndWait();
				((Node)event.getSource()).getScene().getWindow().hide();
				 root = FXMLLoader.load(getClass().getClassLoader().getResource("metier/view/GCompteView.fxml"));
		            Stage stage = new Stage();
		            stage.setTitle("Accuelle");
		            stage.setScene(new Scene(root));
		            stage.show();
			} catch (NumberFormatException | NamingException | IOException e) {
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
