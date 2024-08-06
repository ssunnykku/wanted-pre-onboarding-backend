package com.wanted.pre_onboarding.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class JobPostingDTOTest {

    private Validator validator;

    @Test
    void validationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        JobPostingDTO jobPostingDTO = new JobPostingDTO();

        jobPostingDTO.setCompanyId(null);
        jobPostingDTO.setPosition("  ");
        jobPostingDTO.setCompensation(0);
        jobPostingDTO.setSkill(null);

        Set<ConstraintViolation<JobPostingDTO>> violations = validator.validate(jobPostingDTO);
        for (ConstraintViolation<JobPostingDTO> violation : violations) {
            log.info("violation=" + violation);
            log.info("violation.message=" + violation.getMessage());
        }
    }

}