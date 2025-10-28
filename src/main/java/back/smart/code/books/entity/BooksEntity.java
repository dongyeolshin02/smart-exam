package back.smart.code.books.entity;

import back.smart.code.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_books")
public class BooksEntity extends BaseTimeEntity {
    @Id
    @Size(max = 100)
    @Column(name = "bk_code", nullable = false, length = 100)
    private String bkCode;

    @Size(max = 255)
    @NotNull
    @Column(name = "bk_name", nullable = false)
    private String bkName;

    @ColumnDefault("''")
    @Column(name = "descriptions")
    private String descriptions;

    @ColumnDefault("''")
    @Column(name = "contents")
    private String contents;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @ColumnDefault("0")
    @Column(name = "stock")
    private Integer stock;

    @Size(max = 30)
    @ColumnDefault("'N'")
    @Column(name = "types", length = 30)
    private String types;


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BooksFileEntity> tbBooksFiles = new LinkedHashSet<>();

}










