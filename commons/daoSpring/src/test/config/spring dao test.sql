-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.35-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema document_test
--

CREATE DATABASE IF NOT EXISTS document_test;
USE document_test;

--
-- Definition of table `auteur`
--

DROP TABLE IF EXISTS `auteur`;
CREATE TABLE `auteur` (
  `id` int(10) unsigned NOT NULL,
  `nom` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auteur`
--

/*!40000 ALTER TABLE `auteur` DISABLE KEYS */;
INSERT INTO `auteur` (`id`,`nom`) VALUES 
 (0,'auteur 0'),
 (1,'auteur 1'),
 (2,'auteur 2');
/*!40000 ALTER TABLE `auteur` ENABLE KEYS */;


--
-- Definition of table `document`
--

DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `titre` varchar(45) NOT NULL,
  `date` datetime NOT NULL,
  `id_auteur` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_document_auteur` (`id_auteur`),
  CONSTRAINT `FK_document_auteur` FOREIGN KEY (`id_auteur`) REFERENCES `auteur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `document`
--

/*!40000 ALTER TABLE `document` DISABLE KEYS */;
INSERT INTO `document` (`id`,`titre`,`date`,`id_auteur`) VALUES 
 (1,'titre 1','2002-04-08 00:00:00',NULL),
 (2,'titre 2','1999-07-03 00:00:00',NULL),
 (3,'titre 3','2023-08-26 00:00:00',1),
 (4,'titre 4','2025-03-30 00:00:00',2),
 (5,'titre 5','2024-02-18 00:00:00',0);
/*!40000 ALTER TABLE `document` ENABLE KEYS */;


--
-- Definition of table `etat`
--

DROP TABLE IF EXISTS `etat`;
CREATE TABLE `etat` (
  `id` int(10) unsigned NOT NULL,
  `id_document` int(10) unsigned NOT NULL,
  `etat` varchar(45) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_etat_document` (`id_document`),
  CONSTRAINT `FK_etat_document` FOREIGN KEY (`id_document`) REFERENCES `document` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `etat`
--

/*!40000 ALTER TABLE `etat` DISABLE KEYS */;
INSERT INTO `etat` (`id`,`id_document`,`etat`,`date`) VALUES 
 (0,2,'init','1999-12-31 23:59:59'),
 (1,2,'open','2000-12-31 23:59:59'),
 (2,2,'close','2001-12-31 23:59:59'),
 (3,3,'init','1999-12-31 23:59:59'),
 (4,3,'open','2001-12-31 23:59:59'),
 (5,1,'open','1998-12-31 23:59:59');
/*!40000 ALTER TABLE `etat` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
