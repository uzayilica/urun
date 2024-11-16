package com.uzay.urun.service;

import com.uzay.urun.dto.UrunRequestDto;
import com.uzay.urun.dto.UrunResponseDto;
import com.uzay.urun.entity.Urun;

import java.util.List;

public interface UrunServiceInterface {

    UrunResponseDto getUrun(Integer id) ;

    List<UrunResponseDto> getAllUrun();

    UrunResponseDto addUrun(UrunRequestDto urun);

    Boolean deleteUrun(Integer id);

    UrunResponseDto updateUrun(Integer id,UrunRequestDto urun);




}
