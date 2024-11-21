package com.uzay.urun.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="birey")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;
//!
//!User kaydedildiğinde Role'leri de kaydedilecek (User'daki CascadeType.ALL sayesinde)
@OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
@JsonManagedReference
private Set<Role> role;

}
