package com.uzay.urun.service;

import com.uzay.urun.dto.UrunRequestDto;
import com.uzay.urun.dto.UrunResponseDto;
import com.uzay.urun.entity.Urun;
import com.uzay.urun.exception.UrunNotFoundException;
import com.uzay.urun.mapper.UrunMapper;
import com.uzay.urun.repository.UrunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrunService implements UrunServiceInterface {

    private final UrunRepository repository;
    private final UrunMapper urunMapper;


    @Override
    public UrunResponseDto getUrun(Integer id) {
        Urun urun = repository.findById(id).orElse(null);
        UrunResponseDto urunResponseDto = urunMapper.urunToResponse(urun);
        return urunResponseDto;

    }

    @Override
    public List<UrunResponseDto> getAllUrun() {
        List<Urun> urunler = repository.findAll();
        List<UrunResponseDto> list = urunler.stream().map(urun -> urunMapper.urunToResponse(urun)).toList();
        return list;
    }

    @Override
    public UrunResponseDto addUrun(UrunRequestDto urunRequestDto) {

        Urun urun = urunMapper.requestToUrun(urunRequestDto);
        Urun save = repository.save(urun);
        return urunMapper.urunToResponse(save);

    }

    @Override
    public Boolean deleteUrun(Integer id) {
        try {
            if (!repository.existsById(id)) {
                return false; // Eğer id bulunamazsa false döndür
            }
            repository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }

    @Override
    public UrunResponseDto updateUrun(Integer id, UrunRequestDto urunRequestDto) {
        Urun urun = repository.findById(id).orElseThrow(() -> new UrunNotFoundException("ürün bulunamadı"));
        urun.setAd(urunRequestDto.getAd());
        urun.setFiyat(urunRequestDto.getFiyat());
        urun.setStokMiktar(urunRequestDto.getStokMiktar());
        urun.setAciklama(urunRequestDto.getAciklama());
        urun.setKategori(urunRequestDto.getKategori());
        Urun save = repository.save(urun);
        UrunResponseDto urunResponseDto = urunMapper.urunToResponse(save);
        return urunResponseDto;
    }


}
