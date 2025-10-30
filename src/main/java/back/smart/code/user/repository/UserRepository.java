package back.smart.code.user.repository;

import back.smart.code.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
