package back.smart.code.books.repository;

import back.smart.code.books.entity.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BooksRepository extends JpaRepository<BooksEntity, String>,
                                            JpaSpecificationExecutor<BooksEntity> {




    @Query(value="""
            select b from BooksEntity b  
                left join fetch b.tbBooksFiles 
            where b.bkCode =:bkCode
            """)
    Optional<BooksEntity> getBook(@Param("bkCode")String bkCode);

}
