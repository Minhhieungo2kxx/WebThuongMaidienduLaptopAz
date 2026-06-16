package vn.ecornormere.ecornomereAZ.services.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;
import vn.ecornomere.ecornomereAZ.dto.request.ProductFilterDTO;
import vn.ecornomere.ecornomereAZ.dto.request.ProductSearchRequest;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.repository.ItemRepository;
import vn.ecornomere.ecornomereAZ.repository.ProductRepository;
import vn.ecornomere.ecornomereAZ.service.FilterProductService;
import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.ProductService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilterProductServiceTest {
    @Mock
    private ItemService itemService;
    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ProductRepository productRepository;


    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private FilterProductService filterProductService;

    @Test
    void shouldReturnProductFilterViewWhenSortPriceAsc() {

        // Arrange
        String page = "0";
        String sort = "price-asc";
        Page<Product> mockPage = new PageImpl<>(List.of(new Product()));

        when(itemService.getAllItemsPaginatedSorted(0, 6, sort)).thenReturn(mockPage);
        // Act
        String view = filterProductService.ShowProductSortUser(page, sort, model);
        // Assert
        assertEquals("client/homepage/productfilter", view);
        verify(model).addAttribute("allProducts", mockPage.getContent());
        verify(model).addAttribute("currentPage", 0);
        verify(model).addAttribute("totalPages", mockPage.getTotalPages());
        verify(model).addAttribute("sort", sort);
    }
    @Test
    void shouldDefaultToPageZeroWhenPageIsInvalid() {
        // Arrang
        when(itemService.getAllItemsPaginatedSorted(0, 6, "price-asc")).thenReturn(Page.empty());
        // Act
        filterProductService.ShowProductSortUser("abc", "price-asc", model);
        // Assert
        verify(itemService).getAllItemsPaginatedSorted(0, 6, "price-asc");
    }
    @Test
    void shouldDefaultToPageZeroWhenPageIsNegative() {

        when(itemService.getAllItemsPaginatedSorted(0, 6, "price-asc")).thenReturn(Page.empty());
        filterProductService.ShowProductSortUser("-1", "price-asc", model);
        verify(itemService).getAllItemsPaginatedSorted(0, 6, "price-asc");
    }
    @Test
    void shouldFilterProductsSuccessfully() {

        ProductFilterDTO dto = new ProductFilterDTO();
        dto.setFactory(List.of("Apple"));
        dto.setTarget(List.of("Gaming"));
        dto.setPrice(List.of("1000-2000"));
        dto.setSort(List.of("price-asc"));
        dto.setPage("0");
        Page<Product> page = new PageImpl<>(List.of(new Product()));
        when(productService.filterProducts(any(), any(), any(),
                anyString()
                ,eq(0), eq(6)))
                .thenReturn(page);
        String result = filterProductService.filterProductUser(dto, model);
        assertEquals("client/homepage/productfilter", result);
        verify(productService).filterProducts(dto.getFactory(), dto.getTarget(), dto.getPrice(),
                        "price-asc",
                        0,
                        6);
    }
    @Test
    void shouldUseNoSortWhenSortIsNull() {

        ProductFilterDTO dto = new ProductFilterDTO();
        dto.setSort(null);
        dto.setPage("0");
        when(productService.filterProducts(any(), any(), any(), anyString(),
                anyInt(),
                anyInt()))
                .thenReturn(Page.empty());
        filterProductService.filterProductUser(dto, model);
        verify(productService).filterProducts(any(), any(), any(),
                        eq("no-sort"),
                        eq(0),
                        eq(6));
    }
    @Test
    void shouldUsePageZeroWhenPageInvalid() {

        ProductFilterDTO dto = new ProductFilterDTO();
        dto.setPage("abc");
        when(productService.filterProducts(any(), any(), any(), anyString(),
                anyInt(),
                anyInt()))
                .thenReturn(Page.empty());
        filterProductService.filterProductUser(dto, model);
        verify(productService).filterProducts(any(), any(), any(),
                        anyString(),
                        eq(0),
                        eq(6));
    }
    @Test
    void shouldSearchProductsSuccessfully() {

        ProductSearchRequest request = new ProductSearchRequest();
        request.setSearchTerm("iphone");
        request.setPage(0);
        request.setSize(6);
        Page<Product> page = new PageImpl<>(List.of(new Product()));
        when(productService.searchProducts(request)).thenReturn(page);
        String result = filterProductService.searchProductUser(request, model);
        assertEquals("client/homepage/searchproducs", result);
        verify(productService).searchProducts(request);
        verify(model).addAttribute("searchTerm", "iphone");
    }




}
