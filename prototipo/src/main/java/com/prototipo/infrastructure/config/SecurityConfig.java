package com.prototipo.infrastructure.config;

import com.prototipo.infrastructure.persistence.db.adapter.CredencialImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(http -> http.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Usar sesiones HTTP
                )
                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers(
                            "/", // bueno esto es para dejarle al spring le de el control al index de angular
                            "/favicon.ico", // Tooodos estos son archivos generados por angular, cada uno de estos de abajo
                            "/index.html",
                            "main-QC5HBKA6.js",
                            "/polyfills-FFHMD2TL.js",
                            "/styles-ZIUGYFFV.css"
                    ).permitAll();
                    // Configurar los endpoints públicos
                    auth.requestMatchers(HttpMethod.POST, "/autenticacion/iniciarSesion").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/autenticacion/cerrarSesion").permitAll();

                    // Restringir acceso por rol correcto
                    auth.requestMatchers("/administrador/**").hasAuthority("Administrador");
                    auth.requestMatchers("/solicitante/**").hasAuthority("Solicitante");
                    auth.requestMatchers("/responsable/**").hasAuthority("Responsable");

                    auth.anyRequest().authenticated();
                    //auth.anyRequest().permitAll();
                })
                .logout(logout -> logout.logoutUrl("/autenticacion/cerrarSesion").invalidateHttpSession(true).deleteCookies("JSESSIONID"))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CredencialImpl credencial) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(this.passwordEncoder());
        provider.setUserDetailsService(credencial);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
}
