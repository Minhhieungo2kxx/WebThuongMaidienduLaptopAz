package vn.ecornomere.ecornomereAZ.config.UploadfileServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UploadFileCloudinary {
      private final UploadProperties uploadProperties;
      private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");

      public void validateUploadImage(MultipartFile file, String nameFolder) throws IOException {

            if (file == null || file.isEmpty()) {
                  throw new IllegalStateException("File không được để trống");
            }

            // 1. Check size (ví dụ 5MB - bạn chỉnh theo config)
            long maxSize = uploadProperties.getMaxFileSize().toBytes();
            if (file.getSize() > maxSize) {
                  throw new IllegalStateException("Ảnh vượt quá dung lượng cho phép");
            }

            // 2. Check extension
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || !originalFileName.contains(".")) {
                  throw new IllegalStateException("File không hợp lệ");
            }

            String extension = originalFileName
                        .substring(originalFileName.lastIndexOf('.') + 1)
                        .toLowerCase();

            if (!ALLOWED_EXTENSIONS.contains(extension)) {
                  throw new IllegalStateException("Chỉ hỗ trợ file ảnh: jpg, jpeg, png, gif, webp");
            }

            // 3. Check đúng là ảnh thật (tránh fake .jpg)
            try (InputStream input = file.getInputStream()) {
                  BufferedImage image = ImageIO.read(input);

                  if (image == null) {
                        throw new IllegalStateException("File không phải là ảnh hợp lệ");
                  }

                  // 4. (Tuỳ chọn) check kích thước pixel
                  int width = image.getWidth();
                  int height = image.getHeight();

                  if (width < 50 || height < 50) {
                        throw new IllegalStateException("Ảnh quá nhỏ (pixel không hợp lệ)");
                  }

                  if (width > 5000 || height > 5000) {
                        throw new IllegalStateException("Ảnh quá lớn về kích thước pixel");
                  }
            }
      }

}
