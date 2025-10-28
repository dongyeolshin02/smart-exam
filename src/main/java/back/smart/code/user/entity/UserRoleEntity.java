package back.smart.code.user.entity;

import back.smart.code.common.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_user_role")
public class UserRoleEntity  extends BaseTimeEntity {
    @Id
    private String roleId;
    private String roleName;

}