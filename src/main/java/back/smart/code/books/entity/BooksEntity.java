package back.smart.code.books.entity;

import back.smart.code.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_books")
public class BooksEntity extends BaseTimeEntity {
    @Id
    private String bkCode;

    private String bkName;

    private String descriptions;

    private String contents;

    private Integer price;

    private Integer stock;

    private String types;


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BooksFileEntity> tbBooksFiles = new LinkedHashSet<>();


    public void addFiles(BooksFileEntity entity) {
        if(tbBooksFiles == null) this.tbBooksFiles = new HashSet<>();
        entity.setBook(this);
        tbBooksFiles.add(entity);
    }
}










