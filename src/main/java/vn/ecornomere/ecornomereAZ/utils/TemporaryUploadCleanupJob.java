package vn.ecornomere.ecornomereAZ.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.ecornomere.ecornomereAZ.model.entity.TemporaryUpload;
import vn.ecornomere.ecornomereAZ.repository.TemporaryUploadRepository;
import vn.ecornomere.ecornomereAZ.service.UploadFile.FileService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TemporaryUploadCleanupJob {
    private final TemporaryUploadRepository repository;
    private final FileService fileService;

    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanup() {

        Instant cutoff = Instant.now().minus(30, ChronoUnit.DAYS);

        log.info("Start cleanup job");

        while (true) {

            List<TemporaryUpload> batch =
                    repository.findTop100ByUsedFalseAndCreatedAtBeforeOrderByIdAsc(cutoff);

            if (batch.isEmpty()) {
                break;
            }

            for (TemporaryUpload file : batch) {

                try {
                    // 1. delete cloudinary trước
                    fileService.deleteFile(
                            file.getPublicId(),
                            file.getResourceType()
                    );

                    // 2. delete DB sau khi OK
                    repository.delete(file);

                } catch (Exception e) {
                    log.error("Failed file {}", file.getPublicId(), e);

                    // optional: retry logic sau này
                }
            }

            // flush + clear context nếu cần (Hibernate optimization)
        }

        log.info("Cleanup finished");
    }

}
