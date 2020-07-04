package com.kaicoinico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApplicationConfig extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(ApplicationConfig.class, args);
  }

  /* 외부 톰캣컨테이너에서 스프링 부트를 사용하기 위한 스프링 어플리케이션 빌더 설정 */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(ApplicationConfig.class);
    
  }
}
