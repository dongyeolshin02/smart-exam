package back.smart.code.books.dto;

import back.smart.code.books.entity.BooksEntity;
import back.smart.code.books.entity.BooksFileEntity;
import back.smart.code.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

public class BooksFileDTO  {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private int bfId;
        private String bkCode;
        private String bfName;
        private String thumbName;
        private String filePath;
        private List<Response> bookfileList;

        public static Response of(BooksFileEntity booksFileEntity){
            return Response.builder()
                    .bfId(booksFileEntity.getBfId())
                    .bkCode(booksFileEntity.getBook().getBkCode())
                    .bfName(booksFileEntity.getBfName())
                    .thumbName(booksFileEntity.getThumbName())
                    .filePath(booksFileEntity.getFilePath())
                    .build();
        }

    }

}