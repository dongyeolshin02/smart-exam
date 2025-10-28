package back.smart.code.filter;

import back.smart.code.common.utils.JWTUtils;
import back.smart.code.security.dto.SecureUserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginCustomFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    //유효시간
    private final static  long ACCESS_TOKEN_EXPIRE_TIME = 86400L;
    private final static  long REFRESH_TOKEN_EXPIRE_TIME = 86400L * 2;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //아이디 패스워드 꺼내기
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        //username 과 password 를 가지는 토큰 객체 생성
        UsernamePasswordAuthenticationToken authRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(username, password);

        //인증 수행(UserDetailService -> 인증절차 --> 성공하면 Authentication 객체 반환 )
        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        //보안 인증객체에서 사용자 정보 가져오기
        SecureUserDTO user = (SecureUserDTO) authResult.getPrincipal();
        String userId = user.getUserId();
        String userName = user.getUserName();
        //권한
        Iterator<? extends GrantedAuthority> iter = user.getAuthorities().iterator();
        String userRole = iter.next().getAuthority().replaceAll("ROLE_", "");

        //토큰 생성
        String token = jwtUtils.createToken("access", userId, userName, userRole, ACCESS_TOKEN_EXPIRE_TIME);
        String refreshToken = jwtUtils.createToken("refresh", userId, userName, userRole, REFRESH_TOKEN_EXPIRE_TIME);

        response.addCookie(createCookie("refresh", refreshToken));
        response.setStatus(HttpServletResponse.SC_OK);

        JSONObject jObj = new JSONObject();
        jObj.put("resultMsg", "OK");
        jObj.put("status", HttpServletResponse.SC_OK);

        JSONObject data = new JSONObject();
        data.put("userId", userId);
        data.put("userName", userName);
        data.put("userRole", userRole);
        data.put("token", token);

        jObj.put("content", data);

        //클라이언트에 전송
        response.setContentType("application/json");
        response.getWriter().write(jObj.toString());

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        JSONObject jObj = new JSONObject();
        jObj.put("resultMsg", "FAIL");
        jObj.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        jObj.put("content","{}");

        //클라이언트에 전송
        response.setContentType("application/json");
        response.getWriter().write(jObj.toString());
    }


    //토큰 저장
    private Cookie createCookie(String name, Object value) {
        Cookie cookie = new Cookie(name, String.valueOf(value));
        cookie.setPath("/");
        cookie.setMaxAge((int)REFRESH_TOKEN_EXPIRE_TIME);
        cookie.setHttpOnly(true); // 자바스크립트에서 접근 금지

        return cookie;
    }
}
