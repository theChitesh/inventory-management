package com.inventory.config;

import javax.inject.Inject;
import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
	
	
  private final AuthenticationProvider authenticationProvider;

  @Inject
  public SecurityConfiguration(final AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
  }

  private static FilterRegistrationBean filterRegistrationBean(final Filter filter) {
    final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(filter);
    filterRegistrationBean.setEnabled(false);
    return filterRegistrationBean;
  }

  @Configuration
  public class ManagementApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
	  
    private final InventoryFilter inventoryFilter;

    @Inject
    public ManagementApiSecurityConfigurationAdapter(final InventoryFilter inventoryFilter) {
      this.inventoryFilter = inventoryFilter;
    }

    @Inject
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public FilterRegistrationBean jwtAuthenticationFilterRegistration() {
      return filterRegistrationBean(inventoryFilter);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
    	
      // disable caching
      http.headers().cacheControl();

      http.csrf()
          .disable() //CSRF irrelevant for JWT auth
          .authorizeRequests()
          .anyRequest().authenticated().and()
          .authorizeRequests()
          .and()
          .addFilterBefore(inventoryFilter, UsernamePasswordAuthenticationFilter.class);
    }
  }
}