package back.smart.code.books.controller;


import back.smart.code.books.dto.BooksDTO;
import back.smart.code.books.service.BookService;
import back.smart.code.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return ResponseEntity.ok().body(ApiResponse.ok(bookService.getBooksList(pageable)));
    }


    @GetMapping("/books/{bkCode}")
    public ResponseEntity<ApiResponse<?>> getBook(@PathVariable("bkCode")String bkCode) throws Exception {

        return ResponseEntity.ok().body(ApiResponse.ok(bookService.getBook(bkCode)));
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
