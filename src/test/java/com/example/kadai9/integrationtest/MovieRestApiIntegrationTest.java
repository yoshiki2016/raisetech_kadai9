package com.example.kadai9.integrationtest;

import com.example.kadai9.MovieForm;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
public class MovieRestApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "movieList.yml")
    @Transactional
    void 映画が全件取得できること() throws Exception {
        String response = mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                [
                   {
                      "id":1,
                      "movieTitle":"アルマゲドン",
                      "publishedYear":2000
                   },
                   {
                      "id":2,
                      "movieTitle":"トイ・ストリー2",
                      "publishedYear":2000
                   },
                   {
                      "id":3,
                      "movieTitle":"機動戦士ガンダム 逆襲のシャア",
                      "publishedYear":1988
                   }
                ]
                """, response, JSONCompareMode.STRICT);
    }


    @Test
    @DataSet(value = "movieList.yml")
    @Transactional
    void 指定の年の映画が取得できること() throws Exception {
        String response = mockMvc.perform(get("/movies?publishedYear=1988"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                [
                   {
                      "id":3,
                      "movieTitle":"機動戦士ガンダム 逆襲のシャア",
                      "publishedYear":1988
                   }
                ]
                """, response, JSONCompareMode.STRICT);
    }


    @Test
    @DataSet(value = "movieList.yml")
    @Transactional
    void 指定のIDの映画が取得できること() throws Exception {
        String response = mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
               {
                  "id":1,
                  "movieTitle":"アルマゲドン",
                  "publishedYear":2000
               }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "movieList.yml")
    @Transactional
    void 存在しないIDの映画を取得しようとすると404エラーになること() throws Exception {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2023, 6, 16, 13, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
        try(MockedStatic<ZonedDateTime> zonedDateTimeMockedStatic = Mockito.mockStatic(ZonedDateTime.class)){
            zonedDateTimeMockedStatic.when(ZonedDateTime::now).thenReturn(zonedDateTime);

            String response = mockMvc.perform(get("/movies/100"))
                    .andExpect(status().isNotFound())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            JSONAssert.assertEquals("""
               {
                  "timestamp": "2023-06-16T13:00+09:00[Asia/Tokyo]",
                  "message": "resource not found",
                  "status": "404",
                  "path": "/movies/100",
                  "error": "Not Found"
               }
                """, response, true);
        }
    }

    @Test
    @DataSet(value = "movieList.yml")
    @Transactional
    void publishedYearに不正な値を入力したら400エラーになること() throws Exception {
        mockMvc.perform(get("/movies?publishedYear=aaa"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DataSet(value = "movieList.yml")
    @ExpectedDataSet(value = "movieCreateList.yml", ignoreCols = "id")
    void 映画を登録できること() throws  Exception {
        MovieForm movieForm1 = new MovieForm("借りぐらしのアリエッティ", 2010);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody1 = ow.writeValueAsString(movieForm1);
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody1))
                .andExpect(status().isCreated()); // status codeが201であること
        MovieForm movieForm2 = new MovieForm("ドラゴンボールZ 神と神", 2013);
        String requestBody2 = ow.writeValueAsString(movieForm2);
        String response = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody2))
                .andExpect(status().isCreated()) // status codeが201であること
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "message" : "the movie successfully created"
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @Transactional
    @DataSet(value = "movieList.yml")
    void 不正な内容で映画を新規登録すると失敗すること() throws Exception {
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
                .andExpect(status().isBadRequest());
    }


}
