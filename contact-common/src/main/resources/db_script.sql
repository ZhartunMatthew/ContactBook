-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema zhartun_matthew_contactbook
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema zhartun_matthew_contactbook
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `zhartun_matthew_contactbook`
  DEFAULT CHARACTER SET utf8;
GRANT ALL PRIVILEGES ON zhartun_matthew_contactbook.* TO 'zhartun_matthew'@'localhost'
IDENTIFIED BY '1234567';
USE `zhartun_matthew_contactbook`;
-- -----------------------------------------------------
-- Table `zhartun_matthew_contactbook`.`nationality`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zhartun_matthew_contactbook`.`nationality` (
  `id_nationality`   INT         NOT NULL AUTO_INCREMENT,
  `nationality_name` VARCHAR(40) NULL,
  PRIMARY KEY (`id_nationality`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8;

-- -----------------------------------------------------
-- Table `zhartun_matthew_contactbook`.`marital_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zhartun_matthew_contactbook`.`marital_status` (
  `id_marital_status`   INT         NOT NULL,
  `marital_status_name` VARCHAR(25) NULL,
  PRIMARY KEY (`id_marital_status`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8;

-- -----------------------------------------------------
-- Table `zhartun_matthew_contactbook`.`countries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zhartun_matthew_contactbook`.`countries` (
  `id_country`   INT         NOT NULL AUTO_INCREMENT,
  `country_name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_country`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8;

-- -----------------------------------------------------
-- Table `zhartun_matthew_contactbook`.`contacts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zhartun_matthew_contactbook`.`contacts` (
  `id`                INT         NOT NULL AUTO_INCREMENT,
  `first_name`        VARCHAR(40) NOT NULL,
  `last_name`         VARCHAR(40) NOT NULL,
  `patronymic`        VARCHAR(40) NULL,
  `birth_date`        DATE        NULL,
  `sex`               VARCHAR(1)  NULL,
  `marital_status_id` INT         NULL,
  `nationality_id`    INT         NULL,
  `website`           VARCHAR(40) NULL,
  `email`             VARCHAR(40) NULL,
  `photo_path`        VARCHAR(20) NULL,
  `job`               VARCHAR(50) NULL,
  `country_id`        INT         NULL,
  PRIMARY KEY (`id`),
  INDEX `nationality_id_key_idx` (`nationality_id` ASC),
  INDEX `martial_status_id_key_idx` (`marital_status_id` ASC),
  INDEX `country_id_key_idx` (`country_id` ASC),
  CONSTRAINT `nationality_id_key`
  FOREIGN KEY (`nationality_id`)
  REFERENCES `zhartun_matthew_contactbook`.`nationality` (`id_nationality`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `marital_status_id_key`
  FOREIGN KEY (`marital_status_id`)
  REFERENCES `zhartun_matthew_contactbook`.`marital_status` (`id_marital_status`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `country_id_key`
  FOREIGN KEY (`country_id`)
  REFERENCES `zhartun_matthew_contactbook`.`countries` (`id_country`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8;

-- -----------------------------------------------------
-- Table `zhartun_matthew_contactbook`.`addresses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zhartun_matthew_contactbook`.`addresses` (
  `contact_id`   INT         NOT NULL,
  `city`         VARCHAR(40) NULL,
  `street`       VARCHAR(40) NULL,
  `house_number` VARCHAR(10) NULL,
  `flat`         VARCHAR(10) NULL,
  `postcode`     VARCHAR(10) NULL,
  PRIMARY KEY (`contact_id`),
  CONSTRAINT `contact_id_addr_key`
  FOREIGN KEY (`contact_id`)
  REFERENCES `zhartun_matthew_contactbook`.`contacts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8;

-- -----------------------------------------------------
-- Table `zhartun_matthew_contactbook`.`attachments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zhartun_matthew_contactbook`.`attachments` (
  `id_file`     INT          NOT NULL AUTO_INCREMENT,
  `contact_id`  INT          NOT NULL,
  `file_name`   VARCHAR(100) NULL,
  `comment`     VARCHAR(200) NULL,
  `upload_date` DATETIME     NULL,
  PRIMARY KEY (`id_file`),
  INDEX `contact_id_file_key_idx` (`contact_id` ASC),
  CONSTRAINT `contact_id_file_key`
  FOREIGN KEY (`contact_id`)
  REFERENCES `zhartun_matthew_contactbook`.`contacts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8;

-- -----------------------------------------------------
-- Table `zhartun_matthew_contactbook`.`phones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zhartun_matthew_contactbook`.`phones` (
  `phone_id`      INT          NOT NULL AUTO_INCREMENT,
  `contact_id`    INT          NOT NULL,
  `type`          INT          NULL,
  `country_code`  VARCHAR(5)   NULL,
  `operator_code` VARCHAR(5)   NULL,
  `phone_number`  VARCHAR(10)  NULL,
  `comment`       VARCHAR(200) NULL,
  PRIMARY KEY (`phone_id`),
  INDEX `contact_phone_id_key_idx` (`contact_id` ASC),
  CONSTRAINT `contact_phone_id_key`
  FOREIGN KEY (`contact_id`)
  REFERENCES `zhartun_matthew_contactbook`.`contacts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;


INSERT INTO `zhartun_matthew_contactbook`.`marital_status` (`id_marital_status`, `marital_status_name`)
VALUES ('1', 'Холост/Не замужем');
INSERT INTO `zhartun_matthew_contactbook`.`marital_status` (`id_marital_status`, `marital_status_name`)
VALUES ('2', 'Женат/Замужем');
INSERT INTO `zhartun_matthew_contactbook`.`marital_status` (`id_marital_status`, `marital_status_name`)
VALUES ('3', 'Гражданский брак');

INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('1', 'австралиец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('2', 'австриец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('3', 'азербайджанец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('4', 'албанец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('5', 'алжирец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('6', 'американец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('7', 'анголец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('8', 'аргентинец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('9', 'армянин');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('10', 'багамец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('11', 'белорус');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('12', 'бельгиец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('13', 'бенинец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('14', 'болгарин');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('15', 'боливиец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('16', 'бразилец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('17', 'македонец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('18', 'британец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('19', 'венгр');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('20', 'венесуэлец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('21', 'вьетнамец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('22', 'гаитянин');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('23', 'гватемалец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('24', 'немец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('25', 'китаец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('26', 'грек');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('27', 'грузин');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('28', 'датчанин');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('29', 'доминиканец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('30', 'замбиец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('31', 'индиец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('32', 'индонезиец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('33', 'иракец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('34', 'иранец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('35', 'ирландец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('36', 'исландец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('37', 'испанец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('38', 'итальянец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('39', 'казах');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('40', 'камбоджиец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('41', 'канадец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('42', 'кениец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('43', 'киргиз');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('44', 'китаец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('45', 'кубинец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('46', 'латыш');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('47', 'литовец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('48', 'люксембуржец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('49', 'малаец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('50', 'марокканец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('51', 'молдаванин');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('52', 'монгол');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('53', 'непалец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('54', 'нигериец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('55', 'голландец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('56', 'никарагуанец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('57', 'норвежец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('58', 'эмиратец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('59', 'пакистанец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('60', 'парагваец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('61', 'перуанец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('62', 'поляк');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('63', 'португалец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('64', 'пуэрториканец ');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('65', 'русский');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('66', 'румын');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('67', 'сальвадорец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('68', 'саудовец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('69', 'кореец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('70', 'сейшелец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('71', 'француз');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('72', 'сенегалец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('73', 'сингапурец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('74', 'сириец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('75', 'словак');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('76', 'словенец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('77', 'сомалиец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('78', 'суданец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('79', 'таец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('80', 'танзаниец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('81', 'туркмен');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('82', 'турок');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('83', 'узбек');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('84', 'украинец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('85', 'уругваец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('86', 'филиппинец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('87', 'финн');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('88', 'француз');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('89', 'хорват');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('90', 'чех');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('91', 'чилиец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('92', 'швейцарец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('93', 'швед');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('94', 'эстонец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('95', 'серб');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('96', 'кореец');
INSERT INTO `zhartun_matthew_contactbook`.`nationality` (`id_nationality`, `nationality_name`)
VALUES ('97', 'японец');

INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('1', 'Австралия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('2', 'Австрия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('3', 'Азербайджан');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('4', 'Албания');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('5', 'Алжир');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('6', 'Ангола');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('7', 'Андорра');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('8', 'Антарктида');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('9', 'Аргентина');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('10', 'Армения');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('11', 'Афганистан');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('12', 'Багамские о-ва');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('13', 'Бангладеш');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('14', 'Барбадос');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('15', 'Беларусь');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('16', 'Бельгия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('17', 'Бермудские о-ва');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('18', 'Болгария');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('19', 'Боливия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('20', 'Босния и Герцеговина');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('21', 'Бразилия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('22', 'Бруней-Даруссалам');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('23', 'Ватикан');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('24', 'Великобритания');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('25', 'Венгрия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('26', 'Венесуэла');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('27', 'Вьетнам');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('28', 'Гаити');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('29', 'Гватемала');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('30', 'Гвинея');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('31', 'Германия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('32', 'Гибралтар');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('33', 'Гондурас');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('34', 'Гренландия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('35', 'Греция');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('36', 'Грузия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('37', 'Дания');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('38', 'Доминика');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('39', 'Египет');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('40', 'Замбия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('41', 'Зимбабве');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('42', 'Израиль');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('43', 'Индия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('44', 'Индонезия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('45', 'Иордания');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('46', 'Ирак');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('47', 'Иран');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('48', 'Ирландия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('49', 'Исландия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('50', 'Испания');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('51', 'Италия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('52', 'Йемен');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('53', 'Казахстан');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('54', 'Камбоджа');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('55', 'Камерун');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('56', 'Канада');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('57', 'Канарские о-ва');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('58', 'Катар');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('59', 'Кения');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('60', 'Кипр');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('61', 'Киргизия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('62', 'Китай');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('63', 'КНДР');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('64', 'Колумбия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('65', 'Коморские о-ва');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('66', 'Конго');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('67', 'Коста-Рика');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('68', 'Кот-д’Ивуар');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('69', 'Куба');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('70', 'Кувейт');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('71', 'Лаос');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('72', 'Латвия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('73', 'Ливан');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('74', 'Ливия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('75', 'Литва');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('76', 'Лихтенштейн');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('77', 'Люксембург');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('78', 'Мадагаскар');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('79', 'Майотта');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('80', 'Македония');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('81', 'Малайзия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('82', 'Мали');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('83', 'Мальдивские о-ва');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('84', 'Мальта');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('85', 'Марокко');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('86', 'Мексика');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('87', 'Мозамбик');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('88', 'Молдова');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('89', 'Монако');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('90', 'Монголия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('91', 'Мьянма');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('92', 'Намибия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('93', 'Непал');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('94', 'Нигер');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('95', 'Нидерланды');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('96', 'Никарагуа');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('97', 'Новая Зеландия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('98', 'Норвегия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('99', 'ОАЭ');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('100', 'Пакистан');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('101', 'Панама');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('102', 'Папуа – Новая Гвинея');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('103', 'Парагвай');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('104', 'Перу');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('105', 'Польша');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('106', 'Португалия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('107', 'Пуэрто-Рико');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('108', 'Республика Корея');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('109', 'Россия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('110', 'Руанда');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('111', 'Румыния');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('112', 'Сальвадор');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('113', 'Самоа');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('114', 'Саудовская Аравия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('115', 'Сейшельские о-ва');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('116', 'Сербия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('117', 'Сингапур');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('118', 'Сирия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('119', 'Словакия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('120', 'Словения');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('121', 'Соединенные Штаты');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('122', 'Сомали');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('123', 'Судан');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('124', 'Таджикистан');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('125', 'Таиланд');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('126', 'Тайвань');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('127', 'Тунис');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('128', 'Туркменистан');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('129', 'Турция');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('130', 'Уганда');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('131', 'Узбекистан');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('132', 'Украина');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('133', 'Уругвай');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('134', 'Фиджи');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('135', 'Филиппины');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('136', 'Финляндия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('137', 'Франция');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('138', 'Хорватия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('139', 'Черногория');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('140', 'Чехия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('141', 'Чили');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('142', 'Швейцария');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('143', 'Швеция');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('144', 'Шри-Ланка');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('145', 'Эквадор');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('146', 'Эстония');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('147', 'Эфиопия');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('148', 'ЮАР');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('149', 'Ямайка');
INSERT INTO `zhartun_matthew_contactbook`.`countries` (`id_country`, `country_name`)
VALUES ('150', 'Япония');
