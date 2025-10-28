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
@Table(name = "tb_books_img")
public class BooksImgEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id", nullable = false)
    private Integer id;


    @Size(max = 255)
    @NotNull
    @Column(name = "img_name", nullable = false)
    private String imgName;

    @Size(max = 255)
    @NotNull
    @Column(name = "img_path", nullable = false)
    private String imgPath;

    @Size(max = 255)
    @NotNull
    @Column(name = "img_thumb_name", nullable = false)
    private String imgThumbName;

    @NotNull
    @Column(name = "img_order", nullable = false)
    private Integer imgOrder;



}