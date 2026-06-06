package vn.ecornomere.ecornomereAZ.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ecornomere.ecornomereAZ.service.Elasticsearch.ProductMigrationService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/elasticsearch")
public class ElasticsearchAdminController {
    private final ProductMigrationService migrationService;

    @PostMapping("/migrate")
    public ResponseEntity<String> migrate()
            throws IOException {

        migrationService.migrateAllProducts();

        return ResponseEntity.ok("Migration completed");
    }
}
