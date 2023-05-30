DROP TABLE IF EXISTS names;

CREATE TABLE names (
 id int unsigned AUTO_INCREMENT,
 name VARCHAR(20) NOT NULL,
 PRIMARY KEY(id)
);

INSERT INTO names (id, name) VALUES (1, "tanaka");
INSERT INTO names (id, name) VALUES (2, "suzuki");
INSERT INTO names (id, name) VALUES (3, "yamada");

-- 映画情報を管理するテーブルの作成
DROP TABLE IF EXISTS movies;

CREATE TABLE movies (
 id int unsigned AUTO_INCREMENT,
 movie_name VARCHAR(100) NOT NULL,
 published_year int,
 PRIMARY KEY(id)
);

-- 映画情報の初期データ作成
INSERT INTO movies (id, movie_name, published_year) VALUES (1, "アルマゲドン", 2000);
INSERT INTO movies (id, movie_name, published_year) VALUES (2, "トイ・ストーリー2", 2000);
INSERT INTO movies (id, movie_name, published_year) VALUES (3, "ターミネーター2", 1991);
