-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema contactbook
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema contactbook
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `contactbook` DEFAULT CHARACTER SET utf8 ;
USE `contactbook` ;

-- -----------------------------------------------------
-- Table `contactbook`.`nationality`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contactbook`.`nationality` (
  `id_nationality` INT NOT NULL AUTO_INCREMENT,
  `nationality_name` VARCHAR(40) NULL,
  PRIMARY KEY (`id_nationality`))
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


-- -----------------------------------------------------
-- Table `contactbook`.`marital_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contactbook`.`marital_status` (
  `id_marital_status` INT NOT NULL,
  `marital_status_name` VARCHAR(25) NULL,
  PRIMARY KEY (`id_marital_status`))
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


-- -----------------------------------------------------
-- Table `contactbook`.`countries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contactbook`.`countries` (
  `id_country` INT NOT NULL AUTO_INCREMENT,
  `country_name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_country`))
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


-- -----------------------------------------------------
-- Table `contactbook`.`contacts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contactbook`.`contacts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `patronymic` VARCHAR(20) NULL,
  `birth_date` DATE NULL,
  `sex` VARCHAR(1) NULL,
  `marital_status_id` INT NULL,
  `nationality_id` INT NULL,
  `website` VARCHAR(40) NULL,
  `email` VARCHAR(40) NULL,
  `photo_path` VARCHAR(200) NULL,
  `job` VARCHAR(30) NULL,
  `country_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `nationality_id_key_idx` (`nationality_id` ASC),
  INDEX `martial_status_id_key_idx` (`marital_status_id` ASC),
  INDEX `country_id_key_idx` (`country_id` ASC),
  CONSTRAINT `nationality_id_key`
    FOREIGN KEY (`nationality_id`)
    REFERENCES `contactbook`.`nationality` (`id_nationality`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `marital_status_id_key`
    FOREIGN KEY (`marital_status_id`)
    REFERENCES `contactbook`.`marital_status` (`id_marital_status`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `country_id_key`
    FOREIGN KEY (`country_id`)
    REFERENCES `contactbook`.`countries` (`id_country`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


-- -----------------------------------------------------
-- Table `contactbook`.`addresses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contactbook`.`addresses` (
  `contact_id` INT NOT NULL,
  `city` VARCHAR(40) NULL,
  `street` VARCHAR(40) NULL,
  `house_number` VARCHAR(10) NULL,
  `flat` VARCHAR(10) NULL,
  `postcode` VARCHAR(10) NULL,
  PRIMARY KEY (`contact_id`),
  CONSTRAINT `contact_id_addr_key`
    FOREIGN KEY (`contact_id`)
    REFERENCES `contactbook`.`contacts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


-- -----------------------------------------------------
-- Table `contactbook`.`attachments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contactbook`.`attachments` (
  `id_file` INT NOT NULL AUTO_INCREMENT,
  `contact_id` INT NOT NULL,
  `file_path` VARCHAR(200) NULL,
  `comment` VARCHAR(200) NULL,
  `upload_date` DATETIME NULL,
  PRIMARY KEY (`id_file`),
  INDEX `contact_id_file_key_idx` (`contact_id` ASC),
  CONSTRAINT `contact_id_file_key`
    FOREIGN KEY (`contact_id`)
    REFERENCES `contactbook`.`contacts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


-- -----------------------------------------------------
-- Table `contactbook`.`phones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contactbook`.`phones` (
  `phone_id` INT NOT NULL AUTO_INCREMENT,
  `contact_id` INT NOT NULL,
  `type` INT NULL,
  `country_code` VARCHAR(5) NULL,
  `operator_code` VARCHAR(5) NULL,
  `phone_number` VARCHAR(10) NULL,
  `comment` VARCHAR(200) NULL,
  PRIMARY KEY (`phone_id`),
  INDEX `contact_phone_id_key_idx` (`contact_id` ASC),
  CONSTRAINT `contact_phone_id_key`
    FOREIGN KEY (`contact_id`)
    REFERENCES `contactbook`.`contacts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO `contactbook`.`marital_status` (`id_marital_status`, `marital_status_name`) VALUES ('1', 'Холост/Не замужем');
INSERT INTO `contactbook`.`marital_status` (`id_marital_status`, `marital_status_name`) VALUES ('2', 'Женат/Замужем');
INSERT INTO `contactbook`.`marital_status` (`id_marital_status`, `marital_status_name`) VALUES ('3', 'Гражданский брак');

INSERT INTO `contactbook`.`nationality` (`id_nationality`, `nationality_name`) VALUES ('1', 'беларус');
INSERT INTO `contactbook`.`nationality` (`id_nationality`, `nationality_name`) VALUES ('2', 'русский');
INSERT INTO `contactbook`.`nationality` (`id_nationality`, `nationality_name`) VALUES ('3', 'украинец');
INSERT INTO `contactbook`.`nationality` (`id_nationality`, `nationality_name`) VALUES ('4', 'поляк');
INSERT INTO `contactbook`.`nationality` (`id_nationality`, `nationality_name`) VALUES ('5', 'чех');
INSERT INTO `contactbook`.`nationality` (`id_nationality`, `nationality_name`) VALUES ('6', 'немец');
INSERT INTO `contactbook`.`nationality` (`id_nationality`, `nationality_name`) VALUES ('7', 'француз');

INSERT INTO `contactbook`.`countries` (`id_country`, `country_name`) VALUES ('1', 'Беларусь');
INSERT INTO `contactbook`.`countries` (`id_country`, `country_name`) VALUES ('2', 'Россия');
INSERT INTO `contactbook`.`countries` (`id_country`, `country_name`) VALUES ('3', 'Украина');
INSERT INTO `contactbook`.`countries` (`id_country`, `country_name`) VALUES ('4', 'Польша');
INSERT INTO `contactbook`.`countries` (`id_country`, `country_name`) VALUES ('5', 'Чехия');
INSERT INTO `contactbook`.`countries` (`id_country`, `country_name`) VALUES ('6', 'Германия');
INSERT INTO `contactbook`.`countries` (`id_country`, `country_name`) VALUES ('7', 'Франция');
