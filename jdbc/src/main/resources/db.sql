Drop user tomcat;

CREATE USER tomcat with ENCRYPTED PASSWORD 'password';

DROP SCHEMA web_app CASCADE;
CREATE SCHEMA web_app;

CREATE TABLE web_app.pages(
  id INT PRIMARY KEY ,
  type VARCHAR(10),
  nickname VARCHAR(30) NOT NULL ,
  first_name VARCHAR(100) NOT NULL ,
  second_name VARCHAR(30) NOT NULL ,
  dob DATE,
  timezone VARCHAR(20),
  language VARCHAR(7),
  last_post_id INT,
  about VARCHAR(4049)
);

CREATE TABLE web_app.accounts(
  login VARCHAR(30) PRIMARY KEY ,
  h_password VARCHAR(100),
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

CREATE TABLE web_app.comments
(
  page_id INT,
  post_id INT,
  commentator_id INT,
  comment_id INT,
  content VARCHAR(4096),
  time TIMESTAMP,
  CONSTRAINT "post_id_fk" FOREIGN KEY (page_id, post_id) REFERENCES web_app.posts (page_id, post_id) ON DELETE CASCADE,
  CONSTRAINT "page_initials" FOREIGN KEY (commentator_id) REFERENCES web_app.pages (id)  ON DELETE CASCADE
);
CREATE INDEX ON web_app.comments (page_id, post_id);

CREATE TABLE web_app.subscribe(
  subscriber_id INT,
  source_id INT,
  PRIMARY KEY (subscriber_id, source_id)

);

CREATE TABLE web_app.page_avatars(
  page_id INT REFERENCES web_app.pages(id) ON DELETE CASCADE,
  ava_id VARCHAR(20),
  PRIMARY KEY (page_id)

);

GRANT USAGE ON SCHEMA web_app to tomcat;
GRANT ALL ON ALL SEQUENCES IN SCHEMA web_app TO tomcat;
GRANT ALL ON ALL TABLES IN SCHEMA web_app TO tomcat;
GRANT CONNECT ON DATABASE postgres to tomcat;
