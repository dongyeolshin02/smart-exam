package back.smart.code.books.dto;

import back.smart.code.books.entity.BooksEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link back.smart.code.books.entity.BooksEntity}
 */

public class BooksDTO implements Serializable {

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private String bkCode;
        private String bkName;
        private String descriptions;
        private String contents;
        private Integer price;
        private Integer stock;
        private String types;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt;

        public static Response of(BooksEntity booksEntity){
            return Response.builder()
                    .bkCode(booksEntity.getBkCode())
                    .bkName(booksEntity.getBkName())
                    .descriptions(booksEntity.getDescriptions())
                    .contents(booksEntity.getContents())
                    .price(booksEntity.getPrice())
                    .stock(booksEntity.getStock())
                    .types(booksEntity.getTypes())
                    .createdAt(booksEntity.getCreateAt())
                    .updatedAt(booksEntity.getUpdateAt())
                    .build();
        }
    }

}