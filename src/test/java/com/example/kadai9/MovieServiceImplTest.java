package com.example.kadai9;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {
    @InjectMocks
    MovieServiceImpl movieServiceImpl;

    @Mock
    MovieMapper movieMapper;

    @Test
    public void 存在する映画の年数を指定したときに正常に映画が返されること() {
        List<Movie> testMovies = List.of(new Movie(1, "アルマゲドン", 1998));
        doReturn(testMovies)
                .when(movieMapper).findMovies(1998);

        List<Movie> actual = movieServiceImpl.findMovies(1998);
        assertThat(actual)
                .isEqualTo(testMovies);
    }

    @Test
    public void 全映画が返されること() {
        List<Movie> testMovies = List.of(new Movie(1, "アルマゲドン", 1998), new Movie(2, "クレヨンしんちゃん電撃!ブタのヒヅメ大作戦", 1998));
        doReturn(testMovies)
                .when(movieMapper).findAll();

        List<Movie> actual = movieServiceImpl.findMovies(null);
        assertThat(actual)
                .isEqualTo(testMovies);
    }

}
