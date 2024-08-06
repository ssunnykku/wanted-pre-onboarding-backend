package com.wanted.pre_onboarding.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.pre_onboarding.domain.Company;
import com.wanted.pre_onboarding.dto.JobPostingDTO;
import com.wanted.pre_onboarding.dto.JobPostingUpdateDTO;
import com.wanted.pre_onboarding.service.JobPostingService;
import com.wanted.pre_onboarding.util.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@ActiveProfiles("test")
class JobPostingControllerTest {
    @Autowired
    TestUtil testUtil;
    @Mock
    JobPostingService jobPostingService;
    @InjectMocks
    JobPostingController jobPostingController;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(jobPostingController)
                .build();
    }

    @Test
    @DisplayName("채용공고 등록")
    void addJobPosting() throws Exception {

        UUID companyId = UUID.fromString("aa815892-d059-4efe-81b8-58dd20a34a96");

        JobPostingDTO jobPostingDTO = JobPostingDTO.builder()
                .position("백엔드 주니어 개발자")
                .compensation(1000000)
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .skill("Python")
                .companyId(companyId)
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/job-postings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobPostingDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("채용공고 수정")
    void editJobPosting() throws Exception{
        //given
        Long jobPostingId = 1L;

        UUID companyId = UUID.fromString("aa815892-d059-4efe-81b8-58dd20a34a96");

        JobPostingUpdateDTO editDto =
                JobPostingUpdateDTO.builder()
                        .jobPostingId(jobPostingId)
                        .position("백엔드 주니어 개발자")
                        .compensation(1500000)
                        .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                        .skill("Python")
                        .build();

        // stub
        BDDMockito.willDoNothing().given(jobPostingService).editJobPosting(jobPostingId, editDto);

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/job-postings/{jobPostingId}", jobPostingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
        //then
        BDDMockito.verify(jobPostingService).editJobPosting(jobPostingId, editDto);

    }

    @Test
    @DisplayName("채용공고 삭제")
    void removeJobPosting() throws Exception {
        //given
        Long jobPostingId = 1L;

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/job-postings/{jobPostingId}", jobPostingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //then
        BDDMockito.verify(jobPostingService).removeJobPosting(jobPostingId);
    }

    @Test
    @JsonProperty

    @DisplayName("채용공고 목록")
    void getJobPostingList() throws Exception {
        //given
        List<JobPostingDTO> resultList = new ArrayList<>();
        UUID companyId = UUID.fromString("aa815892-d059-4efe-81b8-58dd20a34a96");

        JobPostingDTO jobPostingDTO1 = JobPostingDTO.builder()
                .position("백엔드 주니어 개발자")
                .compensation(1000000)
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .skill("Python")
                .companyId(companyId)
                .build();

        JobPostingDTO jobPostingDTO2 = JobPostingDTO.builder()
                .position("프론트엔드 주니어 개발자")
                .compensation(1000000)
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .skill("JavaScript")
                .companyId(companyId)
                .build();

        JobPostingDTO jobPostingDTO3 = JobPostingDTO.builder()
                .position("JAVA 주니어 개발자")
                .compensation(1000000)
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .skill("java")
                .companyId(companyId)
                .build();

        resultList.add(jobPostingDTO1);
        resultList.add(jobPostingDTO2);
        resultList.add(jobPostingDTO3);
        //stub
        BDDMockito.given(jobPostingService.getJobPostingList()).willReturn(resultList);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/job-postings")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(resultList.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].position").value(jobPostingDTO1.getPosition()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].position").value(jobPostingDTO2.getPosition()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].position").value(jobPostingDTO3.getPosition()))
                .andDo(print());
        //then
        BDDMockito.verify(jobPostingService).getJobPostingList();
    }

    @Test
    @DisplayName("채용공고 검색: 원티드")
    @JsonProperty
    @Transactional
    void searchJobPosting() throws Exception {

        //given
        List<JobPostingDTO> resultList = new ArrayList<>();

        UUID companyId1 = UUID.fromString("aa815892-d059-4efe-81b8-58dd20a34a96");
        UUID companyId2 = UUID.fromString("ab815892-d059-4efe-81b8-58dd20a34a96");

        JobPostingDTO jobPosting1 =  JobPostingDTO.builder()
                .jobPostingId(1L)
                .position("백엔드 주니어 개발자")
                .compensation(1500000)
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .skill("Python")
                .companyId(companyId1)
                .companyName("삼성SDS")
                .country("한국")
                .location("서울")
                .build();
        JobPostingDTO jobPosting2 = JobPostingDTO.builder()
                .jobPostingId(2L)
                .position("프론트엔드 개발자")
                .compensation(500000)
                .description("프론트엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .skill("javascript")
                .companyId(companyId2)
                .companyName("원티드코리아")
                .country("한국")
                .location("부산")
                .build();

        resultList.add(jobPosting1);
        resultList.add(jobPosting2);

        //stub
        BDDMockito.given(jobPostingService.searchJobPosting("원티드")).willReturn(resultList);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/job-postings/search?keyword=원티드")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(resultList.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].position").value(jobPosting1.getPosition()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].position").value(jobPosting2.getPosition()))
                .andDo(print());
        //then
        BDDMockito.verify(jobPostingService).searchJobPosting("원티드");
    }

    @Test
    @DisplayName("채용공고 상세 페이지")
    @Transactional
    void getJobPostingDetails() throws Exception  {

        //given
        Long jobPostingId = 1L;

        Company company = testUtil.createCompany("원티드랩","한국","서울");
        List<Long> jobPostingIdList = new ArrayList<>();
        jobPostingIdList.add(2L);
        jobPostingIdList.add(3L);

        JobPostingDTO responseDto =
                JobPostingDTO.builder()
                .jobPostingId(jobPostingId)
                .position("백엔드 주니어 개발자")
                .compensation(1000000)
                .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .skill("Python")
                .companyId(company.getId())
                .companyName("원티드랩")
                .country("한국")
                .location("서울")
                .jobPostingIdList(jobPostingIdList)
                .build();

        //stub
        BDDMockito.given(jobPostingService.getJobPostingDetails(jobPostingId)).willReturn(responseDto);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/job-postings/" + jobPostingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value(responseDto.getPosition()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.skill").value(responseDto.getSkill()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jobPostingIdList.size()").value(jobPostingIdList.size()))
                .andDo(print());

        //then
        BDDMockito.verify(jobPostingService).getJobPostingDetails(jobPostingId);

    }
}