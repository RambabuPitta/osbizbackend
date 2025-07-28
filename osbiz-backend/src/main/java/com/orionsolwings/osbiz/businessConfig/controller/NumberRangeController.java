package com.orionsolwings.osbiz.businessConfig.controller;

import com.orionsolwings.osbiz.businessConfig.model.NumberRange;
import com.orionsolwings.osbiz.businessConfig.service.NumberRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/number-ranges")
public class NumberRangeController {

    @Autowired
    private NumberRangeService service;

    @PostMapping
    public ResponseEntity<NumberRange> create(@RequestBody NumberRange numberRange) {
        return ResponseEntity.ok(service.create(numberRange));
    }

    @GetMapping("/{moduleType}")
    public ResponseEntity<NumberRange> getByModuleType(@PathVariable String moduleType) {
        Optional<NumberRange> result = service.getByModuleType(moduleType);
        return result.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<NumberRange>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping
    public ResponseEntity<NumberRange> update(@RequestBody NumberRange numberRange) {
        return ResponseEntity.ok(service.update(numberRange));
    }

    @DeleteMapping("/{moduleType}")
    public ResponseEntity<Void> delete(@PathVariable String moduleType) {
        service.deleteByModuleType(moduleType);
        return ResponseEntity.noContent().build();
    }
}
