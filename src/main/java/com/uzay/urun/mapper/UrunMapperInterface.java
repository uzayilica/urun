package com.uzay.urun.mapper;

import com.uzay.urun.dto.UrunRequestDto;
import com.uzay.urun.dto.UrunResponseDto;
import com.uzay.urun.entity.Urun;

public interface UrunMapperInterface {

    public Urun requestToUrun(UrunRequestDto urunRequestDto);

    public UrunResponseDto urunToResponse(Urun urun);


}
