package back.smart.code.books.controller;


import back.smart.code.books.dto.BooksDTO;
import back.smart.code.books.entity.BooksEntity;
import back.smart.code.books.service.BookService;
import back.smart.code.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class BookRestController {

    private final BookService bookService;



    @GetMapping("/books")
    public ResponseEntity<ApiResponse<?>> getBooks(
            @PageableDefault(size = 10, page = 0, sort="createAt",
                    direction = Sort.Direction.DESC)Pageable pageable) throws Exception {

        Map<String, Object> result = bookService.getBooksList(pageable);
        return ResponseEntity.ok().body(ApiResponse.ok(result));
    }

    @PostMapping("/books")
    public ResponseEntity<ApiResponse<?>> addBook(@ModelAttribute  BooksDTO.Request booksDTO) throws Exception {
        return ResponseEntity.ok(bookService.createBooks(booksDTO));
    }



    @PutMapping("/books")
    public ResponseEntity<ApiResponse<?>> updateBook(@ModelAttribute  BooksDTO.Request booksDTO) throws Exception {
        return ResponseEntity.ok(bookService.createBooks(booksDTO));
    }

}
