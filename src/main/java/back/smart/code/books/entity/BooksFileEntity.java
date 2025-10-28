package back.smart.code.books.entity;

import back.smart.code.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tb_books_files")
public class BooksFileEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bf_id", nullable = false)
    private Integer bfId;


    @Size(max = 255)
    @NotNull
    @Column(name = "bf_name", nullable = false)
    private String bfName;

    @Size(max = 255)
    @NotNull
    @Column(name = "thumb_name", nullable = false)
    private String thumbName;

    @Size(max = 255)
    @NotNull
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @ColumnDefault("1")
    @Column(name = "orders")
    private Integer orders;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // 해당 옵션은 DB의  외래키 옵션에  on delete cascade 있어야 가능
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "bk_code", nullable = false)
    private BooksEntity book;

}