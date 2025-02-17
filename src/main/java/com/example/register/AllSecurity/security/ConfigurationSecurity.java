package com.example.register.AllSecurity.security;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration

@EnableWebSecurity
@AllArgsConstructor
public class ConfigurationSecurity {



    /*La configuration SessionCreationPolicy.STATELESS dans Spring Security est utilisée pour les applications RESTful ou microservices. Elle désactive la gestion des sessions côté serveur, ce qui oblige chaque requête à inclure les informations d'authentification (comme les jetons JWT). Cela convient aux systèmes sans état, où chaque requête est indépendante.


            sessionManagement :Cette méthode configure les paramètres liés à la gestion des sessions dans Spring Security. Elle permet de définir comment les sessions doivent être gérées dans l'application
     */

    @Autowired
    private  final JwtFilter jwtFilter;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//
//
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable) //Désactive la protection CSRF (Cross-Site Request Forgery)
//                .authorizeHttpRequests(
//
//                        authorize->
//                                authorize
//                                        .requestMatchers("/inscription").permitAll() //pas besoin d etre conncter /inscription
//                                        .requestMatchers("/activation").permitAll()
//                                        .requestMatchers("/login").permitAll()
//                                         .requestMatchers("/hello").hasRole("Administrateur")
//                                        .requestMatchers("/deconnection").permitAll()
//                                        .requestMatchers("/UpdatePassword").permitAll()
//                                        .requestMatchers("/newpassword").permitAll()
////                                        .requestMatchers("/item/add/{cartId}").permitAll()
//                                        .requestMatchers("/item/add", "/item/add/**").permitAll()
//                                        .requestMatchers("/delete/{cartId}").permitAll()
//                                        .requestMatchers("/update/{cartId}").permitAll()
//                                        .requestMatchers("/product/getbyid/{id}").permitAll()
//                                        .requestMatchers("/product/getbyuser/{id}").permitAll()
//                                        .requestMatchers("/product/delet/{id}").permitAll()
//                                        .requestMatchers("/product/add").authenticated()
//                                        .requestMatchers("/product/update").permitAll()
//                                        .requestMatchers("/product/update/{productId}").permitAll()
//                                        .requestMatchers("/Item/{cartId}").permitAll()
//                                        .requestMatchers("/{cartId}/my-cart").permitAll()
//                                        .requestMatchers("/{cartId}/delet").permitAll()
//                                        .requestMatchers("/{cartId}/cart/total-price").permitAll()
//                                        .requestMatchers("/product/all").permitAll()
//                                        .requestMatchers("/product/category/{category}").permitAll()
//
//                                        .requestMatchers("/myOrder/{userId}").permitAll()
//                                        .anyRequest().authenticated()// mais les autre url necessite une authetification
//                )
//                .sessionManagement(httpSecuritySessionManagementConfigurer ->
//                       httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//               .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)   //sernamePasswordAuthenticationFilter est le filtre par défaut dans Spring Security qui gère l'authentification avec un nom d'utilisateur et un mot de passe (souvent sur /login).
//                .build();
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Active la configuration CORS
                .csrf(AbstractHttpConfigurer::disable) // Désactive CSRF
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/inscription").permitAll()
                                .requestMatchers("/activation").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/hello").hasRole("Administrateur")
                                .requestMatchers("/deconnection").permitAll()
                                .requestMatchers("/UpdatePassword").permitAll()
                                .requestMatchers("/newpassword").permitAll()
                                .requestMatchers("/item/add", "/item/add/**").permitAll()
//                                .requestMatchers("/item/delete/{productId}").permitAll()
                                .requestMatchers("/delete/{cartId}").permitAll()
                                .requestMatchers("/update/{cartId}").permitAll()
                                .requestMatchers("/product/getbyid/{id}").permitAll()
                                .requestMatchers("/product/getbyuser/{id}").permitAll()
                                .requestMatchers("/product/delet/{id}").permitAll()
                                .requestMatchers("/product/add").authenticated()
                                .requestMatchers("/product/update").permitAll()
                                .requestMatchers("/product/update/{productId}").permitAll()
                                .requestMatchers("/Item/{cartId}").permitAll()
                                .requestMatchers("/{cartId}/my-cart").permitAll()
                                .requestMatchers("/{cartId}/delet").permitAll()
                                .requestMatchers("/{cartId}/cart/total-price").permitAll()
                                .requestMatchers("/product/all").permitAll()
                                .requestMatchers("/product/category/{category}").permitAll()
                                .requestMatchers("/myOrder/{userId}").permitAll()
                                .requestMatchers("/my-cart").permitAll()
                                .requestMatchers("/Item").permitAll()
                                .requestMatchers("/item/update").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    /*
    * AuthenticationManager : Gère l'authentification en validant les identifiants des utilisateurs et retourne un objet Authentication si l'authentification réussit
    *
    * AuthenticationConfiguration : Fournit l'AuthenticationManager configuré par Spring Security pour permettre son utilisation dans l'application.
    * */


    //on resume AuthenticationManager s occupe de gerer l authetification dans le projet
    @Bean
    public AuthenticationManager authetificationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
    }


    // la question , qui vat chercher l utilisateur (cad, que le mot de passe et login correspond a l utilisateur )?

    //pour cela il ya un service UserDetailService permet de retouner les information sur l utilisateur connceter






    //L'AuthenticationProvider est responsable de valider les informations d'authentification d'un utilisateur et de retourner un objet Authentication si la validation réussit.
    //AuthenticationProvider peut avoir un accès direct à la base de données
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //on fait l appel de la methode qui recupre les donne de l utilisateur
        //daoAuthenticationProvider.setUserDetailsService(this.userDetailsService());

        //je veux utiliser la dependence qui j ai importer
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);


        // cette ligne pas encore comprie ??????
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return daoAuthenticationProvider;

    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Remplace par ton domaine en production
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // C'est ici que le problème est corrigé

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }





}
