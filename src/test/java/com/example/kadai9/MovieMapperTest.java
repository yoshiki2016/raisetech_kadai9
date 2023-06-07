package com.example.kadai9;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MovieMapperTest {
    @Autowired
    MovieMapper movieMapper;

    @Test
    @DataSet(value = "movies.yml")
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

}