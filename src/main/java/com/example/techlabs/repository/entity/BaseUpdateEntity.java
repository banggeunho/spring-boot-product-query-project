package com.example.techlabs.repository.entity;

import com.example.techlabs.service.vo.command.ProductCommandVO;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseUpdateEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column
    private LocalDateTime lastModifiedAt;

    @LastModifiedBy
    @Column
    private String lastModifiedBy;

    @Comment("상품 삭제여부")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = this.createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        this.lastModifiedAt = LocalDateTime.now();
    }

    @PreRemove
    public void onDelete() {
        this.lastModifiedAt = LocalDateTime.now();
        this.isDeleted = true;
    }
}
