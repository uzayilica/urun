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
@Table(name = "urun", uniqueConstraints = {
        @UniqueConstraint(name = "urun_ad_key", columnNames = {"ad"}),
        @UniqueConstraint(name = "urun_aciklama_key", columnNames = {"aciklama"})
})
public class Urun extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
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

    @PrePersist
    public void prePersist() {
        this.olusturmaTarihi = LocalDateTime.now();
    }
}