package fr.urssaf.image.sae.anais.portail.service;

import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCodesEnvironnement;
import fr.urssaf.image.sae.anais.framework.modele.SaeAnaisEnumCompteApplicatif;
import fr.urssaf.image.sae.anais.framework.service.SaeAnaisService;

/**
 * Classe de service pour la connexion à ANAIS<br>
 * L'implémentation s'appuie sur {@link SaeAnaisService}<br>
 * <br>
 * Ce service est accessible par 'Inversion of Control' (IOC)<br>
 * <br>
 * <code>@Autowired</code><br>
 * <code>private ConnectionService connectionService;</code><br>
 * 
 */
@Service
public class ConnectionService {

   private final SaeAnaisService service = new SaeAnaisService();

   /**
    * La connection appelle la méthode
    * {@link SaeAnaisService#authentifierPourSaeParLoginPassword}<br>
    * <br>
    * Les arguments sont:<br>
    * <ul>
    * <li>environnement : le code de l'environnement récupéré dans la
    * configuration du portail</li>
    * <li>serveur : vide</li>
    * <li>profilCompteApplicatif : le code du profil applicatif récupéré dans la
    * configuration du portail</li>
    * <li>compteApplicatif : vide</li>
    * <li>userLogin : l'identifiant renseigné dans le champ du formulaire «
    * identifiant »</li>
    * <li>userPassword : le mot de passe renseigné dans le champ du formulaire «
    * mot de passe »</li>
    * <li>codeInterRegion : vide</li>
    * <li>codeOrganisme : vide</li>
    * </ul>
    * <br>
    * La méthode renvoie le résultat de
    * {@link SaeAnaisService#authentifierPourSaeParLoginPassword}<br>
    * 
    * @param userLogin
    *           login de l'utilisateur
    * @param userPassword
    *           mot de passe de l'utilisateur
    * @return Vecteur d'identification au format XML
    * @throws fr.urssaf.image.sae.anais.framework.service.exception.SaeAnaisApiException
    */
   public final String connect(String userLogin, String userPassword) {

      String token = service.authentifierPourSaeParLoginPassword(
            SaeAnaisEnumCodesEnvironnement.Production, null,
            SaeAnaisEnumCompteApplicatif.Sae, null, userLogin, userPassword,
            null, null);

      return token;
   }
}
