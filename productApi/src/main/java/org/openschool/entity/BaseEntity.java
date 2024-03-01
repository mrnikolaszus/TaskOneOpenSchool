package org.openschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_created")
    private LocalDateTime timeCreated;

    @Column(name = "time_updated")
    private LocalDateTime timeUpdated;

    @PrePersist
    public void setTimeCreated() {
        timeCreated = LocalDateTime.now();
    }

    @PreUpdate
    public void setTimeUpdated() {
        timeUpdated = LocalDateTime.now();
    }
}