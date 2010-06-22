/**
 * GedImagePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package fr.urssaf.image.commons.webservice.rpc.ged.service;

import java.rmi.RemoteException;

import fr.urssaf.image.commons.webservice.rpc.ged.modele.AuthInfo;
import fr.urssaf.image.commons.webservice.rpc.ged.modele.CategorieTypeZoneWS;
import fr.urssaf.image.commons.webservice.rpc.ged.modele.FichierWS;
import fr.urssaf.image.commons.webservice.rpc.ged.modele.IndexValeur;
import fr.urssaf.image.commons.webservice.rpc.ged.modele.TypeZoneWS;

public interface GedImageService {

	/**
	 * Mise à jour des index d'un document.
	 */
	public boolean updateIndexDocumentGED(AuthInfo login, String base,
			String idDocument, IndexValeur[] listeIndexValeur)
			throws RemoteException;

	/**
	 * Mise à jour des index d'un document.
	 */
	public String moveDocumentGED(AuthInfo login, String baseOrig,
			String idDocument, String baseDest, IndexValeur[] listeIndexValeur)
			throws RemoteException;

	/**
	 * Insertion d'un document.
	 */
	public String insertDocumentGED(AuthInfo login, String base,
			IndexValeur[] listeIndexValeur, FichierWS file)
			throws RemoteException;

	/**
	 * Supprime un document
	 */
	public boolean deleteDocumentGED(AuthInfo login, String base,
			String idDocument) throws RemoteException;

	/**
	 * Liste des type zone d'une base.
	 */
	public TypeZoneWS[] getTypesZonesBase(AuthInfo login, String base)
			throws RemoteException;

	/**
	 * Liste des types zones et des catégories pour une base
	 */
	public CategorieTypeZoneWS[] getCategorieTypesZonesBase(AuthInfo login,
			String base) throws RemoteException;

	/**
	 * Pong
	 */
	public String ping() throws RemoteException;

	/**
	 * Liste des des dictionnaires pour une base et un type zone
	 */
	public String[] getDictionnaire(AuthInfo login, String base,
			String typezone, String masque) throws RemoteException;
}
