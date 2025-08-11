package com.example.enotes.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/cache")
@Tag(name = "Cache", description = "Cache APIs")
public interface CacheEndpoint {

    @GetMapping("/")
    public ResponseEntity<?> getAllCache();

    @GetMapping("/{cache_name}")
    public ResponseEntity<?> getCacheName(@PathVariable String cache_name);

    @DeleteMapping("/")
    public ResponseEntity<?> removeAllCache();

}
