package back.smart.code.books.service;

import back.smart.code.books.repository.BooksImgRepository;
import back.smart.code.common.utils.CommonFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {

    @Value("${server.file.upload.path}")
    private String filePath;

    @Value("${server.file.editor.path}")
    private String editorPath;

    private final BooksImgRepository booksImgRepository;

    private CommonFileUtils commonFileUtils;

    public Map<String, Object> uploadEditorImg(MultipartFile file) throws Exception{

        Map<String, Object> fileMap = commonFileUtils.uploadFile(file, editorPath);
        String imageUrl = "http://localhost:9090/img/editor/" + fileMap.get("storedName");

        fileMap.put("imageUrl",imageUrl);
        return fileMap;
    }
}
