# API仕様についてまとめます。
編集中！<br>
movieテーブルに登録されているデータのうち<br>
指定された年に対応するデータを取得するReadの実装
```
[yoshikishinya@YoshikinoMacBook-Pro] ~
% curl --location 'http://localhost:8080/movies?published_year=2000'
[{"id":1,"movieTitle":"アルマゲドン","publishedYear":2000},{"id":2,"movieTitle":"トイ・ストーリー2","publishedYear":2000}]%
```

