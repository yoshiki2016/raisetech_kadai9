-- 映画情報を管理するテーブルの作成
DROP TABLE IF EXISTS movies;

CREATE TABLE movies (
 id int unsigned AUTO_INCREMENT,
 movie_title VARCHAR(100) NOT NULL,
 published_year int,
 PRIMARY KEY(id)
);

-- 映画情報の初期データ作成
INSERT INTO movies (id, movie_title, published_year) VALUES (1, "アルマゲドン", 2000);
INSERT INTO movies (id, movie_title, published_year) VALUES (2, "トイ・ストーリー2", 2000);
INSERT INTO movies (id, movie_title, published_year) VALUES (3, "ターミネーター2", 1991);
