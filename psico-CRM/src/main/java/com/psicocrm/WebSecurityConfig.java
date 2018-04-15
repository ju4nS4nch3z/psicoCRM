package com.psicocrm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/login").permitAll()
				.antMatchers("/registration").permitAll().antMatchers("/resources/**", "/registration").permitAll()
				.antMatchers("/resetPassword").permitAll().antMatchers("/resources/**", "/resetPassword").permitAll()
				.anyRequest().authenticated();
		// .and().formLogin()
		// .successHandler(customAuthenticationSuccessHandler)
		// .loginPage("/login")
		// .defaultSuccessUrl("/dashboard", true)
		// .permitAll()
		// .usernameParameter("email").passwordParameter("clau")
		// .and().logout().permitAll();
		http.formLogin().loginPage("/login").defaultSuccessUrl("/dashboard").usernameParameter("mail")
		.passwordParameter("password").permitAll();
	/*
		http.formLogin().loginPage("/login").defaultSuccessUrl("/dashboard").usernameParameter("mail")
				.passwordParameter("password").failureUrl("/login").permitAll();
*/
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID").invalidateHttpSession(true);
		
		http.exceptionHandling().accessDeniedPage("/error");
	}

	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
}