package com.gateway.api.config

import com.gateway.api.config.handler.JWTAuthFailureHandler
import com.gateway.api.config.handler.JWTAuthSuccessHandler
import com.gateway.api.config.jwt.JWTConverter
import com.gateway.api.config.jwt.JWTTokenWebFilter
import com.gateway.api.service.ExternalUserDetailsService
import com.gateway.api.service.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.codec.json.AbstractJackson2Decoder
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfiguration : WebFluxConfigurer {

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun configureSecurity(
        http: ServerHttpSecurity,
        jwtAuthenticationFilter: AuthenticationWebFilter,
        jwtService: JwtService
    ): SecurityWebFilterChain {
        http.cors {
            it.configurationSource(corsConfiguration())
        }
        return http.authorizeExchange { exchange ->
            exchange
                .pathMatchers(*EXCLUDED_PATH).permitAll()
                .anyExchange().authenticated()
        }.csrf { csrf -> csrf.disable() }
            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .addFilterAt(JWTTokenWebFilter(jwtService), SecurityWebFiltersOrder.AUTHORIZATION)
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .build()
    }

    @Bean
    fun authenticationWebFilter(
        reactiveAuthenticationManager: ReactiveAuthenticationManager,
        jwtConverter: JWTConverter,
        successHandler: JWTAuthSuccessHandler,
        failureHandler: JWTAuthFailureHandler
    ): AuthenticationWebFilter = AuthenticationWebFilter(reactiveAuthenticationManager).apply {
        setRequiresAuthenticationMatcher {
            ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login").matches(it)
        }

        setServerAuthenticationConverter(jwtConverter)

        setAuthenticationSuccessHandler(successHandler)
        setAuthenticationFailureHandler(failureHandler)

        setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance())
    }

    @Bean
    fun jacksonDecoder(): AbstractJackson2Decoder = Jackson2JsonDecoder()

    @Bean
    fun reactiveAuthenticationManager(
        reactiveUserDetailsService: ExternalUserDetailsService,
        passwordEncoder: PasswordEncoder
    ): ReactiveAuthenticationManager =
        UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService).apply {
            setPasswordEncoder(passwordEncoder)
        }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
    }

    @Bean
    fun corsConfiguration(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowCredentials = true
        configuration.allowedOrigins = listOf("http://localhost:3000")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("Access-Control-Allow-Origin",
            "Authorization", "Content-Type")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun corsWebFilter(source: CorsConfigurationSource): CorsWebFilter =
        CorsWebFilter(source)


    companion object {
        val EXCLUDED_PATH = arrayOf("/auth/refresh", "/", "/static/**", "/favicon.ico")
    }
}