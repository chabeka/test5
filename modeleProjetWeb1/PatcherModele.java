import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

/**
 * 
 * Une classe permettant de patcher des sources copiées à partir du projet modèle modeleProjetWeb1
 * Du quick & dirty (d'où la désactivation de PMD)
 * 
 * @author CER6990173
 * 
 */
@SuppressWarnings("PMD")
public class PatcherModele
{

	static class Parametres
	{
		String repertoireSources;
		String packageBase;
		String eclipseNomProjet;
		String mavenGroupId;
		String mavenArtefactId;
		String mavenVersion;
		String mavenProjectName;
		String jdbcLogin;
		String jdbcPassword;
		String jdbcUrlMain;
		String jdbcUrlTu;
		String jdbcDriver;
		String hibernateDialect;
		String hibernateDefaultCatalog;
		String maquetteAppTitle;
		String maquetteAppCopyright;
		String maquetteTheme;
	}
	
	static Parametres lesParametres = new Parametres();
	
	static final String CRLF = System.getProperty("line.separator");
	
	// Les valeurs à remplacer
	static String findEclipseNomProjet = "modeleProjetWeb1";
	static String findPomXmlGroupId = "<groupId>fr.urssaf.image</groupId>";
	static String findPomXmlArtifactId = "<artifactId>modeleProjetWeb1</artifactId>";
	static String findPomXmlVersion = "<version>0.0.1-SNAPSHOT</version>";
	static String findPomXmlName = "<name>Projet modele pour une application web</name>";
	static String findApplicationContextXmlBasepackage = "<context:component-scan base-package=\"fr.urssaf.image.modeleProjetWeb1\" />";
	static String findApplicationContextDatasourcesJdbcDriver = "<value>org.postgresql.Driver</value>";
	static String findHibernateCfgXmlDialect = "<property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQLDialect</property>";
	static String findWebXmlMaquetteAppTitle = "Maquette : Titre de l'application";
	static String findWebXmlMaquetteCopyright = "Maquette : Copyright";
	static String findWebXmlMaquetteTheme = "aed";
		
	
	/**
	 * Méthode principale
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		
		// Récupération des paramètres
		if (!getParametres())
		{
			return;
		}
		if (!verifParametres())
		{
			return;
		}
		
		// Eclipse - Nom du projet
		eclipseProjet();
		
		// pom.xml
		pomXml();
		
		// Package de base
		basePackage();
		
		// hibernate.properties
		hibernateProperties();
		
		// applicationContext-dataSources.xml
		applicationContextDataSourcesXml();
		
		// hibernate.cfg.xml
		hibernateCfgXml();
		
		// main jdbc.properties
		jdbcPropertiesMain();
		
		// test jdbc.properties
		jdbcPropertiesTest();
		
		// web.xml
		webXml();
		
		// Fin
		outln("Traitement OK");
		
	}
	
	private static void outln(final String chaine)
	{
		System.out.println(chaine); 
	}
	
	
	private static void outln()
	{
		outln("");
	}
	
	private static void out(final String chaine)
	{
		System.out.print(chaine); 
	}
	
	
	private static Boolean getParametres()
	{
		
		/*
		// Pour les tests
		lesParametres.RepertoireSources = "G:\\pmareche\\eclipse_workspace\\testModele";
		lesParametres.PackageBase = "fr.urssaf.image.testModele";
		lesParametres.EclipseNomProjet = "testModele";
		lesParametres.MavenProjectName = "Test du modèle d'application web";
		lesParametres.MavenGroupId = "fr.urssaf.image";
		lesParametres.MavenArtefactId = "testModele";
		lesParametres.MavenVersion = "0.0.1-SNAPSHOT";
		lesParametres.JdbcLogin = "[JDBC - login]";
		lesParametres.JdbcPassword = "[JDBC - password]";
		lesParametres.JdbcUrlMain = "[JDBC - Main URL]";
		lesParametres.JdbcUrlTu = "[JDBC - Tests unitaires URL]";
		lesParametres.JdbcDriver = "[JDBC - Driver]";
		lesParametres.HibernateDialect = "[Hibernate - Dialect]";
		lesParametres.HibernateDefaultCatalog = "[Hibernate - Default Catalog]";
		lesParametres.MaquetteAppTitle = "[Maquette - AppTitle]";
		lesParametres.MaquetteAppCopyright = "[Maquette - AppCopyright]";
		lesParametres.MaquetteTheme = "[Maquette - Theme]";
		/* */
		
		Scanner in = new Scanner(System.in);
		outln("Saisir les valeurs suivantes :");
		
		out("Repertoire des sources a patcher (ex: G:\\repPerso\\eclipse_workspace\\monBeauProjet) > ");
		lesParametres.repertoireSources = in.nextLine().trim();
		
		out("Package de base (ex: fr.urssaf.image.monBeauProjet) > ");
		lesParametres.packageBase = in.nextLine().trim();
		
		out("Nom du projet pour Eclipse (ex: monBeauProjet) > ");
		lesParametres.eclipseNomProjet = in.nextLine().trim();
		
		out("Maven - Nom du projet (ex: Ceci est un beau projet) > ");
		lesParametres.mavenProjectName = in.nextLine().trim();
		
		out("Maven - GroupId (ex: fr.urssaf.image) > ");
		lesParametres.mavenGroupId = in.nextLine().trim();
		
		out("Maven - ArtefactId (ex: monBeauProjet) > ");
		lesParametres.mavenArtefactId = in.nextLine().trim();
		
		out("Maven - Version (ex: 0.0.1-SNAPSHOT) > ");
		lesParametres.mavenVersion = in.nextLine().trim();
		
		out("JDBC - Login > ");
		lesParametres.jdbcLogin = in.nextLine().trim();
		
		out("JDBC - Password > ");
		lesParametres.jdbcPassword = in.nextLine().trim();
		
		out("JDBC - URL (ex: jdbc:postgresql://localhost:5432/arsabox) > ");
		lesParametres.jdbcUrlMain = in.nextLine().trim();
		
		out("JDBC - URL pour les tests unitaires (ex: jdbc:postgresql://localhost:5432/arsabox) > ");
		lesParametres.jdbcUrlTu = in.nextLine().trim();
		
		out("JDBC - Driver (ex: org.postgresql.Driver) > ");
		lesParametres.jdbcDriver = in.nextLine().trim();
		
		out("Hibernate - Dialect (ex: org.hibernate.dialect.PostgreSQLDialect) > ");
		lesParametres.hibernateDialect = in.nextLine().trim();
		
		out("Hibernate - Default Catalog (ex: arsabox) > ");
		lesParametres.hibernateDefaultCatalog = in.nextLine().trim();
		
		out("Maquette IHM - Titre de l'application > ");
		lesParametres.maquetteAppTitle = in.nextLine().trim();
		
		out("Maquette IHM - Copyright (ex: Copyright CIRTIL 2010) > ");
		lesParametres.maquetteAppCopyright = in.nextLine().trim().trim();
		
		out("Maquette IHM - Theme (ex: aed) > ");
		lesParametres.maquetteTheme = in.nextLine().trim();
		
		outln();
		outln();
		outln("Recapitulatif : ");
		outln();
		outln();
		outln("Repertoire des sources a patcher : " + lesParametres.repertoireSources);
		outln("Package de base : " + lesParametres.packageBase);
		outln("Nom du projet pour Eclipse : " + lesParametres.eclipseNomProjet);
		outln("Maven - Nom du projet : " + lesParametres.mavenProjectName);
		outln("Maven - GroupId : " + lesParametres.mavenGroupId);
		outln("Maven - ArtefactId : " + lesParametres.mavenArtefactId);
		outln("Maven - Version : " + lesParametres.mavenVersion);
		outln("JDBC - Login : " + lesParametres.jdbcLogin);
		outln("JDBC - Password : " + lesParametres.jdbcPassword);
		outln("JDBC - URL : " + lesParametres.jdbcUrlMain);
		outln("JDBC - URL : " + lesParametres.jdbcUrlTu);
		outln("JDBC - Driver : " + lesParametres.jdbcDriver);
		outln("Hibernate - Dialect : " + lesParametres.hibernateDialect);
		outln("Hibernate - Default Catalog : " + lesParametres.hibernateDefaultCatalog);
		outln("Maquette IHM - Titre de l'application : " + lesParametres.maquetteAppTitle);
		outln("Maquette IHM - Copyright : " + lesParametres.maquetteAppCopyright);
		outln("Maquette IHM - Theme : " + lesParametres.maquetteTheme);
		
		outln();
		outln();
		out("Voulez-vous continuer ? (Y/N)");
		String ok = in.nextLine();
		if (!ok.toUpperCase().equals("Y"))
		{
			outln("Traitement annule a la demande de l'utilisateur");
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	
	private static Boolean verifParametres()
	{
		
		// Vérifications sommaires
		if (lesParametres.repertoireSources.length()==0)
		{
			outln("Erreur : Le repertoire des sources a patcher doit etre saisi !");
			return false;
		}
		
		// TODO : vérifications complètes
		
		// Renvoie true si on arrive jusque là
		return true;
	}
	
	
	private static void eclipseProjet() throws IOException
	{
		
		// Variables
		String cheminFichier;
		String find;
		String replace;
		
		// .project
		cheminFichier = lesParametres.repertoireSources + "\\.project";
		find = findEclipseNomProjet;
		replace = lesParametres.eclipseNomProjet;
		findReplaceInFile(cheminFichier,find,replace);
		
		// .settings\org.eclipse.wst.common.component
		cheminFichier = lesParametres.repertoireSources + "\\.settings\\org.eclipse.wst.common.component";
		find = findEclipseNomProjet;
		replace = lesParametres.eclipseNomProjet;
		findReplaceInFile(cheminFichier,find,replace);
		
	}
	
	
	private static void pomXml() throws IOException
	{
		
		String[] findArray = new String[4];
		String[] replaceArray = new String[4];
		
		findArray[0] = findPomXmlGroupId; 
		replaceArray[0] = "<groupId>" + lesParametres.mavenGroupId + "</groupId>";
		findArray[1] = findPomXmlArtifactId;
		replaceArray[1] = "<artifactId>" + lesParametres.mavenArtefactId + "</artifactId>";
		findArray[2] = findPomXmlVersion;
		replaceArray[2] = "<version>" + lesParametres.mavenVersion + "</version>";
		findArray[3] = findPomXmlName; 
		replaceArray[3] = "<name>" + lesParametres.mavenProjectName + "</name>";
		
		String cheminFichier = lesParametres.repertoireSources + "\\pom.xml";
		findReplaceInFile(cheminFichier,findArray,replaceArray);
		
	}
	
	
	private static void basePackage() throws IOException
	{
		
		// Modification du fichier src\main\resources\applicationContext.xml 
		String find = findApplicationContextXmlBasepackage;
		String replace = "<context:component-scan base-package=\"" + lesParametres.packageBase + "\" />";
		String cheminFichier = lesParametres.repertoireSources + "\\src\\main\\resources\\applicationContext.xml";
		findReplaceInFile(cheminFichier,find,replace);
		
		// Déplacement des répertoires
		// src\main\java\fr\\urssaf\image\modeleProjetWeb1
		// =>
		// src\main\java\fr\\urssaf\image\NomDuProjet
		// Répertoire source
		String repertoireSource = lesParametres.repertoireSources + "\\src\\main\\java\\fr\\urssaf\\image\\modeleProjetWeb1";
		// Répertoire de destination
		String[] repertoiresPackageBase = lesParametres.packageBase.split("\\.");
		String repertoireDest = lesParametres.repertoireSources + "\\src\\main\\java";
		for (String repertoire: repertoiresPackageBase)
		{
			repertoireDest += "\\" + repertoire;
		}
		// Déplacement
		File from = new File(repertoireSource);
		File to = new File(repertoireDest);
		from.renameTo(to);
		
		// TODO : changer le nom des packages dans les .java
		
		
	}
	
	
	private static void hibernateProperties() throws IOException
	{
		
		// src\main\config\hibernate.properties
		
		String[] properties = new String[6];
		String[] newValues = new String[6];
		
		properties[0] = "hibernate.connection.driver_class";
		newValues[0] = lesParametres.jdbcDriver;
		
		properties[1] = "hibernate.connection.username";
		newValues[1] = lesParametres.jdbcLogin;
		
		properties[2] = "hibernate.connection.password";
		newValues[2] = lesParametres.jdbcPassword;
		
		properties[3] = "hibernate.connection.url";
		newValues[3] = lesParametres.jdbcUrlMain;
		
		properties[4] = "hibernate.dialect";
		newValues[4] = lesParametres.hibernateDialect;
		
		properties[5] = "hibernate.default_catalog";
		newValues[5] = lesParametres.hibernateDefaultCatalog;
		
		String cheminFichier = lesParametres.repertoireSources + "\\src\\main\\config\\hibernate.properties";
		findReplaceInPropertiesFile(cheminFichier,properties,newValues);
		
	}
	
	
	private static void applicationContextDataSourcesXml() throws IOException
	{
		// src\main\resources\applicationContext-dataSources.xml 
		String find = findApplicationContextDatasourcesJdbcDriver;
		String replace = "<value>" + lesParametres.jdbcDriver + "</value>";
		String cheminFichier = lesParametres.repertoireSources + "\\src\\main\\resources\\applicationContext-dataSources.xml";
		findReplaceInFile(cheminFichier,find,replace);
	}
	
	private static void hibernateCfgXml() throws IOException
	{
		// src\main\resources\hibernate.cfg.xml
		String find = findHibernateCfgXmlDialect;
		String replace = "<property name=\"hibernate.dialect\">" + lesParametres.hibernateDialect +  "</property>" ;
		String cheminFichier = lesParametres.repertoireSources + "\\src\\main\\resources\\hibernate.cfg.xml";
		findReplaceInFile(cheminFichier,find,replace);
	}
	
	
	private static void jdbcPropertiesMain() throws IOException
	{
	
		// src\main\resources\jdbc.properties
		
		String[] properties = new String[3];
		String[] newValues = new String[3];
		
		properties[0] = "jdbc.url";
		newValues[0] = lesParametres.jdbcUrlMain ;
		
		properties[1] = "jdbc.username";
		newValues[1] = lesParametres.jdbcLogin;
		
		properties[2] = "jdbc.password";
		newValues[2] = lesParametres.jdbcPassword;
		
		String cheminFichier = lesParametres.repertoireSources + "\\src\\main\\resources\\jdbc.properties";
		findReplaceInPropertiesFile(cheminFichier,properties,newValues);
		
	}
	
	
	private static void jdbcPropertiesTest() throws IOException
	{
	
		// src\test\resources\jdbc.properties
		
		String[] properties = new String[3];
		String[] newValues = new String[3];
		
		properties[0] = "jdbc.url";
		newValues[0] = lesParametres.jdbcUrlTu ;
		
		properties[1] = "jdbc.username";
		newValues[1] = lesParametres.jdbcLogin;
		
		properties[2] = "jdbc.password";
		newValues[2] = lesParametres.jdbcPassword;
		
		String cheminFichier = lesParametres.repertoireSources + "\\src\\test\\resources\\jdbc.properties";
		findReplaceInPropertiesFile(cheminFichier,properties,newValues);
		
	}
	
	
	private static void webXml() throws IOException
	{
		
		// src\main\webapp\WEB-INF\web.xml
		
		String find[] = new String[3];
		String replace[] = new String[3];
		
		find[0] = webXmlBuildChaine(findWebXmlMaquetteAppTitle);
		replace[0] = webXmlBuildChaine(lesParametres.maquetteAppTitle) ;
		
		find[1] = webXmlBuildChaine(findWebXmlMaquetteCopyright);
		replace[1] = webXmlBuildChaine(lesParametres.maquetteAppCopyright) ;
		
		find[2] = webXmlBuildChaine(findWebXmlMaquetteTheme);
		replace[2] = webXmlBuildChaine(lesParametres.maquetteTheme) ;
		
		String cheminFichier = lesParametres.repertoireSources + "\\src\\main\\webapp\\WEB-INF\\web.xml";
		findReplaceInFile(cheminFichier,find,replace);
		
	}
	
	
	private static String webXmlBuildChaine(String chaine)
	{
		return "<param-value>" + chaine + "</param-value>";
	}
	
		
	private static void findReplaceInFile(
			String cheminFichier,
			String[] find,
			String[] replace) throws IOException
	{
		String contenuFichier = getFileContent(cheminFichier);
		for (int i=0;i<find.length;i++)
		{
			contenuFichier = contenuFichier.replaceAll(find[i], replace[i]);
		}
		writeFile(cheminFichier,contenuFichier);
	}
	
	
	private static void findReplaceInFile(
			String cheminFichier,
			String find,
			String replace) throws IOException
	{
		String[] findArray = new String[1] ;
		findArray[0] = find;
		String[] replaceArray = new String[1];
		replaceArray[0] = replace;
		findReplaceInFile(cheminFichier,findArray,replaceArray);
	}

	
	private static String getFileContent(String cheminFichier) throws IOException
	{
		try
	    {
	    	StringBuffer buffer = new StringBuffer();
	    	FileInputStream fis = new FileInputStream(cheminFichier);
	    	InputStreamReader isr = new InputStreamReader(fis,"UTF8");
	    	Reader in = new BufferedReader(isr);
	    	int ch;
	    	while ((ch = in.read()) > -1)
	    	{
	    		buffer.append((char)ch);
	    	}
	    	in.close();
	    	return buffer.toString();
	    }
	    catch (IOException e) 
	    {
	    	throw e;
		}
		
	}
	
	private static void writeFile(
			String cheminFichier,
			String contenu) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(cheminFichier);
		Writer out = new OutputStreamWriter(fos, "UTF8");
		out.write(contenu);
		out.close();
	}
	
	
	private static void findReplaceInPropertiesFile(
			String cheminFichier,
			String[] properties,
			String[] newValues) throws IOException
	{
		String contenuFichier = getFileContent(cheminFichier);
		int position ;
		int position2 ;
		String property;
		for (int i=0;i<properties.length;i++)
		{
			property = properties[i];
			position = contenuFichier.indexOf(property);
			if (position!=-1)
			{
				position = contenuFichier.indexOf("=",position) + 1;
				position2 = contenuFichier.indexOf(CRLF,position);
				if (position2==-1)
				{
					position2 = contenuFichier.length();
				}
				contenuFichier = contenuFichier.substring(0,position) 
					+ newValues[i]
					+ contenuFichier.substring(position2) ; 
			}
		}
		writeFile(cheminFichier,contenuFichier);
	}
	
	
	
	
	
}
