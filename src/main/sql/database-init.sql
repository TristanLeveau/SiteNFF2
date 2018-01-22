CREATE TABLE `livraison` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `summary` mediumtext DEFAULT NULL,
  `semestre` varchar(40) DEFAULT NULL,
  `dateSoiree` char(10) DEFAULT NULL,
  `image` varchar(150) DEFAULT NULL,
    `supprime` bit(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `participant` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  `prenom` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `motDePasse` varchar(45) DEFAULT NULL,
   `livraison` INT NULL,
  PRIMARY KEY (`id`),
  INDEX fk_participant_soiree_idx (livraison ASC),
  CONSTRAINT fk_participant_soiree FOREIGN KEY (livraison) REFERENCES livraison(id))ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `semestre` (
  label VARCHAR(40) PRIMARY KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
