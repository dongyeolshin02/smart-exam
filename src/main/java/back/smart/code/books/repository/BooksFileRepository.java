package back.smart.code.books.repository;

import back.smart.code.books.entity.BooksFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BooksFileRepository extends JpaRepository<BooksFileEntity, Integer>,
                                            JpaSpecificationExecutor<BooksFileEntity> {
}
