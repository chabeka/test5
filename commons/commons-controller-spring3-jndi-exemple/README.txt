BASE DE DONNEES

- fichier de configuration de la base de données pour MySQL
src>main>config>document_jndi.sql


JNDI DANS TOMCAT

- ouvrir le fichier context.xml dans tomcat 
ex pour WTP tomcat v6.0: Servers>Tomcat v6.0 Server at localhost-config>context.xml
- ajouter une ressource dans <Context> pour le dataSource 
ex:
<Resource driverClassName="com.mysql.jdbc.Driver" name="jdbc/mysql"
      password="admin" username="root" type="javax.sql.DataSource"
      url="jdbc:mysql://localhost:3306/document_jndi" />
      
ATTENTION: N'oubliez pas d'ajouter le driver dans le repertoire lib de tomcat

- ajouter une variable d'environnement 'Title'

<Environment name="title/default" value="Spring Web Exemple with JNDI"
   type="java.lang.String" />