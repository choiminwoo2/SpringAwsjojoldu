package com.jojoldu.webservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//JpaAuditing을 등록하는데.
// 테스트할때 어플리케이션에 직접 어노테이션을 달면
// 문제가 발생하므로 설정파일로 따로 만들어서 사용!.
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
