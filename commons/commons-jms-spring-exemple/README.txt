- installation du ActiveMQ (http://activemq.apache.org/)
	- download le binaire ActiveMQ 5.4.2 Release (http://activemq.apache.org/activemq-542-release.html)
	- lancer le serveur \bin\activemq.bat
	- lire la console d'admin : http://localhost:8161/admin/
	
- ouvrir un invite de commande à la racine du projet
- actions possibles
	- receive : ant receive
	- send : ant send -Dargs="<prénom> <nom>"