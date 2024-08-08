package com.wanted.pre_onboarding.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.pre_onboarding.controller.JobPostingController;
import com.wanted.pre_onboarding.dto.JobPostingDTO;
import com.wanted.pre_onboarding.dto.JobPostingUpdateDTO;
import com.wanted.pre_onboarding.repository.JobPostingRepository;
import com.wanted.pre_onboarding.service.JobPostingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ControllerAdviceTest {

    @Autowired
    private MockMvc mockMvc;

//    @Autowired
//    private JobPostingRepository jobPostingRepository;

    @Mock
    private JobPostingService jobPostingService;

    @InjectMocks
    private JobPostingController jobPostingController;

    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostingController)
                .setControllerAdvice(new ControllerAdvice())
                .build();
    }

    @Test
    @DisplayName("[Exception] 채용 공고 등록: position이 null일 때")
    public void handleValidationExceptions() throws Exception {

        UUID companyId = UUID.fromString("aa815892-d059-4efe-81b8-58dd20a34a96");

        JobPostingDTO jobPostingDTO = JobPostingDTO.builder()
                .position(null)
                .compensation(1000000)
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .skill("Python")
                .companyId(companyId)
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/job-postings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobPostingDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("VALIDATION_ERROR"))
                .andDo(print());

    }

//    @AfterEach
//    public void AfterEach() {
//        jobPostingRepository.deleteAll();
//
//    }

}