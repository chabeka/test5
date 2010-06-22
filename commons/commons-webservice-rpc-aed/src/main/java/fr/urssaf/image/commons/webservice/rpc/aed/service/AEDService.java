/**
 * AEPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package fr.urssaf.image.commons.webservice.rpc.aed.service;

import java.rmi.RemoteException;

import fr.urssaf.image.commons.webservice.rpc.aed.modele.Document;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.DocumentGed;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.DocumentGedAED;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.DocumentRejete;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.DocumentSupprime;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.DocumentSynchro;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.Erreur;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.LotAED;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.Mode;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.PreDocument;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.DispatchLotModele;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.InitLotModele;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.ParametrageTransfertModele;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.SynchroniseLotLIFinModele;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.SynchroniseLotLIIniModele;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.SynchroniseLotModele;


public interface AEDService {

	/**
	 * Permet de tester la connexion aux services WEB.
	 */
	public String ping() throws RemoteException;

	/**
	 * Initialisation du lot et recuperation du mode de transfert.
	 */
	public InitLotModele initLot(String idTransfert, String idLot,
			String nomLot, PreDocument[] listeDocuments,
			String nombreDocuments, Mode modeTest) throws RemoteException;

	/**
	 * Déclaration d'une nouvelle copie de document opérée dans une application
	 * cliente SAEL.
	 */
	public boolean declareNouvellesCopies(Document[] listeDocuments,
			String progTraitement, String operation, Mode modeTest)
			throws RemoteException;

	/**
	 * Traitement des documents provenant de la GED (GED deportee)
	 */
	public Erreur gedDeportee(String idOrganisme,
			DocumentGed[] listeDocumentGed, Mode modeTest)
			throws RemoteException;

	/**
	 * Recuperation du parametrage du transfert
	 */
	public ParametrageTransfertModele getParametrageTransfert(
			String applicationSource, String IP) throws RemoteException;

	/**
	 * Methode de dispatch et synchronisation des referentiels chaine
	 * d'acquisition - AE
	 */
	public DispatchLotModele dispatchLot(String applicationSource, String IP,
			String idLot, DocumentSynchro[] listeDocuments, Mode modeTest)
			throws RemoteException;

	/**
	 * Methode de synchronisation des referentiels chaine d'acquisition - AE
	 */
	public SynchroniseLotModele synchroniseLot(String applicationSource,
			String IP, String idLot, DocumentSynchro[] listeDocuments,
			Mode modeTest) throws RemoteException;

	/**
	 * Methode de synchronisation des lots GED a l archivage
	 */
	public Erreur synchroniseGed(DocumentGedAED[] listeDocuments, Mode modeTest)
			throws RemoteException;

	/**
	 * Methode de synchronisation des referentiels chaine d'acquisition - AE
	 */
	public SynchroniseLotLIIniModele synchroniseLotLIIni(
			String applicationSource, String IP, String idLot,
			String idLotFils, String typeDocument,
			DocumentSynchro[] listeDocuments, Mode modeTest)
			throws RemoteException;

	/**
	 * Methode de synchronisation des referentiels chaine d'acquisition - AE
	 */
	public Erreur correspondancesID(String[] listeID, Mode modeTest)
			throws RemoteException;

	/**
	 * Methode de synchronisation des referentiels chaine d'acquisition - AE
	 */
	public SynchroniseLotLIFinModele synchroniseLotLIFin(
			String applicationSource, String IP, String idLot,
			String idLotFils, String typeDocument,
			DocumentSynchro[] listeDocuments, Mode modeTest)
			throws RemoteException;

	/**
	 * Permet de rejeter des documents
	 */
	public Erreur rejetDocuments(String applicationSource, String IP,
			String idLot, DocumentRejete[] listeDocumentsRejetes)
			throws RemoteException;

	/**
	 * Methode de declaration de la suppression d'une affaire WATT
	 */
	public Erreur suppressionDocuments(
			DocumentSupprime[] listeDocumentsSupprimes, Mode modeTest)
			throws RemoteException;

	/**
	 * Archivage d'un lot de GED
	 */
	public LotAED archivageLot(String ip, LotAED lot) throws RemoteException;

	/**
	 * Synchronisation d'un lot
	 */
	public LotAED synchronisationLot(String ip, LotAED lot)
			throws RemoteException;
}
