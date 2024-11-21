package com.uzay.urun.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<StudentProfile, Integer> {
}
