package back.smart.code.books.repository;

import back.smart.code.books.entity.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BooksRepository extends JpaRepository<BooksEntity, String>,
                                            JpaSpecificationExecutor<BooksEntity> {

}
