CREATE TABLE `soiree` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `summary` mediumtext DEFAULT NULL,
  `ville` varchar(40) DEFAULT NULL,
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
   `soiree` INT NULL,
  PRIMARY KEY (`id`),
  INDEX fk_participant_soiree_idx (soiree ASC),
  CONSTRAINT fk_participant_soiree FOREIGN KEY (soiree) REFERENCES soiree(id))ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ville` (
  label VARCHAR(40) PRIMARY KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
