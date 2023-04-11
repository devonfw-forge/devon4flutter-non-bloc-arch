CREATE TABLE EMPLOYEE (
  id BIGINT auto_increment,
  modificationCounter INTEGER NOT NULL,
  employeeid BIGINT auto_increment,
  name VARCHAR(255),
  surname VARCHAR(255),
  email VARCHAR(255), .
  PRIMARY KEY (employeeid));