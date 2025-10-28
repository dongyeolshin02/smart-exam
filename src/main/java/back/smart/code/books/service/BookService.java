package back.smart.code.books.service;

import back.smart.code.books.dto.BooksDTO;
import back.smart.code.books.dto.BooksFileDTO;
import back.smart.code.books.entity.BooksEntity;
import back.smart.code.books.entity.BooksFileEntity;
import back.smart.code.books.repository.BooksRepository;
import back.smart.code.common.dto.ApiResponse;
import back.smart.code.common.utils.CommonFileUtils;
import back.smart.code.common.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {

    @Value("${server.file.upload.path}")
    private String filePath;

    private final BooksRepository booksRepository;
    private final  CommonFileUtils fileUtils;
    private List<String> extentions =
            Arrays.asList("jpg", "jpeg", "gif", "png", "webp", "bmp");


    @Transactional
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


    @Transactional
    public Map<String, Object> getBook(String bkCode) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();

        BooksEntity entity =
                booksRepository.getBook(bkCode)
                        .orElseThrow(() -> new RuntimeException("책이 없음"));

        BooksDTO.Response  book = BooksDTO.Response.of(entity);
        resultMap.put("vo", book);
        return resultMap;
    }



    @Transactional
    public ApiResponse<String> createBooks(BooksDTO.Request request) throws Exception {

        Map<String, Object> fileMap = this.uploadImageFiles(request.getFile());
        BooksFileEntity booksFileEntity = new BooksFileEntity();
        booksFileEntity.setBfName(fileMap.get("originalName").toString());
        booksFileEntity.setBfStoredName(fileMap.get("storedName").toString());
        booksFileEntity.setThumbName(fileMap.get("thumbName").toString());
        booksFileEntity.setFilePath(filePath);

        BooksEntity entity = new BooksEntity();

        //랜덤 ID 생성
        String newCode = CommonUtils.getRandUUID();

        entity.setBkCode(newCode);
        entity.setBkName(request.getBkName());
        entity.setContents(request.getContents());
        entity.setStock(request.getStock());
        entity.setPrice(request.getPrice());
        entity.setTypes(request.getTypes());

        entity.addFiles(booksFileEntity);

        booksRepository.save(entity);

        return ApiResponse.ok(null);
    }


    @Transactional
    public ApiResponse<String> updateBooks(BooksDTO.Request request) throws Exception {

        //기존 책 불러오기
        BooksEntity entity =
                booksRepository.getBook(request.getBkCode())
                        .orElseThrow(() -> new RuntimeException("책이 없음"));

        //DTO로 만들기
        BooksDTO.Response book = BooksDTO.Response.of(entity);

        //내용 업데이트
        entity.setBkName(request.getBkName());
        entity.setContents(request.getContents());
        entity.setStock(request.getStock());
        entity.setPrice(request.getPrice());
        entity.setTypes(request.getTypes());

        //파일 교체
        if(request.getFile() != null && !request.getFile().isEmpty()) {

            Map<String, Object> fileMap = this.uploadImageFiles(request.getFile());
            entity.getTbBooksFiles().clear();

            if(fileMap != null) {
                BooksFileEntity booksFileEntity = new BooksFileEntity();
                booksFileEntity.setBfName(fileMap.get("originalName").toString());
                booksFileEntity.setBfStoredName(fileMap.get("storedName").toString());
                booksFileEntity.setThumbName(fileMap.get("thumbName").toString());
                booksFileEntity.setFilePath(filePath);

                entity.addFiles(booksFileEntity);
            }else {
                throw new RuntimeException("파일 업로드 실패");
            }
        }

        //기존 파일 삭제
        if(request.getFile() != null && !request.getFile().isEmpty()) {
            if(book.getBookfileList() != null && book.getBookfileList().size() > 0) {
                for(BooksFileDTO.Response fileDTO : book.getBookfileList()) {
                    String oldFilePath = fileDTO.getFilePath() + fileDTO.getBfStoredName();
                    //파일 삭제
                    fileUtils.deleteFile(oldFilePath);
                }
            }
        }

        return ApiResponse.ok(null);
    }



    //파일 업로드 별도 처리
    private  Map<String, Object> uploadImageFiles(MultipartFile file) throws Exception {

        String fileName  = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".")+1);

        if(!extentions.contains(ext)) {
            throw new RuntimeException("파일형식이 맞지 않습니다. 이미지만 가능합니다.");
        }

        Map<String, Object> fileMap =
                fileUtils.uploadFile(file, filePath);

        if(fileMap == null) {
            throw new RuntimeException("파일 업로드가 실패했습니다.");
        }

        String thumbFilePath = filePath + "thumb" + File.separator;
        String storedFilePath = filePath + fileMap.get("storedName").toString();

        File thumbFile = new File(storedFilePath);

        if(!thumbFile.exists()) {
            throw new RuntimeException("업로드파일이 존재하지 않음 ");
        }

        String thumbName = fileUtils.makeThumbnail( thumbFile, 150, 150, thumbFilePath);

        fileMap.put("thumbName", thumbName);

        return fileMap;
    }



}
