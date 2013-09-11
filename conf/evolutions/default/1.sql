# --- !Ups

CREATE  TABLE users (
  id INT NOT NULL AUTO_INCREMENT ,
  first_name VARCHAR(255) NOT NULL ,
  last_name VARCHAR(255) NOT NULL ,
  email VARCHAR(255) NOT NULL ,
  password VARCHAR(60) NOT NULL ,
  PRIMARY KEY (id) ,
  UNIQUE INDEX id_UNIQUE (id ASC)
);

# --- !Downs

DROP TABLE IF EXISTS users;