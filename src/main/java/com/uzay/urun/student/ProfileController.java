package com.uzay.urun.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ProfileController {
    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    //!ekleme metotu olmayacak zaten öğrneci eklendiğinde ekleniyor

    @GetMapping("/get-profiles")
    public ResponseEntity<?> getProfiles() {
        List<StudentProfile> all = profileRepository.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("hiç profile bulunamadı");
        }
        return ResponseEntity.ok(all);
    }

    @GetMapping("/get-profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable int id) {
        StudentProfile studentProfile = profileRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(studentProfile);
    }

    @DeleteMapping("/delete/profile/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable int id) {
        if (!profileRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("hiç profile bulunamadı");
        }
        profileRepository.deleteById(id);
        return ResponseEntity.ok().body("başarıyla silindi");
    }

    @PutMapping("/update/profile/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer id, @RequestBody StudentProfile studentProfile) {
        if (!profileRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("hiç profile bulunamadı");
        }
        StudentProfile studentKayitli = profileRepository.findById(id).get();
        studentKayitli.setBio(studentProfile.getBio());
        profileRepository.save(studentKayitli);
        return ResponseEntity.ok(studentKayitli);

    }






}
