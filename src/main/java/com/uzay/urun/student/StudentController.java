package com.uzay.urun.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;
    private final ProfileRepository profileRepository;

    public StudentController(StudentRepository studentRepository, ProfileRepository profileRepository) {
        this.studentRepository = studentRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/student-ekle")
    public ResponseEntity<?> ekleStudent(@RequestBody Student student) {
        // Önce student'i kaydet
        Student savedStudent = studentRepository.save(student);

        // StudentProfile oluştur ve student ile ilişkilendir
        StudentProfile profile = new StudentProfile();
        profile.setBio(student.getStudentProfile().getBio());
        profile.setStudent(savedStudent);

        // Student'a profile'ı set et
        savedStudent.setStudentProfile(profile);

        // Son halini kaydet
        return ResponseEntity.ok(studentRepository.save(savedStudent));
    }
    @DeleteMapping("/student-sil/{id}")
    public ResponseEntity<?>deleteStudent(@PathVariable Integer id){
        if (!studentRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Öğrenci bulunamadı: " + id);
        }
        try {
            studentRepository.deleteById(id);
            return ResponseEntity
                    .ok()
                    .body("Öğrenci başarıyla silindi. ID: " + id);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Silme işlemi sırasında bir hata oluştu: " + e.getMessage());
        }
    }
    @GetMapping("/get-student/{studentId}")
    public ResponseEntity<?> getStudent(@PathVariable Integer studentId) {
        if (!studentRepository.existsById(studentId)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Öğrenci bulunamadı: " + studentId);
        }
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        else {
           return  ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Silme işlemi sırasında bir hata oluştu: " );
        }
    }

    @GetMapping("/get-all-student")
    public ResponseEntity<?> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/update-student/{studentId}")
    public ResponseEntity<?> updateStudent(@RequestBody Student student,Integer studentId) {
        if (!studentRepository.existsById(studentId)) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("bulunamadı");
        }
        Student studentKayitli = studentRepository.findById(studentId).get();
        studentKayitli.setName(student.getName());
        studentKayitli.setLastName(student.getLastName());
        studentRepository.save(studentKayitli);
        return ResponseEntity.ok("güncellend,");

    }

}
