package com.example.kadai9;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MovieMapperTest {
    @Autowired
    MovieMapper movieMapper;

    @Test
    @DataSet(value = "movieList.yml")
    @Transactional
    public void すべての映画が取得できること() {
        List<Movie> movieList = movieMapper.findAll();
        assertThat(movieList)
            .hasSize(3)
            .contains(
                    new Movie(1, "アルマゲドン", 2000),
                    new Movie(2, "トイ・ストリー2", 2000),
                    new Movie(3, "機動戦士ガンダム 逆襲のシャア", 1988)
            );
    }

    @Test
    @DataSet(value = "movieEmpty.yml")
    @Transactional
    public void 映画が存在しない時は空のListを返すこと() {
        List<Movie> movieList = movieMapper.findAll();
        assertThat(movieList).isEmpty();
    }

    @Test
    @DataSet(value = "movieList.yml")
    @Transactional
    public void 指定の年の映画が取得できること() {
        List<Movie> movieList = movieMapper.findMovies(1988);
        assertThat(movieList)
            .hasSize(1)
            .contains(
                    new Movie(3, "機動戦士ガンダム 逆襲のシャア", 1988)
            );
    }

    @Test
    @DataSet(value = "movieList.yml")
    @Transactional
    public void 指定のIDの映画が取得できること() {
        Optional<Movie> movie = movieMapper.findMovieById(1);
        assertThat(movie)
                .containsExactly(
                        new Movie(1, "アルマゲドン", 2000)
                );
    }

    @Test
    @DataSet(value = "movieList.yml")
    @Transactional
    public void 指定したidの映画が存在しない時空のOptionalを返すこと() {
        Optional<Movie> movie = movieMapper.findMovieById(100);
        assertThat(movie).isEmpty();
    }

    @Test
    @Transactional
    @DataSet(value = "movieList.yml")
    @ExpectedDataSet(value = "movieCreateList.yml", ignoreCols = "id")
    public void DBに新規映画が追加された際にidがオートインクリメントされること() {
        // 作成処理の前後でidの有無を確かめる。
        Movie movie4 = new Movie("借りぐらしのアリエッティ", 2010);
        assertThat(movie4.getId()).isEqualTo(0);
        movieMapper.createMovie(movie4);
        assertThat(movie4.getId()).isGreaterThan(0);

        Movie movie5 = new Movie("ドラゴンボールZ 神と神", 2013);
        assertThat(movie5.getId()).isEqualTo(0);
        movieMapper.createMovie(movie5);
        assertThat(movie5.getId()).isGreaterThan(0);

        // idがオートインクリメントされていることを確認する。
        // 後で作ったmovie5のidの方が大きいことを確認する。
        assertThat(movie5.getId()).isGreaterThan(movie4.getId());
    }

}
