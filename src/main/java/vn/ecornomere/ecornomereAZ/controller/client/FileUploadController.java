package vn.ecornomere.ecornomereAZ.controller.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.ecornomere.ecornomereAZ.dto.response.ApiResponse;
import vn.ecornomere.ecornomereAZ.dto.response.UploadFileResponse;
import vn.ecornomere.ecornomereAZ.service.UploadFile.FileService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/file")
@Slf4j
@RequiredArgsConstructor
public class FileUploadController {
      private final FileService fileService;

      // Upload file dich vu ben thu 3 cloudinary (nhu kieu AWS)
      @PostMapping("/cloudinary")
      public ResponseEntity<?> uploadFileCloudinary(
                  @RequestParam("file") MultipartFile file,
                  @RequestParam(value = "folder", defaultValue = "default") String folder, Authentication authentication) {
            return fileService.uploadCloudinary(file,folder,authentication);
      }

}
