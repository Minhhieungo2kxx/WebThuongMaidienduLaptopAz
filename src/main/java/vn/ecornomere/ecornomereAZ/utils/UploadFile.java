package vn.ecornomere.ecornomereAZ.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile {

    public UploadFile() {

    }

    public String getnameFile(MultipartFile avatarFile, String nameFolder) throws IOException, IllegalStateException {
        String target = "";

        // Xử lý avatar (nếu có)
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String uploadDir = "D:/Ngominhhieu/Back_end_java/spring-mvc-ecornomere-laptopaz/myapp/uploads/"
                    + nameFolder;
            Path uploadPath = Paths.get(uploadDir);

            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Lấy phần mở rộng của file
            @SuppressWarnings("null")
            String fileExtension = avatarFile.getOriginalFilename()
                    .substring(avatarFile.getOriginalFilename().lastIndexOf("."));

            // Tạo tên file duy nhất
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // Tạo đường dẫn lưu file
            Path filePath = uploadPath.resolve(uniqueFileName);
            avatarFile.transferTo(filePath.toFile());

            target = uniqueFileName;

        }

        return target;

    }

}
