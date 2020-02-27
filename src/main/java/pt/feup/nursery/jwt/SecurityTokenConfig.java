package pt.feup.nursery.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                /*
                // make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                // Add a filter to validate the tokens with every request
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
                // authorization requests config
                .authorizeRequests()
                .antMatchers("/",
                        "/v2/api-docs",
                        "/configuration/**",
                        "/webjars/*", // used by swagger
                        "/swagger-*", // used by swagger
                        "auth/login" ).permitAll()
                // allow all who are accessing "auth" service
                .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/nurseries",
                        "/patients", "/medicines").hasRole("nursery.api")
                .and()
                .authorizeRequests()
                // must be an admin if trying to access admin area (authentication is also required here)
                //.antMatchers( "/admin/**").hasRole("ADMIN")
                // don't need any authentication for these
                // Any other request must be authenticated*/
                .authorizeRequests()
                .antMatchers("/").permitAll();
                //.anyRequest().authenticated();
    }
    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources",
                "/configuration/**",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
