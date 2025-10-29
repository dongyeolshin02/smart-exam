package back.smart.code.user.service;

import back.smart.code.common.dto.ApiResponse;
import back.smart.code.user.dto.UserDTO;
import back.smart.code.user.entity.UserEntity;
import back.smart.code.user.entity.UserRoleEntity;
import back.smart.code.user.repository.UserRepository;
import back.smart.code.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ApiResponse<?> registerUser(UserDTO.Register registerDTO)  throws Exception {
        // 아이디 중복 체크
        if (userRepository.existsByUserId(registerDTO.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 이메일 중복 체크
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 역할 조회
        UserRoleEntity role = userRoleRepository.findById(registerDTO.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 역할입니다."));

        // 사용자 엔티티 생성
        UserEntity user = new UserEntity();
        user.setUserId(registerDTO.getUserId());
        user.setPasswd(passwordEncoder.encode(registerDTO.getPassword()));
        user.setNames(registerDTO.getNames());
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setBirth(registerDTO.getBirth());
        user.setGender(registerDTO.getGender());
        user.setAddr(registerDTO.getAddr());
        user.setAddrDetail(registerDTO.getAddrDetail());
        user.setRole(role);

        userRepository.save(user);

        return ApiResponse.ok("가입성공");
    }

    @Transactional(readOnly = true)
    public boolean checkUserIdExists(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public boolean checkEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public ApiResponse<?> getUserList(Pageable pageable) {
        Page<UserEntity> userPage = userRepository.findAll(pageable);

        List<UserDTO.Response> data = userPage.getContent()
                .stream()
                .map(UserDTO.Response::of)
                .toList();

        UserDTO.PageResponse pageResponse = new UserDTO.PageResponse(
                userPage.getNumber(),
                userPage.getTotalElements(),
                data
        );

        return ApiResponse.ok(pageResponse);
    }

    public ApiResponse<?> getUser(String userId) throws Exception{
        UserEntity entity =  userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        UserDTO.Response  user = UserDTO.Response.of(entity);
        return ApiResponse.ok(user);
    }

}
