package com.example.kadai9;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieServiceImpl movieServiceImpl;

    @Test
    public void 全件取得できること() throws Exception {
        List<Movie> movieList = List.of(
                new Movie(1, "アルマゲドン", 2000),
                new Movie(2, "トイ・ストーリー2", 2000),
                new Movie(3, "機動戦士ガンダム 逆襲のシャア", 1988)
        );

        given(movieServiceImpl.findMovies(null)).willReturn(movieList);

        // ステータスとサイズの確認
        mockMvc.perform(get("/movies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().json("""
                        [
                        {
                        "id":1,
                        "movieTitle":"アルマゲドン",
                        "publishedYear":2000
                        },
                        {
                        "id":2,
                        "movieTitle":"トイ・ストーリー2",
                        "publishedYear":2000
                        },
                        {
                        "id":3,
                        "movieTitle":"機動戦士ガンダム 逆襲のシャア",
                        "publishedYear":1988
                        }
                        ]
                        """));
    }

    @Test
    public void 年数指定で映画を検索できること() throws Exception {
        List<Movie> movieList = List.of(
                new Movie(3, "機動戦士ガンダム 逆襲のシャア", 1988)
        );

        given(movieServiceImpl.findMovies(1988)).willReturn(movieList);

        // ステータスとサイズの確認
        mockMvc.perform(get("/movies?publishedYear=1998")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().json("""
                        [
                        {
                        "id":3,
                        "movieTitle":"機動戦士ガンダム 逆襲のシャア",
                        "publishedYear":1988
                        }
                        ]
                        """));
    }
}
