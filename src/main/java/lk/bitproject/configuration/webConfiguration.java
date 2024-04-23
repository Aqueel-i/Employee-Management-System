package lk.bitproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class webConfiguration {

    String[] resourcesURL = { "/resources/**", "/controllerjs/**" };

    /*
     * @Bean
     * public WebSecurityCustomizer webSecurityCustomizer() {
     * return (web) -> web.ignoring()
     * // Spring Security should completely ignore URLs starting with /resources/
     * .requestMatchers(resourcesURL);
     * }
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // request filter
                .authorizeHttpRequests((request) -> {
                    request
                            .requestMatchers("/login").permitAll()
                            .requestMatchers("/createadmin").permitAll()
                            .requestMatchers(resourcesURL).permitAll()
                            .requestMatchers("/dashboard")
                            .hasAnyAuthority("Admin", "Manager", "Cashier", "Store-Manager")
                            .requestMatchers("/employee/**").hasAnyAuthority("Admin", "Manager")
                            .requestMatchers("/user/**").hasAnyAuthority("Admin", "Manager")
                            .requestMatchers("/supplier/**")
                            .hasAnyAuthority("Admin", "Manager", "Cashier", "Store-Manager")
                            .requestMatchers("/privilege/**")
                            .hasAnyAuthority("Admin", "Manager", "Cashier", "Store-Manager")
                            .requestMatchers("/item/**").hasAnyAuthority("Admin", "Manager", "Cashier", "Store-Manager")
                            .anyRequest().authenticated();
                })

                // login details
                .formLogin((login) -> {
                    login.loginPage("/login") // return login page
                            .usernameParameter("username") // login username parameter
                            .passwordParameter("password") // login password parameter
                            .defaultSuccessUrl("/dashboard", true)
                            .failureUrl("/login?error=invalidusernamepassword");
                })

                // logout details
                .logout((logout) -> {
                    logout.logoutUrl("/logout").logoutSuccessUrl("/login");
                })

                // csrf details
                .csrf((csrf) -> {
                    csrf.disable();
                })

                // exception handeling
                .exceptionHandling((exception) -> {
                    exception.accessDeniedPage("/errorpage");
                });

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
