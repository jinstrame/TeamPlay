Drop user tomcat;
CREATE USER tomcat with ENCRYPTED PASSWORD 'password';

CREATE SCHEMA web_app;

DROP SCHEMA web_app CASCADE;
CREATE SCHEMA web_app;

CREATE TABLE web_app.pages(
  id INT PRIMARY KEY ,
  type VARCHAR(10),
  nickname VARCHAR(30) NOT NULL ,
  first_name VARCHAR(100) NOT NULL ,
  second_name VARCHAR(30) NOT NULL ,
  dob DATE,
  language VARCHAR(7),
  main_post_id INT,
  last_post_id INT
);

CREATE TABLE web_app.accounts(
  login VARCHAR(30) PRIMARY KEY ,
  h_password VARCHAR(60),
  token INT,
  person_id INT REFERENCES web_app.pages(id) ON DELETE CASCADE
);

CREATE TABLE web_app.posts(
  page_id INT REFERENCES web_app.pages(id) ON DELETE CASCADE ,
  post_id INT,
  prev_post_id INT,
  post_time TIMESTAMP ,
  content VARCHAR(4096),
  PRIMARY KEY (page_id, post_id)
);

CREATE TABLE web_app.game_participants(
  page_id INT REFERENCES web_app.pages(id) ON DELETE CASCADE,
  game_id VARCHAR(20),
  account_id VARCHAR(100),
  rank VARCHAR(10),
  PRIMARY KEY (page_id, game_id)
);

CREATE TABLE web_app.org_participants(
  participant_id INT REFERENCES web_app.pages(id) ON DELETE CASCADE,
  organisation_id INT REFERENCES web_app.pages(id) ON DELETE CASCADE,
  participation_type VARCHAR(10)
);

GRANT USAGE ON SCHEMA web_app to tomcat;
GRANT ALL ON ALL SEQUENCES IN SCHEMA web_app TO tomcat;
GRANT ALL ON ALL TABLES IN SCHEMA web_app TO tomcat;
GRANT CONNECT ON DATABASE postgres to tomcat;
