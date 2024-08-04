package com.wanted.pre_onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.pre_onboarding.dto.JobPostingUserDTO;
import com.wanted.pre_onboarding.service.JobPostingService;
import com.wanted.pre_onboarding.service.JobPostingUserService;
import com.wanted.pre_onboarding.util.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class JobPostingUserControllerTest {
    @Autowired
    TestUtil testUtil;
    @Mock
    JobPostingUserService jobPostingUserService;
    @InjectMocks
    JobPostingUserController jobPostingUserController ;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(jobPostingUserController)
                .build();
    }

    @Test
    @DisplayName("채용공고 지원")
    void applyJobPosting() throws Exception {

        Long jobPostingId = 1L;
        UUID userId = UUID.fromString("aa815892-d059-4efe-81b8-58dd20a34a96");

        JobPostingUserDTO jobPostingUserDTO = JobPostingUserDTO.builder()
                .jobPostingId(jobPostingId)
                .userId(userId)
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/job-postings/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobPostingUserDTO))
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }
}