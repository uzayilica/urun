package com.uzay.urun.entity;

import com.uzay.urun.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Builder
@Entity
public class Urun extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String ad;

    @Column(nullable = false)
    private BigDecimal fiyat;

    @Column(nullable = false)
    private int stokMiktar;

    @Column(nullable = false)
    private String aciklama;

    @Column(nullable = false)
    private String kategori;

    @Column(nullable = false)
    private LocalDateTime olusturmaTarihi;

//!    @PrePersist anotasyonunu sadece entity sınıflarında kullanmalısınız.
    @PrePersist
    public void prePersist() {
        this.olusturmaTarihi = LocalDateTime.now(); // Kayıt tarihi otomatik olarak ayarlanır
    }

}
