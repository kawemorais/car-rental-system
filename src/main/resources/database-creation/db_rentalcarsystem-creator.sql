CREATE DATABASE IF NOT EXISTS db_rentalcarsystem;

USE db_rentalcarsystem;

CREATE TABLE IF NOT EXISTS tb_users
(
    id       BIGINT              NOT NULL AUTO_INCREMENT,
    name     VARCHAR(100)        NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(200)        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_address
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    street      VARCHAR(100) NOT NULL,
    houseNumber VARCHAR(7)   NOT NULL,
    district    VARCHAR(70)  NOT NULL,
    city        VARCHAR(70)  NOT NULL,
    uf          VARCHAR(2)   NOT NULL,
    complement  VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_clients
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(100) NOT NULL,
    age       TINYINT      NOT NULL,
    sex       VARCHAR(2),
    cpf       VARCHAR(11)  NOT NULL UNIQUE,
    cnhNumber VARCHAR(11)  NOT NULL UNIQUE,
    fkAddress BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (fkAddress) REFERENCES tb_address (id)
);

CREATE TABLE IF NOT EXISTS tb_cars
(
    id              BIGINT      NOT NULL AUTO_INCREMENT,
    carModel        VARCHAR(50) NOT NULL,
    carBrand        VARCHAR(50) NOT NULL,
    fabricationYear TINYINT     NOT NULL,
    plateNumber     VARCHAR(7)  NOT NULL UNIQUE,
    rentPrice       DECIMAL     NOT NULL,
    rentStatus      VARCHAR(10) NOT NULL,
    photoUrl        TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_rental
(
    id                 BIGINT   NOT NULL AUTO_INCREMENT,
    rentStartDateTime  DATETIME NOT NULL,
    rentFinishDateTime DATETIME NOT NULL,
    rentTotalPrice     DECIMAL  NOT NULL,
    fkUser             BIGINT   NOT NULL,
    fKClient           BIGINT   NOT NULL,
    fkCar              BIGINT   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (fkUser) REFERENCES tb_users (id),
    FOREIGN KEY (fkClient) REFERENCES tb_clients (id),
    FOREIGN KEY (fkCar) REFERENCES tb_cars (id)
);
