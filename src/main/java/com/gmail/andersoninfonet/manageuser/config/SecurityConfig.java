package com.gmail.andersoninfonet.manageuser.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gmail.andersoninfonet.manageuser.security.Http403EntryPoint;
import com.gmail.andersoninfonet.manageuser.security.JwtAuthenticationEntryPoint;
import com.gmail.andersoninfonet.manageuser.security.filter.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
	private Http403EntryPoint http403EntryPoint;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean(name = "http403ForbiddenEntryPoint")
	public Http403ForbiddenEntryPoint authenticationEntryPoint() {
		return http403EntryPoint;
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		var authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
		providers.add(authenticationProvider());
		ProviderManager provider = new ProviderManager(providers);
		return provider;
	}
	
	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean()throws Exception {
		return new JwtAuthenticationTokenFilter();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/swagger-ui*","/webjars/**","/swagger-resources/**", "/h2_console/**");
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception{
    	//configuração utilizada para desativar a segurança para facilitar o desenvolvimento e conseguir utilizar o h2 console
//    	http.authorizeRequests()
//        	.antMatchers("/").permitAll()
//        	.antMatchers("/h2_console/**").permitAll();
//    	http.csrf().disable();
//    	http.headers().frameOptions().disable();
    	//fim dev config
    	
    	http.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
    	.and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authorizeRequests()
		.antMatchers("/auth/**").permitAll()
		.antMatchers("/swagger-ui*").permitAll()
		.antMatchers("/v2/api-docs/**").permitAll()
		.anyRequest().authenticated();
    	http.addFilterBefore( authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    	http.headers().cacheControl();
    	
    }
}
