- installation du ActiveMQ (http://activemq.apache.org/)
	- download le binaire ActiveMQ 5.4.2 Release (http://activemq.apache.org/activemq-542-release.html)
	- lancer le serveur \bin\activemq.bat
	- lire la console d'admin : http://localhost:8161/admin/
	
- ouvrir un invite de commande à la racine du projet
- actions possibles
	- receive : 
		- ant receive -Dargs="<temps d'attente>" : en mode asynchrone le prg s'arrête au bout du temps d'attente indiqué 
		- ant subscribe -Dargs="<temps d'attente>" : le prg s'arrête au bout du temps d'attente indiqué
		- ant receiveS en mode synchrone : le prg s'arrête quand le message est consommé
	- send : ant send -Dargs="<prénom> <nom>"
	- publish : ant publish -Dargs="<prénom> <nom>"
	
- rappel pour l'installation de ant
    - téléchargement du binaire http://ant.apache.org/bindownload.cgi
    - ajouter le path du bin dans "path" des variables pour d'environnement
    - tester que la commande ant est bien comprise telle quelle dans un invite de commandes  