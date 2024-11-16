package com.uzay.urun.repository;

import com.uzay.urun.entity.Urun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UrunRepository  extends JpaRepository<Urun,Integer> {

    Optional<Urun> findByAd(String ad);
    List<Urun> findByFiyat(BigDecimal fiyat);


}
