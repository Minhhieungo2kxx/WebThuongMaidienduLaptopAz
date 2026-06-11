package vn.ecornomere.ecornomereAZ.service.Events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import vn.ecornomere.ecornomereAZ.dto.record.ProductDeletedEvent;
import vn.ecornomere.ecornomereAZ.dto.record.ProductEvent;
import vn.ecornomere.ecornomereAZ.service.Elasticsearch.ProductIndexService;
import vn.ecornomere.ecornomereAZ.service.UploadFile.FileService;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductIndexEventListener {

    private final ProductIndexService productIndexService;
    private final FileService fileService;

    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ProductEvent event) {

        productIndexService.indexProduct(event.product());
    }

    @Async("taskExecutor")
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(ProductDeletedEvent event) {
        try {
            productIndexService.DeleteIndexElasticsearch(
                    event.productId());
        } catch (Exception ex) {
            log.error("Delete ES failed", ex);
        }

        try {
            fileService.deleteFile(
                    event.publicId(),
                    event.resourceType()
            );
        } catch (Exception ex) {
            log.error("Delete file failed", ex);
        }
    }
}
