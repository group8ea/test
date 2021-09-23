package edu.miu.appointmentsystem.config;


import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String USERNAME_QUERY = "SELECT a.username , a.password , 1 AS enabled"
			+ "  FROM Account a   WHERE a.username = ?";

	private static final String AUTHORITIES_BY_USERNAME_QUERY = "SELECT a.username , concat('ROLE_',r.roles) AS role "
			+ "  FROM Roles r INNER JOIN Account a ON a.user_id = r.user_user_id"
			+ "  WHERE a.username =?";

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		auth.inMemoryAuthentication()
				.passwordEncoder(encoder)
				.withUser("user")
				.password(encoder.encode("user"))
				.roles("ADMIN");

		auth.authenticationProvider(authProvider()).jdbcAuthentication()
				.passwordEncoder(encoder)
				.dataSource(dataSource)
				.usersByUsernameQuery(USERNAME_QUERY)
				.authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/favicon.ico").permitAll()
				.antMatchers("/users/**").permitAll()
				.antMatchers("/categories/**").permitAll()
				.antMatchers("/appointments/**").permitAll()
//				.antMatchers("/users/**").hasRole("ADMIN")
//				.antMatchers("/countries/**").hasAnyRole("Service", "EM")
//				.antMatchers("/people/**").hasRole("Service")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.and()
				.csrf().disable();

	}

	@Resource(name="customUserService")
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

}

