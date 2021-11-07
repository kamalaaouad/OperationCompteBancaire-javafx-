package metier.entities;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;


public class Compte implements Serializable {

	private Long code;
	private double solde;
	private Date dateCreation;
	private static final long serialVersionUID = 1L;

	public Compte() {
		super();
	}   
	
	public Compte(double solde, Date dateCreation) {
		super();
		this.solde = solde;
		this.dateCreation = dateCreation;
	}
	

	public Compte(Long code, double solde, Date dateCreation) {
		super();
		this.code = code;
		this.solde = solde;
		this.dateCreation = dateCreation;
	}

	public Long getCode() {
		return this.code;
	}

	public void setCode(Long code) {
		this.code = code;
	}   
	public double getSolde() {
		return this.solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}   
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
   
}
