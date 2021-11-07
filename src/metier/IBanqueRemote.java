package metier;

import java.util.List;

import javax.ejb.Remote;

import metier.entities.Compte;

@Remote
public interface IBanqueRemote {
	
	Compte createCompte(Compte c);
	Compte findCompteByCode(Long code);
	List<Compte> findListCompte();
	void verser(Long code, double montant);
	void retirer(Long code, double montant);
	void verement(Long code1, Long code2,double montant);

}
