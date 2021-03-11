package com.youtube_timestamp.api.curation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youtube_timestamp.api.common.base.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "timestamp")
@Setter
@Getter
@NoArgsConstructor
public class Timestamp extends AbstractEntity {
    @Id
    @GeneratedValue()
    Long id;

    @Column
    String title;

    @Column
    String timestamp;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    Curation curation;

}
