package com.youtube_timestamp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube_timestamp.api.curation.dto.CurationCreateDTO;
import com.youtube_timestamp.api.curation.entity.Curation;
import com.youtube_timestamp.api.curation.entity.Timestamp;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurationE2ETest extends BaseTest {

    private final String domain = "/api/v1/curation";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    List<Curation> curations = new ArrayList<>();

    @BeforeAll
    void beforeAll() {
        deleteAll();
        loader();
        createCurations();
    }


    public void createCurations(){
        for(int i=0; i<10;i++){
            CurationCreateDTO dto = new CurationCreateDTO();
            dto.setTitle("curation title"+ i);
            dto.setYoutube_url("youtube_url");
            dto.setUserEntity(userEntity);
            List<Timestamp> tsl = new ArrayList<>();
            Timestamp ts = new Timestamp();
            ts.setTimestamp("13:33");
            ts.setTitle("timestamp title");
            tsl.add(ts);
            dto.setTimestamp(tsl);
            curations.add(curationService.create(dto));
        }
    }

    @Test
    public void 큐레이션_생성() throws Exception {
        CurationCreateDTO dto = new CurationCreateDTO();
        dto.setTitle("curation title");
        dto.setYoutube_url("youtube_url");
        List<Timestamp> tsl = new ArrayList<>();
        Timestamp ts = new Timestamp();
        ts.setTimestamp("13:33");
        ts.setTitle("timestamp title");
        tsl.add(ts);
        dto.setTimestamp(tsl);
        mockMvc.perform(postRequest(domain + "/", dto)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 큐레이션_디테일() throws Exception{
        mockMvc.perform(getRequest(domain + "/"+curations.get(0).getId())).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 내_큐레이션_리스트() throws Exception{
        mockMvc.perform(getRequest(domain + "/me")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 큐레이션_기본_페이징() throws Exception{
        mockMvc.perform(getRequest(domain + "/")).andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.pageable").exists())
                .andExpect(jsonPath("$.content").exists());
    }


    @Test
    public void 큐레이션_인기_급상승_리스트() throws Exception{
        mockMvc.perform(getRequest(domain + "/"+curations.get(0).getId())).andExpect(status().isOk());
        mockMvc.perform(getRequest(domain + "/"+curations.get(1).getId())).andExpect(status().isOk());
        mockMvc.perform(getRequest(domain + "/"+curations.get(1).getId())).andExpect(status().isOk());
        mockMvc.perform(getRequest(domain + "/"+curations.get(2).getId())).andExpect(status().isOk());
        mockMvc.perform(getRequest(domain + "/"+curations.get(2).getId())).andExpect(status().isOk());
        mockMvc.perform(getRequest(domain + "/"+curations.get(2).getId())).andExpect(status().isOk());
        mockMvc.perform(getRequest(domain + "/pop")).andExpect(status().isOk())
                .andDo(print());
    }



    @Test
    public void 큐레이션_수정() throws Exception{

    }

    @Test
    public void 큐레이션_삭제() throws Exception{

    }


    @AfterAll
    void afterAll() throws Exception {

    }
}
