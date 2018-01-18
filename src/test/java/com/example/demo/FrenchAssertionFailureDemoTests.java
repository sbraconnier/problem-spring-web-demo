package com.example.demo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.spring.web.advice.MediaTypes;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FrenchAssertionFailureDemoTests {

  @Autowired private MockMvc mockMvc;

  @Test
  public void tooShortCode() throws Exception {

    // Send the creation request.
    mockMvc
        .perform(
            post("/api/foo/tooShort"))
        // Verify that the response status is BAD_REQUEST (400).
        .andExpect(status().isBadRequest())
        // Verify that the response content type is compatible with application/problem+json
        .andExpect(content().contentTypeCompatibleWith(MediaTypes.PROBLEM_VALUE))
        // Verify the content, which is supposed to be the validation errors.
        .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("title").value("Constraint Violation"))
        .andExpect(jsonPath("violations", hasSize(1)))
        .andExpect(jsonPath("violations[0].field", equalTo("test.code")))
        .andExpect(
            jsonPath(
                "violations[0].message",
                equalTo("Le code doit contenir entre 10 et 20 caractères")));
  }
}
