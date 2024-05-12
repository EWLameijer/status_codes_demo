package org.ericwubbo.statuscodesdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

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
        return itemRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item, UriComponentsBuilder uriComponentsBuilder) {
        if (item.getName() == null || item.getPrice().compareTo(BigDecimal.ZERO) <= 0 || item.getId() != null)
            return ResponseEntity.badRequest().build();
        itemRepository.save(item);
        var location = uriComponentsBuilder.path("{id}").buildAndExpand(item.getId()).toUri();
        return ResponseEntity.created(location).body(item);
    }
}
