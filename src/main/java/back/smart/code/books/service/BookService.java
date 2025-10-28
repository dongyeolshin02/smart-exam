package back.smart.code.books.service;

import back.smart.code.books.dto.BooksDTO;
import back.smart.code.books.entity.BooksEntity;
import back.smart.code.books.repository.BooksRepository;
import back.smart.code.common.utils.CommonFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {

    @Value("${server.file.upload.path}")
    private String filePath;

    private final  CommonFileUtils commonFileUtils;
    private final BooksRepository booksRepository;


    public Map<String, Object> getBooksList(Pageable pageable) throws Exception {

        Page<BooksEntity> booksPage = booksRepository.findAll(pageable);
        List<BooksDTO.Response> bookList =
                booksPage.getContent().stream()
                .map(BooksDTO.Response::of).toList();

        return Map.of(
                "total" , booksPage.getTotalElements(),
                "data" , bookList,
                "page" , pageable.getPageNumber()
             );
    }

}
