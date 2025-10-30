package back.smart.code.common.utils;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.MultiStepRescaleOp;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Component
public class CommonFileUtils {


    public  Map<String, Object> uploadFile(MultipartFile file, String path) {
        Map<String, Object> map = new HashMap<>();

        if(file != null && !file.isEmpty()) {

            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
            String storedName = uuId + extension;

            String filePath = path + storedName;
            File newFile = new File(filePath);

            //파일 경로가 없으면 만들기
            if(!newFile.getParentFile().exists()) {
                    newFile.getParentFile().mkdirs();
            }

            try {
                //파일 복사
                file.transferTo(newFile);
                map.put("storedName", storedName);
                map.put("originalName", fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    public  List<Map<String, Object>> uploadFile(List<MultipartFile> files, String path) {
        List<Map<String, Object>> mapList = new ArrayList<>();

        if(files != null && !files.isEmpty()) {
            for(MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String extension = fileName.substring(fileName.lastIndexOf("."));
                String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
                String storedName = uuId + extension;

                String filePath = path + storedName;
                File newFile = new File(filePath);

                //파일 경로가 없으면 만들기
                if(!newFile.getParentFile().exists()) {
                    newFile.getParentFile().mkdirs();
                }

                try {

                    //파일 복사
                    file.transferTo(newFile);

                    Map<String, Object> map = new HashMap<>();
                    map.put("storedName", storedName);
                    map.put("fileName", fileName);
                    mapList.add(map);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return mapList;
    }


    //썸네일 만들기
    public  String makeThumbnail(File file,  int width, int height, String filePath) {

        String thumbName = "";
        if(file != null) {

            String fileName = file.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".")+1);
            thumbName = fileName.substring(0,  fileName.lastIndexOf(".")) + "_thumb." + extension;

            try (FileInputStream in = new FileInputStream(file);
                    BufferedInputStream bf = new BufferedInputStream(in);
                 ){

                //원본파일을 메모리에 복사
                BufferedImage originImg = ImageIO.read(bf);
                //사이즈를 줄이기
                MultiStepRescaleOp scaleOp = new MultiStepRescaleOp(width, height);
                //사이즈 줄일 때 이미지 깨지지 않고 선명하게 처리
                scaleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);
                //줄어든 사이즈 적용해서 이미지 생성 > 아직 메모리에 있음
                BufferedImage thumbImg = scaleOp.filter(originImg, null);
                String thumbPath = filePath + thumbName;

                File thumbFile = new File(thumbPath);

                //경로 없다면 생성
                if(!thumbFile.getParentFile().exists()) {
                    thumbFile.getParentFile().mkdirs();

                }
                //메모리 >> 실제파일로 저장
                boolean isWrite = ImageIO.write(thumbImg, extension, thumbFile);

                if(!isWrite) {
                    throw new Exception("썸네일 생성 실패 ");
                }
            } catch (Exception e) {
                  e.printStackTrace();
            }
        }
        return thumbName;
    }

    //파일 삭제
    public static void deleteFile(String filePath) {

        try{
            File file = new File(filePath);
            if(file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
