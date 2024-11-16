package com.uzay.urun.dto;

import jakarta.persistence.Column;
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
public class UrunRequestDto {


    @NotBlank(message = "ürün adı boş bırakılamaz")
    private String ad;

    @NotNull(message = "fiyat boş bırakılamaz")
    @Min(value = 0 ,message = "fiyat 0'dan küçük olamaz")
    private BigDecimal fiyat;

    @NotNull(message = "stok miktarı boş bırakılamaz")
    @Min(value = 0 ,message = "stok 0'dan küçük olamaz")
    private int stokMiktar;

    @NotBlank(message = "aciklama boş bırakılamaz")
    private String aciklama;

    @NotBlank(message = "kategori boş bırakılamaz")
    private String kategori;






}
