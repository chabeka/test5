package fr.urssaf.image.commons.webservice.rpc.aed.service.modele;

import fr.urssaf.image.commons.webservice.rpc.aed.modele.holders.ErreurHolder;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.holders.ListeDocumentsHolder;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.holders.ListeDocumentsSynchroHolder;


public class SynchroniseLotLIFinModele {

	private ListeDocumentsHolder listeDocumentsManquants;
	private ListeDocumentsSynchroHolder listeDocumentsInconnus;
	private ErreurHolder erreur;

	public SynchroniseLotLIFinModele(
			ListeDocumentsHolder listeDocumentsManquants,
			ListeDocumentsSynchroHolder listeDocumentsInconnus,
			ErreurHolder erreur) {
		this.listeDocumentsManquants = listeDocumentsManquants;
		this.listeDocumentsInconnus = listeDocumentsInconnus;
		this.erreur = erreur;
	}

	public ListeDocumentsHolder getListeDocumentsManquants() {
		return listeDocumentsManquants;
	}

	public ListeDocumentsSynchroHolder getListeDocumentsInconnus() {
		return listeDocumentsInconnus;
	}

	public ErreurHolder getErreur() {
		return erreur;
	}

}