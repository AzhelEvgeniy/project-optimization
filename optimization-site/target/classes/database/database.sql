-- Table: users
CREATE TABLE users (
  username VARCHAR(255) NOT NULL PRIMARY KEY,
  password VARCHAR(255) NOT NULL,
  enabled tinyint(4) NOT NULL DEFAULT '1'
)
  ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- Table: roles
CREATE TABLE authorities (
  id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL ,
  authority VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
)
  ENGINE = InnoDB;


-- Insert data

INSERT INTO users VALUES ('user', '$2a$10$oE.JYCEnrUVnrTaxskZt3uolOGlDg/kt5xw1fzjZCA4RDZHZSYrMS', 1); /*password: 123456*/

INSERT INTO authorities VALUES (1, 'user', 'ROLE_USER');