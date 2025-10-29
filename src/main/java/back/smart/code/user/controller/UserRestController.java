package back.smart.code.user.controller;

import back.smart.code.common.dto.ApiResponse;
import back.smart.code.user.dto.UserDTO;
import back.smart.code.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserRestController {

    private final UserService userService;

    @PostMapping(value = "/user",
            produces = "application/json", consumes = "application/json")
    public ResponseEntity<ApiResponse<?>> registerUser (
            @RequestBody @Valid UserDTO.Register registerDTO) throws Exception{
        log.info("회원가입 요청: userId={}", registerDTO.getUserId());
        return ResponseEntity.ok(userService.registerUser(registerDTO));
    }

    @GetMapping("/check-id/{userId}")
    public ResponseEntity<ApiResponse<String>> checkUserId(@PathVariable String userId) {
        log.info("아이디 중복 체크: userId={}", userId);
        boolean exists = userService.checkUserIdExists(userId);
        String result = exists ? "yes" : "no";
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<ApiResponse<String>> checkEmail(
            @PathVariable String email) {
        log.info("이메일 중복 체크: email={}", email);
        boolean exists = userService.checkEmailExists(email);
        String result = exists ? "yes" : "no";
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<?>> getUserList(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        log.info("사용자 리스트 조회 - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(userService.getUserList(pageable));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<?>> getUser(@PathVariable("userId") String userId) throws Exception {
        return ResponseEntity.ok().body(ApiResponse.ok(userService.getUser(userId)));
    }
}
