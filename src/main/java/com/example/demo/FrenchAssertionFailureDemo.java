package com.example.demo;

import static org.zalando.problem.spring.web.advice.MediaTypes.PROBLEM;

import java.util.Optional;

import javax.validation.constraints.Size;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.validation.ConstraintViolationProblemModule;

@Validated
@RestController
@ControllerAdvice
@SpringBootApplication
public class FrenchAssertionFailureDemo implements ProblemHandling {

  public static final String PROBLEM_UTF8_VALUE = "application/problem+json;charset=UTF-8";
  public static final MediaType PROBLEM_UTF8 = MediaType.parseMediaType(PROBLEM_UTF8_VALUE);

  public static void main(String[] args) {
    SpringApplication.run(FrenchAssertionFailureDemo.class, args);
  }

// Uncomment this and the assertion will succeed
//
//  @Override
//  public Optional<MediaType> negotiate(final NativeWebRequest request) {
//
//    final Optional<MediaType> mediaType = ProblemHandling.super.negotiate(request);
//    return mediaType
//        .filter(PROBLEM::equals)
//        // Using Spring Boot prior 2.0.0, use the following
//        .map(mt -> Optional.of(PROBLEM_UTF8))
//        // Using Spring Boot 2.0.0 and above, use the following
//        // .map(mt -> Optional.of(MediaType.APPLICATION_PROBLEM_JSON_UTF8))
//        .orElse(mediaType);
//  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jacksonBuilder() {

    return builder ->
        // We must register problem-spring-web modules. See:
        // https://github.com/zalando/problem-spring-web#configuration
        builder.modulesToInstall(new ProblemModule(), new ConstraintViolationProblemModule());
  }

  @PostMapping("/api/foo/{code}")
  public ResponseEntity<String> test(
      @PathVariable
          @Size(
            min = 10,
            max = 20,
            message = "Le code doit contenir entre {min} et {max} caractères"
          )
          final String code) {

    return ResponseEntity.ok("Foo");
  }
}
