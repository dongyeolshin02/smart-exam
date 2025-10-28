package back.smart.code.security.service;

import back.smart.code.security.dto.SecureUserDTO;
import back.smart.code.user.entity.UserEntity;
import back.smart.code.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecureUserDetailService  implements  UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity =
                userRepository.findById(username)
                        .orElseThrow(() -> new RuntimeException("사용자 없음"));


        return new SecureUserDTO(entity.getUserId(), entity.getNames(),
                  entity.getPasswd(), entity.getRole().getRoleId()) ;
    }
}
