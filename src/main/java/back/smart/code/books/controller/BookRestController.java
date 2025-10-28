package back.smart.code.books.controller;


import back.smart.code.books.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
@Slf4j
public class BookRestController {

    private final BookService bookService;

    @PostMapping("/ed/img")
    public ResponseEntity<?> addBook(@RequestPart("img") MultipartFile img) throws Exception {
        Map<String,Object> map = bookService.uploadEditorImg(img);
        log.info("이미지저장: {}", map.get("imageUrl") );
        return ResponseEntity.ok().body(map);
    }
}
