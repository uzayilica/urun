package com.uzay.urun.mapper;

import com.uzay.urun.dto.UrunRequestDto;
import com.uzay.urun.dto.UrunResponseDto;
import com.uzay.urun.entity.Urun;

public class UrunMapper  implements  UrunMapperInterface{


    @Override
    public Urun requestToUrun(UrunRequestDto urunRequestDto) {
       return Urun.builder()
                .ad(urunRequestDto.getAd())
                .fiyat(urunRequestDto.getFiyat())
                .stokMiktar(urunRequestDto.getStokMiktar())
                .aciklama(urunRequestDto.getAciklama())
                .kategori(urunRequestDto.getKategori())
                .build();
    }

    @Override
    public UrunResponseDto urunToResponse(Urun urun) {
       return UrunResponseDto.builder()
                .ad(urun.getAd())
                .fiyat(urun.getFiyat())
                .stokMiktar(urun.getStokMiktar())
                .aciklama(urun.getAciklama())
                .kategori(urun.getKategori())
                .olusturmaTarihi(urun.getOlusturmaTarihi())
               .created_at(urun.getCreated_at())
               .created_by(urun.getCreated_by())
               .updated_at(urun.getUpdated_at())
               .updated_by(urun.getUpdated_by())
               .build();
    }
}
