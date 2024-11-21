package com.uzay.urun.student;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String lastName;


    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private StudentProfile studentProfile;

}
