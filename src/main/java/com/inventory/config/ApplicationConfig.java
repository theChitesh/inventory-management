package com.inventory.config;


import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Application Config class which register modules for Application
 * @author chitesh
 *
 */
@Configuration
public class ApplicationConfig {

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule())
        .setSerializationInclusion(Include.NON_ABSENT)
        .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"))
        .setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC))
        .registerModule(new JsonOrgModule());
  }
}
