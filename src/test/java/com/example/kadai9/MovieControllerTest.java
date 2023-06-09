package com.example.kadai9;

import org.codehaus.jackson.map.ObjectMapper;
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
                new Movie(3, "機動戦士ガンダム 逆襲のシャア", 2000)
        );

        given(movieServiceImpl.findMovies(null)).willReturn(movieList);

        // ステータスとサイズの確認
        mockMvc.perform(get("/movies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        // JSONデータの突き合わせ
        // やり方検索中
    }
}
