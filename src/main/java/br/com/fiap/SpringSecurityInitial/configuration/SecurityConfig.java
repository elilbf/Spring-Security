package br.com.fiap.SpringSecurityInitial.configuration;

import br.com.fiap.SpringSecurityInitial.service.MyUserDetailService;
import br.com.fiap.SpringSecurityInitial.support.PlainTextPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailService myUserDetailService;

    public SecurityConfig(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService; // injeta o serviço de usuários.
    }

    // Configurando o gerenciador de autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // configuração padrão!
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PlainTextPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // filtra os paths e aplica as politicas de segurança
        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests.requestMatchers("/public").permitAll();
            authorizeRequests.requestMatchers("/logout").permitAll();
            authorizeRequests.anyRequest().authenticated();
        });
        http.formLogin(
                Customizer.withDefaults());
        return http.build();
    }
}
