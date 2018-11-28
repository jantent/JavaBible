CREATE TABLE user (
  id       INTEGER AUTO_INCREMENT NOT NULL,
  username VARCHAR(64)            NOT NULL,
  sex      VARCHAR(64)            NOT NULL,
  birthday TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  address  VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE admin (
  adminId       INTEGER AUTO_INCREMENT NOT NULL,
  adminAccount  VARCHAR(64)            NOT NULL,
  adminPassword VARCHAR(64)            NOT NULL,
  address       VARCHAR(255),
  PRIMARY KEY (adminId)
);


CREATE TABLE orders (
  id         INTEGER AUTO_INCREMENT NOT NULL,
  user_id    INTEGER                NOT NULL,
  number     VARCHAR(32)            NOT NULL,
  createtime TIMESTAMP               NOT NULL,
  note       VARCHAR(100),
  PRIMARY KEY (id)
);


CREATE TABLE order_detail (
  id        INTEGER AUTO_INCREMENT NOT NULL,
  orders_id INTEGER                NOT NULL,
  items_id  INTEGER                NOT NULL,
  items_num INTEGER,
  PRIMARY KEY (id)
);


CREATE TABLE items(
  id        INTEGER AUTO_INCREMENT NOT NULL,
  name VARCHAR(32) NOT NULL ,
  price FLOAT(10,1) NOT NULL ,
  detail TEXT ,
  pic VARCHAR(512) ,
  createtiem TIMESTAMP ,
  PRIMARY KEY (id)
);
