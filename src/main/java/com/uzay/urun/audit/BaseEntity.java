package com.uzay.urun.audit;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @Column(updatable = false)
    @CreatedBy
    private String created_by;

    @Column(insertable = false)
    @LastModifiedBy
    private String updated_by;

    @Column( updatable = false)
    @CreatedDate
    private LocalDateTime created_at;

    @Column(insertable = false)
    @LastModifiedDate
    private LocalDateTime updated_at;




}
