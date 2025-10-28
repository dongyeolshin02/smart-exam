package back.smart.code.books.dto;

import back.smart.code.books.entity.BooksEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
        private List<BooksFileDTO.Response> bookfileList;

        public static Response of(BooksEntity booksEntity){
            List<BooksFileDTO.Response> bookfileList =
                    booksEntity.getTbBooksFiles().stream().map(BooksFileDTO.Response::of).toList();

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
                    .bookfileList(bookfileList)
                    .build();
        }
    }


    @Data
    public static class Request {
        private String bkCode;
        private String bkName;
        private String descriptions;
        private String contents;
        private Integer price;
        private Integer stock;
        private String types;
        private MultipartFile file;

        public static BooksEntity to (Request request) {

            BooksEntity booksEntity = new BooksEntity();

            //bkCode가 있을때만 추가
            if(request.getBkCode() != null) booksEntity.setBkCode(request.getBkCode());

            booksEntity.setBkName(request.getBkName());
            booksEntity.setDescriptions(request.getDescriptions());
            booksEntity.setContents(request.getContents());
            booksEntity.setPrice(request.getPrice());
            booksEntity.setStock(request.getStock());
            booksEntity.setTypes(request.getTypes());

            return booksEntity;
        }
    }

}