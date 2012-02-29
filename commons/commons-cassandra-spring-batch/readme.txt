Ce projet fournit une implémentation des classes DAO de spring-batch qui utiliser cassandra et zookeeper.

Pour faire fonctionner spring-batch avec ces classes, il faut au préalable avoir créé
les familles de colonnes nécessaires dans cassandra. Pour ça, se reporter au fichier
src/config/modele_schema_cassandra.txt


Pour instancier les différentes DAO avec Spring, nous vous conseillons d'importer le fichier
src/main/resources/applicationContext-commons-cassandra-spring-batch.xml

Les place-holders utilisés sont les suivants :
cassandra.hosts						: listes des serveurs cassandra, avec le port(9160), séparés par des virgules
cassandra.username					: username pour la connexion à cassandra 
cassandra.password					: mot de passe pour la connexion à cassandra
cassandra.keyspace					: nom du keyspace cassandra à utiliser (normalement "SAE", sauf si vous voulez vous créer un keyspace personnel)
zookeeper.hosts						: nom des serveurs zookper, séparés par des virgules
zookeeper.namespace					: espace de nom utilisé par zookeeper (normalement : "SAE", sauf si vous voulez vous isoler)

Exemple :

cassandra.hosts=cer69imageint9.cer69.recouv:9160
cassandra.username=root
cassandra.password=regina4932
cassandra.keyspace=SAE
zookeeper.hosts=cer69-ds4int.cer69.recouv
zookeeper.namespace=SAE
