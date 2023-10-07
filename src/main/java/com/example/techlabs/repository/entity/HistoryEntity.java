package com.example.techlabs.repository.entity;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@RevisionEntity
@Table(name = "rev_info")
public class HistoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "revision_id")
    private Long id;

    @RevisionTimestamp
    @Column(name = "revision_timestamp")
    private Date timestamp;
}
