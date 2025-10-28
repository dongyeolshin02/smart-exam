package back.smart.code.common.dto;

import back.smart.code.common.utils.TimeFortmatUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final String date;
    private final  String resultCode;
    private final  T content;

    private ApiResponse(String resultCode, T content) {
        this.resultCode = resultCode;
        this.content = content;
        this.date = TimeFortmatUtils.getNowTime();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("200", data);
    }

}
