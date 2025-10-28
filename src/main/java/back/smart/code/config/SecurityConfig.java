package back.smart.code.config;

import back.smart.code.common.utils.JWTUtils;
import back.smart.code.security.service.SecureUserDetailService;
import org.apache.tomcat.util.net.DispatchType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecureUserDetailService serviceDetails;
    private final JWTUtils jwtUtils;

    //시큐리티 우선 무시하기
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web ->  
            web.ignoring()
                    .requestMatchers("/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());         
    }


//      @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        AuthenticationConfiguration configuration
//            = http.getSharedObject(AuthenticationConfiguration.class);
//        //loginFilter 에서 인증처리하기 위한 매니저 생성
//        AuthenticationManager manager = this.authenticationManager(configuration);
//
//        LoginCustomFilter loginFilter = new LoginCustomFilter(manager, jwtUtils);
//        loginFilter.setFilterProcessesUrl("/api/v1/login");
//
//        http.csrf(AbstractHttpConfigurer::disable)
//             .cors( cors -> cors.configurationSource(this.configurationSource()))
//            .httpBasic(AbstractHttpConfigurer::disable)
//            .formLogin(AbstractHttpConfigurer::disable)  //세션로그인할 때 필요
//            .authorizeHttpRequests(
//                auth ->
//                    auth.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                        .requestMatchers("/api/v1/login/**").permitAll()
//                        .requestMatchers("/api/v1/logout/**").permitAll()
//                        .requestMatchers("/api/v1/refresh").permitAll()
//                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//
//            //LoginFilter  전에 JWTFilter 를 실행
//            ).addFilterBefore(new JWTFilter(jwtUtils), LoginCustomFilter.class)
//            //UsernamePasswordAuthenticationFilter 이거 대신 LoginFilter를 실행해라
//            .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
//            .addFilterBefore(new CustomLogoutFilter(jwtUtils), LogoutFilter.class)
//            //세션 유지 않함
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .logout(logout ->
//                    logout.logoutRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/api/v1/logout")));
//
//            return http.build();
//    }



    @Bean
    public AuthenticationProvider authProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(serviceDetails);
        provider.setPasswordEncoder(bcyPasswordEncoder());
        return provider;
    }

    //패스워드 암호화 객체 설정
    @Bean
    public PasswordEncoder bcyPasswordEncoder(){
        // 단방향 암호화 방식.  복호화 없음.  값 비교는 가능  
        return  new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(
          AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource configurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        //헤더 설정
         config.setAllowedHeaders(List.of(
        "Authorization", "Content-Type",
         "X-Requested-With", "Accept", 
         "Origin", 
         "Access-Control-Request-Method", 
         "Access-Control-Request-Headers"
    ));

       List<String> ipAddr = new ArrayList<>();

       //클라이언트 적용 포트 만들기
       for(int i = 0; i < 5; i++){
           ipAddr.add("http://localhost:300" + i);
           ipAddr.add("http://localhost:400" + i);
       }

       //메서드 설정
       config.setAllowedMethods(List.of("GET",  "POST", "DELETE", "PUT", "PATCH",  "OPTIONS"));
       config.setAllowedOrigins(ipAddr);
       config.setAllowCredentials(true);
       config.setMaxAge(3600L);

       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", config);

       return source;
    }
}
