package com.ssafy.b105.config;

<<<<<<< HEAD
import com.ssafy.b105.config.oauth.PrincipalOauth2UserService;
import com.ssafy.b105.security.common.FilterSkipMatcher;
import com.ssafy.b105.security.filter.FormLoginFilter;
import com.ssafy.b105.security.filter.JwtAuthenticationFilter;
import com.ssafy.b105.security.handler.FormLoginAuthenticationFailureHandler;
import com.ssafy.b105.security.handler.FormLoginAuthenticationSuccessHandler;
import com.ssafy.b105.security.jwt.HeaderTokenExtractor;
import com.ssafy.b105.security.provider.FormLoginAuthenticationProvider;
import com.ssafy.b105.security.provider.JWTAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;
=======
import com.ssafy.b105.auth.Jwt.JwtAcessDeniedHandler;
import com.ssafy.b105.auth.JwtAuthenticationEntryPoint;
import com.ssafy.b105.auth.oauth.OAuth2SuccessHandler;
import com.ssafy.b105.auth.oauth.OAuth2UerService;
import com.ssafy.b105.auth.Jwt.domain.Jwt;
import com.ssafy.b105.auth.Jwt.JwtFilter;
import com.ssafy.b105.auth.Jwt.domain.JwtTokenConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsUtils;

>>>>>>> dev

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

<<<<<<< HEAD
    private final FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
    private final FormLoginAuthenticationFailureHandler formLoginAuthenticationFailureHandler;
    private final PrincipalOauth2UserService principalOauth2UserService;

    private final FormLoginAuthenticationProvider provider;
    private final JWTAuthenticationProvider jwtProvider;
    private final HeaderTokenExtractor headerTokenExtractor;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    // 1.
    protected FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter filter = new FormLoginFilter(
                new AntPathRequestMatcher("/api/auth/login/**", HttpMethod.POST.name()),
                formLoginAuthenticationSuccessHandler,
                formLoginAuthenticationFailureHandler
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    // 2.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(this.provider)
                .authenticationProvider(this.jwtProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http    .httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .disable();

        http
                .headers()
                .frameOptions()
                .disable();

        // ???????????? ????????? JWT??? ???????????? ????????? Session??? ????????? ????????????.
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /*
         * 1.
         * UsernamePasswordAuthenticationFilter ????????? FormLoginFilter, JwtFilter ??? ???????????????.
         * FormLoginFilter : ????????? ????????? ???????????????.
         * JwtFilter       : ????????? ????????? JWT ?????? ??? ????????? ???????????????.
         */
        http
                .addFilterBefore(
                        formLoginFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        jwtFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        // api/auth ?????? ?????? ??????
        //preflightRequest ?????? ???????????? ????????? api ??? ?????? ?????? ????????? ????????? permitAll
        http
                .authorizeRequests()
                .requestMatchers(request -> CorsUtils.isPreFlightRequest(request)).permitAll()
                .mvcMatchers(
                        "/api/auth/**"
                )
                .permitAll()
                .mvcMatchers(
                        "/api/auth/signup/shelter"
                )
                .hasRole("ADMIN")
                .anyRequest().authenticated() //?????? ?????? ????????? ??????????????????
          .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
    }

    private JwtAuthenticationFilter jwtFilter() throws Exception {
        List<AntPathRequestMatcher> skipPath = new ArrayList<>();

        // Static ?????? ?????? ??????
        skipPath.add(new AntPathRequestMatcher("/error", HttpMethod.GET.name()));
        skipPath.add(new AntPathRequestMatcher("/favicon.ico", HttpMethod.GET.name()));
        skipPath.add(new AntPathRequestMatcher("/static", HttpMethod.GET.name()));
        skipPath.add(new AntPathRequestMatcher("/static/**", HttpMethod.GET.name()));

        skipPath.add(new AntPathRequestMatcher("/api/auth/**", HttpMethod.POST.name()));
        skipPath.add(new AntPathRequestMatcher("/api/auth/login/**", HttpMethod.POST.name()));
        skipPath.add(new AntPathRequestMatcher("/api/auth/duplicate/**", HttpMethod.GET.name()));


        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPath,
                "/**"
        );

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
                matcher,
                headerTokenExtractor
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }
=======

  private final Jwt jwt;
  private final JwtTokenConfig jwtTokenConfig;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  private final OAuth2UerService oAuth2UerService;
  private final JwtAcessDeniedHandler jwtAcessDeniedHandler;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


  @Bean
  public JwtFilter jwtFilter() {
    return new JwtFilter(jwt, jwtTokenConfig.getHeader());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
      .ignoring()
      .antMatchers(
        "/h2-console/**",
        "/favicon/ico",
        "/static/**"
      );
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.httpBasic().disable()
      .csrf()
      .disable();

    http
      .headers()
      .frameOptions()
      .disable();

    // ???????????? ????????? JWT??? ???????????? ????????? Session??? ????????? ????????????.
    http
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http
      .exceptionHandling()  //?????? ????????? ??????
      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
      .accessDeniedHandler(jwtAcessDeniedHandler);

    http
      .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

    http
      .authorizeRequests()
      .requestMatchers(request -> CorsUtils.isPreFlightRequest(request)).permitAll()
      .mvcMatchers("/api/auth/**")
      .permitAll()

      .and()

      .authorizeRequests()
      .mvcMatchers("/api/campaign/**")
      .permitAll()

      .and()
      .formLogin().disable()

      .oauth2Login()
      .userInfoEndpoint()
      .userService(oAuth2UerService)
      .and()
      .successHandler(oAuth2SuccessHandler);
  }
>>>>>>> dev
}