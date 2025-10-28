package back.smart.code.books.dto;

import back.smart.code.books.entity.BooksEntity;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link back.smart.code.books.entity.BooksEntity}
 */
@Value
public class BooksEntityDTO implements Serializable {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static  class Response {
       private  String bkCode;
       private String bkName;
       private String descriptions;
       private  String contents;
       private Integer price;
       private Integer stock;
       private String types;
       private List<BooksFileDTO.Response> bookfileList;


        public static Response of(BooksEntity booksEntity){
            List<BooksFileDTO.Response> bookfileList =
                    booksEntity.getTbBooksFiles().stream().map(BooksFileDTO.Response::of).toList();
            return Response
                    .builder()
                    .bkCode(booksEntity.getBkCode())
                    .bkName(booksEntity.getBkName())
                    .descriptions(booksEntity.getDescriptions())
                    .contents(booksEntity.getContents())
                    .price(booksEntity.getPrice())
                    .stock(booksEntity.getStock())
                    .types(booksEntity.getTypes())
                    .bookfileList(bookfileList)
                    .build();
        }
    }
}