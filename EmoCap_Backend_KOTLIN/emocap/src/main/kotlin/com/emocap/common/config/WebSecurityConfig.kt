package com.emocap.common.config

import com.emocap.common.filter.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class WebSecurityConfig(
    private val jwtFilter: JwtFilter,
    private val introspector: HandlerMappingIntrospector,
) {
    val mvcMatcherBuilder = MvcRequestMatcher.Builder(introspector)
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeHttpRequests { authorize ->
            authorize
                //.requestMatchers(mvcMatcherBuilder.pattern("/friendships/**")).hasRole("USER")
                //.requestMatchers(mvcMatcherBuilder.pattern("/users")).hasRole("ADMIN")
                .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui.html"), mvcMatcherBuilder.pattern("/swagger-ui/**"), mvcMatcherBuilder.pattern("/v2/api-docs"),mvcMatcherBuilder.pattern( "/webjars/**")).permitAll()
                .anyRequest().permitAll()
        }
            .addFilterBefore(jwtFilter, AuthorizationFilter::class.java)
            //.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}