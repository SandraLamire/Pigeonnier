package fr.eni.pigeonnier.security;

import java.io.IOException;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // autorisations
        http.authorizeHttpRequests(auth -> {
            // Ne peuvent pas s'exécuter sans login
            auth.requestMatchers(HttpMethod.POST, "/pigeon/add").hasAnyAuthority("USER");
            auth.requestMatchers(HttpMethod.GET, "/pigeon/add").hasAnyAuthority("USER");
            auth.requestMatchers(HttpMethod.POST, "/pigeon/catch").hasAnyAuthority("USER");
            auth.requestMatchers(HttpMethod.GET, "/pigeon/catch").hasAnyAuthority("USER");
            auth.requestMatchers(HttpMethod.GET, "/pigeon").hasAnyAuthority("USER");

            auth.requestMatchers("/css/*").permitAll()
                    .requestMatchers("/images/*").permitAll()
                    .requestMatchers("/javascript/*").permitAll()
                    .requestMatchers("/error").permitAll()
                    .anyRequest().authenticated();
        });

        // Customiser le formulaire de login
        http.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.defaultSuccessUrl("/pigeon").permitAll();
            form.failureUrl("/login-error");

            // permet de définir ce qu'il se passe lorsque le login est validé
            form.successHandler(new SavedRequestAwareAuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request,
                                                    HttpServletResponse response, Authentication authentication)
                        throws IOException, ServletException {

                    // MET L'UTILISATEUR CONNECTE DANS UNE VARIABLE DE SESSION currentUser
                    if(authentication!=null) {
                        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
                        request.getSession().setAttribute("currentUser", userDetails.getUser());
                    }
                    // Renvoie sur la page que l'on voulait ouvrir avant login
                    super.onAuthenticationSuccess(request, response, authentication);
                }

            });
        });

        // /logout --> vider la session et le contexte de securite
        http.logout(logout -> logout.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll());

        return http.build();

    }

}

