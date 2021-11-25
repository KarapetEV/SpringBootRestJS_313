USE myppdb;
CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  firstname varchar(150),
  lastname varchar(150),
  age INT,
  email varchar(255),
  password varchar(100),
  PRIMARY KEY (id)
) ;

CREATE TABLE roles (
  id INT NOT NULL AUTO_INCREMENT,
  role varchar(100) UNIQUE,
  PRIMARY KEY (id)
) ;

CREATE TABLE users_roles (
  user_id int(11) NOT NULL,
  role_id int(11) NOT NULL,
  KEY user_fk_idx (user_id),
  KEY role_fk_idx (role_id),
  CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);