package back.smart.code.user.entity;

import back.smart.code.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class UserEntity  extends BaseTimeEntity {
    @Id
    private String userId;

    private String passwd;

    private String names;

    private String birth;

    private String gender;

    private String phone;

    private String email;

    private String addr;

    private String addrDetail;

    @ManyToOne
    @JoinColumn(name = "roles")
    private UserRoleEntity role;
}