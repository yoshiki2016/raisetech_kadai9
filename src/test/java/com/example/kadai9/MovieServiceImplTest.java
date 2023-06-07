package com.example.kadai9;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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

    @Test
    public void 映画を登録できること() {
        Movie testMovie = new Movie("名探偵コナン 黒鉄の魚影(サブマリン)",2023);
        doNothing().when(movieMapper).createMovie(testMovie);
        movieServiceImpl.createMovie(new CreateMovieForm("名探偵コナン 黒鉄の魚影(サブマリン)",2023));
        verify(movieMapper).createMovie(testMovie);
    }

    @Test
    public void 映画を更新できること() {
        doNothing().when(movieMapper).updateMovie(new Movie(1, "名探偵コナン 黒鉄の魚影(サブマリン)",2023));
        movieServiceImpl.updateMovie(new Movie(1, "名探偵コナン 黒鉄の魚影(サブマリン)",2023));
        verify(movieMapper).updateMovie(new Movie(1, "名探偵コナン 黒鉄の魚影(サブマリン)",2023));
    }

    @Test
    public void 映画を削除できること() {
        doNothing().when(movieMapper).deleteMovie(1);
        movieServiceImpl.deleteMovie(1);
        verify(movieMapper).deleteMovie(1);
    }
}
