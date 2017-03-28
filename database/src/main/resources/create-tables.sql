DROP TABLE IF EXISTS producer;
CREATE TABLE producer (
  producer_id    INT          NOT NULL AUTO_INCREMENT,
  producer_name  VARCHAR(255) NOT NULL UNIQUE,
  country        VARCHAR(255) NULL,
  PRIMARY KEY (producer_id)
);

DROP TABLE IF EXISTS car;
CREATE TABLE car (
  car_id        INT          NOT NULL AUTO_INCREMENT,
  model         VARCHAR(255) NOT NULL,
  release_date  DATE         NOT NULL,
  amount        INT          NOT NULL,
  producer_id   INT          NOT NULL,
  PRIMARY KEY (car_id),
  FOREIGN KEY (producer_id) REFERENCES producer(producer_id) ON DELETE CASCADE
);