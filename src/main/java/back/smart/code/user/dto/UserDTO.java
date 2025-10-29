package back.smart.code.user.dto;

import back.smart.code.user.entity.UserEntity;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

public class UserDTO {
    @Data
    public static class Register {
        @NotBlank(message = "아이디를 입력하십시오")
        private String userId;
        @NotBlank(message = "비밀번호를 입력하십시오")
        private String password;
        private String names;
        @NotBlank(message = "이메일을 입력하십시오")
        private String email;
        @NotBlank(message = "전화번호를 입력하십시오")
        private String phone;
        @NotBlank(message = "생년월일을 입력하십시오")
        private String birth;
        @NotBlank(message = "성별을 선택하십시오")
        private String gender;
        private String addr;
        private String addrDetail;
        private String roleId;
    }

   @Builder
   @Getter
   @NoArgsConstructor(access = AccessLevel.PROTECTED)
   @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private String userId;
        private String names;
        private String email;
        private String phone;
        private String birth;
        private String gender;
        private String addr;
        private String addrDetail;
        private String roleId;

        public static Response of(UserEntity entity) {
            return Response.builder()
                    .userId(entity.getUserId())
                    .names(entity.getNames())
                    .email(entity.getEmail())
                    .phone(entity.getPhone())
                    .birth(entity.getBirth())
                    .gender(entity.getGender())
                    .addr(entity.getAddr())
                    .addrDetail(entity.getAddrDetail())
                    .roleId(entity.getRole() != null ? entity.getRole().getRoleId() : null)
                    .build();
        }
    }

    @Data
    public static class PageResponse {
        private int page;
        private long total;
        private List<Response> data;

        public PageResponse(int page, long total, List<Response> data) {
            this.page = page;
            this.total = total;
            this.data = data;
        }
    }
}
