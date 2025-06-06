package vn.ecornomere.ecornomereAZ.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.ecornomere.ecornomereAZ.model.Product;
import vn.ecornomere.ecornomereAZ.repository.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Product> listNameItems(String name) {
        return this.itemRepository.findByTarget(name);

    }

    public List<Product> getAllItems() {
        return this.itemRepository.findAll();

    }

    public List<Product> getBytargetIn(List<String> listtarget) {
        return this.itemRepository.findByTargetIn(listtarget);

    }
}
