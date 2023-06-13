package com.example.kadai9.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

//    404エラーの単体テストの実装は後回し
//    @Test
//    @DataSet(value = "movieList.yml")
//    @Transactional
//    void 存在しないIDの映画を取得しようとすると404エラーになること() throws Exception {
//        ZonedDateTime zonedDateTime = ZonedDateTime.of(2023, 6, 16, 13, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
//        try(MockedStatic<ZonedDateTime> zonedDateTimeMockedStatic = Mockito.mockStatic(ZonedDateTime.class)){
//            String response = mockMvc.perform(get("/movies/100"))
//                    .andExpect(status().isNotFound())
//                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
//            JSONAssert.assertEquals("""
//               {
//                  "timestamp": 2023-06-16T13:00+09:00[Asia/Tokyo]",
//                  "message": "resource not found",
//                  "status": "404",
//                  "path": "/movies/100",
//                  "error": "Not Found"
//               }
//                """, response, true);
//        }
//    }
}
