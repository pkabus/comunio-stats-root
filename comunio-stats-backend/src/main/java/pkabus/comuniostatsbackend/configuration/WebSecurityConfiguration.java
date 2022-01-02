package pkabus.comuniostatsbackend.configuration;

import static pkabus.comuniostatsbackend.web.controller.FlatPlayerSnapshotController.BASE_FLAT_SNAPSHOTS;
import static pkabus.comuniostatsbackend.web.controller.FlatPlayerSnapshotController.CREATE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser("crawler").password(encoder.encode("password")).authorities("ROLE_CRAWLER");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests() //
				.antMatchers(BASE_FLAT_SNAPSHOTS + CREATE) //
				.hasRole("CRAWLER") //
				.and() //
				.httpBasic() // TODO no basic authentication!!!
				.and() //
				.antMatcher("/**") //
				.anonymous().and().csrf().disable();
	}
}