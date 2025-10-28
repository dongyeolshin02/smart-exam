package back.smart.code.books.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link back.smart.code.books.entity.BooksEntity}
 */
@Value
public class BooksDTO implements Serializable {
    String codes;
    String title;
    String author;
    String publisher;
    Integer stock;
    Long price;
    String bookIndex;
    String descriptions;
    Character activeYn;
    Instant createdAt;
    Instant updatedAt;
}