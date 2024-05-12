package org.ericwubbo.statuscodesdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public Iterable<Item> getAll() {
        return itemRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Item> getById(@PathVariable long id) {
        Optional<Item> possiblyFoundItem = itemRepository.findById(id);
        if (possiblyFoundItem.isPresent()) return ResponseEntity.ok(possiblyFoundItem.get());
        return ResponseEntity.notFound().build();
    }
}
