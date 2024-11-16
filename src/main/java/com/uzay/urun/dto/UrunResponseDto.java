package com.uzay.urun.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Builder
public class UrunResponseDto {
    private String ad;
    private BigDecimal fiyat;
    private int stokMiktar;
    private String aciklama;
    private String kategori;
    private LocalDateTime olusturmaTarihi;

    // Audit bilgileri
    private String created_by;
    private String updated_by;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;



}
