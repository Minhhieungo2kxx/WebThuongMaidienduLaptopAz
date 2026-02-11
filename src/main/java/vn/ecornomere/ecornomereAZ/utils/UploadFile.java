package vn.ecornomere.ecornomereAZ.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile {
    private static final String UPLOAD_BASE_DIR = "D:/Ngominhhieu/Back_end_java/spring-mvc-ecornomere-laptopaz/myapp/uploads/";
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/jpeg", "image/png", "image/webp");

    public UploadFile() {

    }

    public String getnameFile(MultipartFile avatarFile, String nameFolder)
            throws IOException, IllegalStateException {

        if (avatarFile == null || avatarFile.isEmpty()) {
            return "";
        }
        // VALIDATE
        validateImageFile(avatarFile);

        String uploadDir = UPLOAD_BASE_DIR + nameFolder;
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = avatarFile.getOriginalFilename();

        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."))
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "_")
                .replaceAll("_+", "_");

        String uniqueFileName = baseName + "_" + System.currentTimeMillis() + fileExtension;

        Path filePath = uploadPath.resolve(uniqueFileName);
        avatarFile.transferTo(filePath.toFile());

        return uniqueFileName;
    }

    public void deleteImageFile(String fileName, String folderName) {
        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        try {
            Path imagePath = Paths.get(UPLOAD_BASE_DIR, folderName, fileName);

            if (Files.exists(imagePath)) {
                Files.delete(imagePath);
            }
        } catch (IOException e) {
            // Không throw RuntimeException để tránh rollback DB
            System.err.println("Không thể xóa file ảnh: " + e.getMessage());
        }
    }

    public void validateImageFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File ảnh không được để trống");
        }
        // 1. Validate dung lượng (<= 2MB)
        long maxSize = 15 * 1024 * 1024; // 15MB
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException(
                    "Dung lượng ảnh tối đa là 15MB");
        }

        // 2. Validate content-type
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                    "Chỉ cho phép upload ảnh JPG, PNG, WEBP");
        }

        // 3. Validate extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("Tên file không hợp lệ");
        }

        String extension = originalFilename
                .substring(originalFilename.lastIndexOf(".") + 1)
                .toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    "Định dạng ảnh không hợp lệ");
        }
    }

}
