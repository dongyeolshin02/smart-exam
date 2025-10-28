package back.smart.code.filter;

import back.smart.code.common.utils.JWTUtils;
import back.smart.code.security.dto.SecureUserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //요청 request header 에서 token 찾기
        //헤더에 원래 있는 속성임....
        String acessToken = request.getHeader("Authorization");

        if(acessToken == null) {
            log.info("acessToken is null");
            filterChain.doFilter(request, response);
            return; // 자격이 없으니 로그인으로 넘거가라!
        }

        try{

            if(acessToken.startsWith("Bearer ")) {
                acessToken = acessToken.substring(7);
                log.info("acessToken : {}", acessToken);
                if(!jwtUtils.validateToken(acessToken)) {
                    throw new IllegalAccessException("유효하지 않은 토큰입니다");
                }
            }
        }catch (Exception e){
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            JSONObject message = this.getErrorMessage(e.getMessage(),HttpServletResponse.SC_NOT_ACCEPTABLE );
            response.getWriter().write(e.getMessage());
            return;
        }

        //인증 정보 추출
        String userId = jwtUtils.getUserId(acessToken);
        String userName = jwtUtils.getUserName(acessToken);
        String userRole = jwtUtils.gertUserRole(acessToken);

        SecureUserDTO dto = new SecureUserDTO(userId, userName, "", userRole);

        //로그인 객체 만들고
        // 세션에 저장 >> 로그인 인증을 위해
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(dto, "" , dto.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //다음으로 이동
        filterChain.doFilter(request, response);
    }

    private JSONObject  getErrorMessage (String message, int status) {
        JSONObject jObj = new JSONObject();
        jObj.put("resultMsg", message == null ? "Invalid Token" : message);
        jObj.put("status", status);
        return jObj;
    }
}
