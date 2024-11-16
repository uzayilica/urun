package com.uzay.urun.controller;

import com.uzay.urun.dto.UrunRequestDto;
import com.uzay.urun.dto.UrunResponseDto;
import com.uzay.urun.entity.Urun;
import com.uzay.urun.mapper.UrunMapper;
import com.uzay.urun.service.UrunService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/urun-service")
@RequiredArgsConstructor
public class UrunController {

    private final UrunService service;

    @GetMapping("/get-urun/{id}")
    public ResponseEntity<UrunResponseDto> getUrun( @PathVariable Integer id) {
        UrunResponseDto urun = service.getUrun(id);
        if (urun == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(urun);
    }

    @GetMapping("/get-urunler")
    public ResponseEntity<?> getUrunLer() {
        List<UrunResponseDto> allUrun = service.getAllUrun();
        if (allUrun.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ürünler bulunamadı");
        }
        return ResponseEntity.ok(allUrun);
    }

    @PostMapping("/add-urun")
    public ResponseEntity<?> addUrun(@Valid @RequestBody UrunRequestDto urun) {
        UrunResponseDto urunResponseDto = service.addUrun(urun);
        if (urunResponseDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ürünler bulunamadı");
        }
        return ResponseEntity.ok(urunResponseDto);
    }

    @DeleteMapping("/delete-urun/{id}")
    public ResponseEntity<?> deleteUrun(@PathVariable Integer id) {
        Boolean isDeleted = service.deleteUrun(id);
        if (isDeleted) {
            return ResponseEntity.ok("silindi");  // Başarılı silme işlemi
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("silinemedi");  // Silinemedi, kaynak bulunamadı
        }
    }


    @PutMapping("/update-urun/{id}")
    public ResponseEntity<?> updateUrun(@Valid @PathVariable Integer id, @RequestBody UrunRequestDto urunRequestDto) {
        UrunResponseDto urunResponseDto = service.updateUrun(id, urunRequestDto);
        if (urunResponseDto == null) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("ürün güncellemede hata oluştu");
        }
        return ResponseEntity.ok(urunResponseDto);
    }







}
