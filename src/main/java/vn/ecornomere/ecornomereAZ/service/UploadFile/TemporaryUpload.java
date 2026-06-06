package vn.ecornomere.ecornomereAZ.service.UploadFile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ecornomere.ecornomereAZ.repository.TemporaryUploadRepository;

@Service
@RequiredArgsConstructor
public class TemporaryUpload {
    private final TemporaryUploadRepository temporaryUploadRepository;

    public void markAsUsed(String publicId) {

        if (publicId == null || publicId.isBlank()) {
            return;
        }

        int updated = temporaryUploadRepository.markAsUsed(publicId);

        if (updated == 0) {
            throw new IllegalStateException(
                    "Không tìm thấy TemporaryUpload: " + publicId);
        }
    }

    public void markAsUnused(String publicId) {

        if (publicId == null || publicId.isBlank()) {
            return;
        }

        temporaryUploadRepository.markAsUnused(publicId);
    }



}
