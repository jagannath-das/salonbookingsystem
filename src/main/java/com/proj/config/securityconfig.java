////package com.proj.config;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.http.SessionCreationPolicy;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////
////import com.proj.jwt.JwtFilter;
////
////@EnableWebSecurity//enable spring security
////@Configuration
////public class securityconfig {
////	
////private JwtFilter Jwtfilter;
////
////    public securityconfig(JwtFilter jwtfilter) {
////	super();
////	this.Jwtfilter = jwtfilter;
////}
////    @Bean
////    public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception
////    {
////    		
////    http
////        .csrf(csrf->csrf.disable())// Disable CSRF (for REST API)
////         .authorizeHttpRequests(auth->
////                                    auth.requestMatchers("/auth/**")
////                                    .permitAll()
////                                    
////                                        //user api
////                                     .requestMatchers("/user/**").hasRole("USER")
////                                     
////                                     .requestMatchers(
////                                    	        "/swagger-ui/**",
////                                    	        "/v3/api-docs/**"
////                                    	    ).permitAll()
////
////                                     
////                                     //salon api
////                                     .requestMatchers("/salon/**").hasRole("SALON")
////                                     
////                                     .requestMatchers("/admin/**").hasRole("ADMIN")
////        		  
////                                     // Any other API requires login
////                                     .anyRequest().authenticated() )
////         
////      // No Session (JWT based auth)
////         .sessionManagement(session ->
////             session.sessionCreationPolicy(
////                     SessionCreationPolicy.STATELESS
////             )
////         );
////    
////    
////    
////   
////
////        
////    // Add JWT Filter before Spring login filter
////    http.addFilterBefore(
////            Jwtfilter,
////            UsernamePasswordAuthenticationFilter.class
////    );
////
////    return http.build();
////}
////    	
////    	
////    // ===============================
////    // AUTHENTICATION MANAGER
////    // ===============================
////    @Bean
////    public AuthenticationManager authenticationManager(
////            AuthenticationConfiguration config) throws Exception {
////
////        return config.getAuthenticationManager();
////    }
////  
////	@Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////}
//
//package com.proj.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.proj.jwt.JwtAuthenticationEntryPoint;
//import com.proj.jwt.JwtFilter;
//
//@Configuration
//@EnableWebSecurity
//public class securityconfig {
//
//    private final JwtFilter jwtFilter;
//    private final JwtAuthenticationEntryPoint entryPoint;
//
//    // ✅ Constructor Injection
//    public securityconfig(JwtFilter jwtFilter,
//                          JwtAuthenticationEntryPoint entryPoint) {
//        this.jwtFilter = jwtFilter;
//        this.entryPoint = entryPoint;
//    }
//
//    // ===============================
//    // SECURITY FILTER CHAIN
//    // ===============================
//    @Bean
//    public SecurityFilterChain securityfilterchain(HttpSecurity http)
//            throws Exception {
//
//        http
//            // Disable CSRF (REST API)
//            .csrf(csrf -> csrf.disable())
//
//            // Authorization Rules
//            .authorizeHttpRequests(auth -> auth
//
//                    // PUBLIC APIs
//                    .requestMatchers("/auth/**").permitAll()
//
//                    // USER APIs
//                    .requestMatchers("/user/**")
//                    .hasAuthority("ROLE_USER")
//
//                    // SALON APIs
//                    .requestMatchers("/salon/**")
//                    .hasAuthority("ROLE_SALON")
//
//                    // ADMIN APIs
//                    .requestMatchers("/error").permitAll() 
//                    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//
//                    // Swagger
//                    .requestMatchers(
//                            "/swagger-ui/**",
//                            "/v3/api-docs/**"
//                    ).permitAll()
//
//                    // Any other request
//                    .anyRequest().authenticated()
//            )
//
//            // ✅ HANDLE UNAUTHORIZED ACCESS
//            .exceptionHandling(ex ->
//                    ex.authenticationEntryPoint(entryPoint)
//            )
//
//            // JWT = Stateless Session
//            .sessionManagement(session ->
//                    session.sessionCreationPolicy(
//                            SessionCreationPolicy.STATELESS
//                    )
//            );
//
//        // ✅ ADD JWT FILTER
//        http.addFilterBefore(
//                jwtFilter,
//                UsernamePasswordAuthenticationFilter.class
//        );
//
//        return http.build();
//    }
//
//    // ===============================
//    // AUTHENTICATION MANAGER
//    // ===============================
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration config)
//            throws Exception {
//
//        return config.getAuthenticationManager();
//    }
//
//    // ===============================
//    // PASSWORD ENCODER
//    // ===============================
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

package com.proj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.proj.jwt.JwtAuthenticationEntryPoint;
import com.proj.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class securityconfig {

    private final JwtFilter jwtFilter;
    private final JwtAuthenticationEntryPoint entryPoint;

    public securityconfig(
            JwtFilter jwtFilter,
            JwtAuthenticationEntryPoint entryPoint) {

        this.jwtFilter = jwtFilter;
        this.entryPoint = entryPoint;
    }

    // ===============================
    // SECURITY FILTER CHAIN
    // ===============================
    @Bean
    public SecurityFilterChain securityfilterchain(HttpSecurity http)
            throws Exception {

        http

        .cors(cors -> {})  
            // ✅ Disable CSRF for JWT
            .csrf(csrf -> csrf.disable())

            // ✅ Authorization Rules
            .authorizeHttpRequests(auth -> auth
            		
            		 .requestMatchers(
            		            org.springframework.http.HttpMethod.OPTIONS, "/**"
            		        ).permitAll()

                    // ---------- PUBLIC ----------
                    .requestMatchers(
                            "/",
                            "/index.html",
                            "/login.html",
                            "/register.html",
                            "/user_dashboard.html",
                            "/update_user.html",
                            "/get_user.html",
                            "/delete_user.html",
                            "/delete_appointment.html",
                            "/book_appointment.html",
                            "/appointments.html",
                            "/salon_dashboard.html",
                            "/admin_dashboard.html",
                            "/reset.html",
                            "/auth/**",
                            "/forgot-password",
                            "/verify-otp",
                            "/reset-password",
                            "/css/**",
                            "/js/**",
                            "/images/**",
                            "/uploads/**",
                            "/h2-console/**",
                            "/error",
                            "/favicon.ico"
                    ).permitAll()

                    // ---------- ROLES ----------
                    .requestMatchers("/user/**")
                    .hasAuthority("ROLE_USER")

                    .requestMatchers(
                            org.springframework.http.HttpMethod.GET,
                            "/salon/findsalon/**",
                            "/salon/getsalonservices/salonid/**"
                    ).hasAnyAuthority("ROLE_USER", "ROLE_SALON", "ROLE_ADMIN")

                    .requestMatchers("/salon/**")
                    .hasAuthority("ROLE_SALON")

                    .requestMatchers("/admin/**")
                    .hasAuthority("ROLE_ADMIN")

                    // ---------- SWAGGER ----------
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    ).permitAll()

                    // ---------- OTHERS ----------
                    .anyRequest().authenticated()
            )

            // ✅ Handle Unauthorized Access
            .exceptionHandling(ex ->
                    ex.authenticationEntryPoint(entryPoint)
            )

            // ✅ JWT = Stateless
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            );

        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        // ✅ Add JWT Filter
        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    // ===============================
    // AUTHENTICATION MANAGER
    // ===============================
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

    // ===============================
    // PASSWORD ENCODER
    // ===============================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {

        org.springframework.web.cors.CorsConfiguration config =
                new org.springframework.web.cors.CorsConfiguration();

        config.setAllowCredentials(false);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
                new org.springframework.web.cors.UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
