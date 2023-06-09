package com.example.kadai9.controller;

import com.example.kadai9.entity.Movie;
import com.example.kadai9.form.MovieForm;
import com.example.kadai9.service.MovieServiceImpl;
import com.example.kadai9.exception.ResourceNotFoundException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc.perform(get("/movies?publishedYear=1988")
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

    @Test
    public void 指定のIDの映画が取得できること() throws Exception {
        Movie movie = new Movie(1, "アルマゲドン", 2000);
        given(movieServiceImpl.findMovieById(1)).willReturn(movie);
        mockMvc.perform(get("/movies/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                           "id":1,
                           "movieTitle":"アルマゲドン",
                           "publishedYear":2000
                        }
                        """));
    }

    @Test
    void publishedYearに不正な値を入力したら400エラーになること() throws Exception {
        mockMvc.perform(get("/movies?publishedYear=aaa"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 映画が登録できること() throws Exception {
        MovieForm movieForm = new MovieForm("鋼の錬金術師 嘆きの丘の聖なる星", 2011);   // 画面からの入力値
        Movie createMovie = new Movie(10, movieForm.getMovieTitle(), movieForm.getPublishedYear()); // 10はオートインクリメントの結果
        given(movieServiceImpl.createMovie(movieForm.getMovieTitle(), movieForm.getPublishedYear())).willReturn(createMovie);

        // JSON形式のデータに変換
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(movieForm);

        String response = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "message" : "the movie successfully created"
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    public void 不正な内容で映画を新規登録すると失敗すること() throws Exception {
        mockMvc.perform(post("/movies")
                        // 入力を空で受け付けた場合
                        .content("""
                        {
                           "movieTitle":"",
                           "publishedYear":
                        }
                        """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void 映画を更新できること() throws Exception {
        int id = 10;
        MovieForm movieForm = new MovieForm("鋼の錬金術師 嘆きの丘の聖なる星", 2011);   // 画面からの入力値
        doNothing().when(movieServiceImpl).updateMovie(id, movieForm.getMovieTitle(), movieForm.getPublishedYear());

        // JSON形式のデータに変換
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(movieForm);

        String response = mockMvc.perform(patch("/movies/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "message" : "the movie successfully updated"
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    public void 不正な内容で映画を更新すると失敗すること() throws Exception {
        mockMvc.perform(patch("/movies/10")
                        // 入力を空で受け付けた場合
                        .content("""
                        {
                           "movieTitle":"",
                           "publishedYear":
                        }
                        """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void 存在しないIDの映画を更新した際にResourceNotFoundExceptionを返すこと() throws Exception {
        int id = 1000; // 存在しないID
        MovieForm movieForm = new MovieForm("鋼の錬金術師 嘆きの丘の聖なる星", 2011);   // 画面からの入力値
        Mockito.doThrow(new ResourceNotFoundException("resource not found")).when(movieServiceImpl).updateMovie(id, movieForm.getMovieTitle(), movieForm.getPublishedYear());

        // JSON形式のデータに変換
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(movieForm);

        mockMvc.perform(patch("/movies/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNotFound()); // statusが404であることの確認
    }

    @Test
    public void 指定の映画を削除できること() throws Exception {
        int id = 10;
        doNothing().when(movieServiceImpl).deleteMovie(id);
        String response = mockMvc.perform(delete("/movies/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "message" : "the movie successfully deleted"
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    public void 存在しないIDの映画を削除した際にResourceNotFoundExceptionを返すこと() throws Exception {
        int id = 1000; // 存在しないID
        doThrow(new ResourceNotFoundException("resource not found")).when(movieServiceImpl).deleteMovie(id);

        mockMvc.perform(delete("/movies/1000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // statusが404であることの確認
    }

}
