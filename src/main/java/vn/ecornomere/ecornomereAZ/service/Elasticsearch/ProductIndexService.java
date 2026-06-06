package vn.ecornomere.ecornomereAZ.service.Elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ecornomere.ecornomereAZ.model.document.ProductDocument;
import vn.ecornomere.ecornomereAZ.model.entity.Product;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductIndexService {
    private final ElasticsearchClient elasticsearchClient;

    public void indexProduct(Product product)
            throws IOException {
        ProductDocument document =
                ProductDocument.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .shortDesc(product.getShortDesc())
                        .detailDesc(product.getDetailDesc())
                        .factory(product.getFactory())
                        .target(product.getTarget())
                        .price(product.getPrice())
                        .sold(product.getSold())
                        .quantity(product.getQuantity())
                        .build();

        elasticsearchClient.index(i -> i
                .index("products")
                .id(String.valueOf(product.getId()))
                .document(document));
    }
    public void DeleteIndexElasticsearch(Product product){
        try {
            elasticsearchClient.delete(d -> d
                    .index("products")
                    .id(String.valueOf(product.getId())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @PostConstruct
    public void createProductIndex() {
        try {
            boolean exists =
                    elasticsearchClient.indices()
                            .exists(e -> e.index("products")).value();

            if (exists) {
                return;
            }
            elasticsearchClient.indices().create(c -> c
                    .index("products")
                    .mappings(m -> m

                            .properties("name", p -> p
                                    .text(t -> t))

                            .properties("shortDesc", p -> p
                                    .text(t -> t))

                            .properties("detailDesc", p -> p
                                    .text(t -> t))

                            .properties("factory", p -> p
                                    .keyword(k -> k))

                            .properties("target", p -> p
                                    .keyword(k -> k))

                            .properties("price", p -> p
                                    .double_(d -> d))

                            .properties("sold", p -> p
                                    .long_(l -> l))

                            .properties("quantity", p -> p
                                    .long_(l -> l))
                    ));

            System.out.println("Created products index");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
