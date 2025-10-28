package back.smart.code.user.repository;

import back.smart.code.user.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {

}
