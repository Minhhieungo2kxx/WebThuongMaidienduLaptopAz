package vn.ecornomere.ecornomereAZ.service.UploadFile;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import vn.ecornomere.ecornomereAZ.config.UploadfileServer.UploadFileCloudinary;
import vn.ecornomere.ecornomereAZ.config.UploadfileServer.UploadProperties;
import vn.ecornomere.ecornomereAZ.model.entity.TemporaryUpload;
import vn.ecornomere.ecornomereAZ.repository.TemporaryUploadRepository;


import org.springframework.security.core.Authentication;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import java.io.IOException;

import java.time.Instant;

import java.util.Map;

import com.cloudinary.utils.ObjectUtils;

@Service
@RequiredArgsConstructor
public class FileService {

      private final UploadProperties uploadProperties;

      private final Cloudinary cloudinary;

      private final UploadFileCloudinary uploadFile;

      private final TemporaryUploadRepository temporaryUploadRepository;

      public Map<String, String> uploadFile(MultipartFile file, String folderName, Authentication authentication)
                  throws IOException {
            uploadFile.validateUploadImage(file, folderName);
            String originalName = file.getOriginalFilename();
            String baseName = originalName.substring(0, originalName.lastIndexOf("."));
            String uniqueName = System.currentTimeMillis() + "-" + baseName;

            Map<?, ?> result = cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap(
                                    "folder", folderName,
                                    "public_id", uniqueName,
                                    "resource_type", "auto",
                                    "access_mode", "public"

                        ));

            String secureUrl = result.get("secure_url").toString();
            String publicId = result.get("public_id").toString();
            String resourceType = result.get("resource_type").toString();
            handleTemporaryUpload(publicId, secureUrl, resourceType, authentication);

            return Map.of(
                        "url", secureUrl,
                        "publicId", publicId,
                        "resourceType", resourceType // TRẢ VỀ
            );
      }

      public void handleTemporaryUpload(String publicId, String secureUrl, String resourceType,
                  Authentication authentication) {
            TemporaryUpload temporaryUpload = TemporaryUpload.builder()
                        .publicId(publicId)
                        .url(secureUrl)
                        .resourceType(resourceType)
                        .used(false)
                        .createdAt(Instant.now())
                        .build();
            temporaryUploadRepository.save(temporaryUpload);

      }

      public void deleteFile(String publicId, String resourceType) throws IOException {

            Map<?, ?> result = cloudinary.uploader().destroy(
                        publicId,
                        ObjectUtils.asMap("resource_type", resourceType));

            String resultStatus = result.get("result").toString();

            if (!"ok".equals(resultStatus)) {
                  throw new IllegalStateException("Không thể xóa file trên Cloudinary: " + resultStatus);
            }
      }

}
