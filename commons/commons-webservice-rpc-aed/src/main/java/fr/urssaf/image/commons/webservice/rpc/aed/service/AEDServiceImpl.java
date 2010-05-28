package fr.urssaf.image.commons.webservice.rpc.aed.service;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.BooleanHolder;
import javax.xml.rpc.holders.StringHolder;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.webservice.rpc.aed.modele.AEPortType;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.AEServiceLocator;
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
import fr.urssaf.image.commons.webservice.rpc.aed.modele.holders.ErreurHolder;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.holders.ListeDocumentsHolder;
import fr.urssaf.image.commons.webservice.rpc.aed.modele.holders.ListeDocumentsSynchroHolder;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.DispatchLotModele;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.InitLotModele;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.ParametrageTransfertModele;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.SynchroniseLotLIFinModele;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.SynchroniseLotLIIniModele;
import fr.urssaf.image.commons.webservice.rpc.aed.service.modele.SynchroniseLotModele;

public class AEDServiceImpl implements AEDService {

	protected static final Logger log = Logger.getLogger(AEDServiceImpl.class);

	private AEPortType port;

	public AEDServiceImpl() {
		fr.urssaf.image.commons.webservice.rpc.aed.modele.AEService service = new AEServiceLocator();
		try {
			port = service.getAEPort();
		} catch (ServiceException e) {
			log.debug(e.getMessage(), e.fillInStackTrace());
		}
	}

	@Override
	public LotAED archivageLot(String ip, LotAED lot) throws RemoteException {
		return port.archivageLot(ip, lot);
	}

	@Override
	public Erreur correspondancesID(String[] listeID, Mode modeTest)
			throws RemoteException {
		return port.correspondancesID(listeID, modeTest);
	}

	@Override
	public boolean declareNouvellesCopies(Document[] listeDocuments,
			String progTraitement, String operation, Mode modeTest)
			throws RemoteException {
		return port.declareNouvellesCopies(listeDocuments, progTraitement,
				operation, modeTest);
	}

	@Override
	public DispatchLotModele dispatchLot(String applicationSource, String IP,
			String idLot, DocumentSynchro[] listeDocuments, Mode modeTest)
			throws RemoteException {

		ListeDocumentsHolder listeDocumentsManquants = new ListeDocumentsHolder();
		ListeDocumentsSynchroHolder listeDocumentsInconnus = new ListeDocumentsSynchroHolder();
		ErreurHolder erreur = new ErreurHolder();

		port.dispatchLot(applicationSource, IP, idLot, listeDocuments,
				modeTest, listeDocumentsManquants, listeDocumentsInconnus,
				erreur);

		return new DispatchLotModele(listeDocumentsManquants,
				listeDocumentsInconnus, erreur);

	}

	@Override
	public Erreur gedDeportee(String idOrganisme,
			DocumentGed[] listeDocumentGed, Mode modeTest)
			throws RemoteException {
		return port.gedDeportee(idOrganisme, listeDocumentGed, modeTest);
	}

	@Override
	public ParametrageTransfertModele getParametrageTransfert(
			String applicationSource, String IP) throws RemoteException {

		StringHolder typeTransfert = new StringHolder();
		StringHolder urlFTP = new StringHolder();
		StringHolder login = new StringHolder();
		StringHolder password = new StringHolder();
		StringHolder chemin = new StringHolder();
		ErreurHolder erreur = new ErreurHolder();
		StringHolder idTransfert = new StringHolder();

		port.getParametrageTransfert(applicationSource, IP, typeTransfert,
				urlFTP, login, password, chemin, erreur, idTransfert);

		return new ParametrageTransfertModele(typeTransfert, urlFTP, login,
				password, chemin, erreur, idTransfert);

	}

	@Override
	public InitLotModele initLot(String idTransfert, String idLot,
			String nomLot, PreDocument[] listeDocuments,
			String nombreDocuments, Mode modeTest) throws RemoteException {

		BooleanHolder isLotCree = new BooleanHolder();
		ListeDocumentsHolder listeDocuments2 = new ListeDocumentsHolder();
		ErreurHolder erreur = new ErreurHolder();

		port.initLot(idTransfert, idLot, nomLot, listeDocuments,
				nombreDocuments, modeTest, isLotCree, listeDocuments2, erreur);

		return new InitLotModele(isLotCree, listeDocuments2, erreur);

	}

	@Override
	public String ping() throws RemoteException {
		return port.ping();
	}

	@Override
	public Erreur rejetDocuments(String applicationSource, String IP,
			String idLot, DocumentRejete[] listeDocumentsRejetes)
			throws RemoteException {

		return port.rejetDocuments(applicationSource, IP, idLot,
				listeDocumentsRejetes);
	}

	@Override
	public Erreur suppressionDocuments(
			DocumentSupprime[] listeDocumentsSupprimes, Mode modeTest)
			throws RemoteException {
		return port.suppressionDocuments(listeDocumentsSupprimes, modeTest);
	}

	@Override
	public LotAED synchronisationLot(String ip, LotAED lot)
			throws RemoteException {
		return port.synchronisationLot(ip, lot);
	}

	@Override
	public Erreur synchroniseGed(DocumentGedAED[] listeDocuments, Mode modeTest)
			throws RemoteException {
		return port.synchroniseGed(listeDocuments, modeTest);
	}

	@Override
	public SynchroniseLotModele synchroniseLot(String applicationSource,
			String IP, String idLot, DocumentSynchro[] listeDocuments,
			Mode modeTest) throws RemoteException {

		ListeDocumentsHolder listeDocumentsManquants = new ListeDocumentsHolder();
		ListeDocumentsSynchroHolder listeDocumentsInconnus = new ListeDocumentsSynchroHolder();
		ErreurHolder erreur = new ErreurHolder();

		port.synchroniseLot(applicationSource, IP, idLot, listeDocuments,
				modeTest, listeDocumentsManquants, listeDocumentsInconnus,
				erreur);

		return new SynchroniseLotModele(listeDocumentsManquants,
				listeDocumentsInconnus, erreur);

	}

	@Override
	public SynchroniseLotLIFinModele synchroniseLotLIFin(
			String applicationSource, String IP, String idLot,
			String idLotFils, String typeDocument,
			DocumentSynchro[] listeDocuments, Mode modeTest)
			throws RemoteException {

		ListeDocumentsHolder listeDocumentsManquants = new ListeDocumentsHolder();
		ListeDocumentsSynchroHolder listeDocumentsInconnus = new ListeDocumentsSynchroHolder();
		ErreurHolder erreur = new ErreurHolder();

		port.synchroniseLotLIFin(applicationSource, IP, idLot, idLotFils,
				typeDocument, listeDocuments, modeTest,
				listeDocumentsManquants, listeDocumentsInconnus, erreur);

		return new SynchroniseLotLIFinModele(listeDocumentsManquants,
				listeDocumentsInconnus, erreur);

	}

	@Override
	public SynchroniseLotLIIniModele synchroniseLotLIIni(
			String applicationSource, String IP, String idLot,
			String idLotFils, String typeDocument,
			DocumentSynchro[] listeDocuments, Mode modeTest)
			throws RemoteException {

		ListeDocumentsHolder listeDocumentsManquants = new ListeDocumentsHolder();
		ListeDocumentsSynchroHolder listeDocumentsInconnus = new ListeDocumentsSynchroHolder();
		ErreurHolder erreur = new ErreurHolder();

		port.synchroniseLotLIIni(applicationSource, IP, idLot, idLotFils,
				typeDocument, listeDocuments, modeTest,
				listeDocumentsManquants, listeDocumentsInconnus, erreur);

		return new SynchroniseLotLIIniModele(listeDocumentsManquants,
				listeDocumentsInconnus, erreur);

	}

}
