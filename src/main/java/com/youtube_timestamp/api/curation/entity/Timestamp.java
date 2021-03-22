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

    @Column
    String second;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    Curation curation;


    public Timestamp(String title, String timestamp, String second, Curation curation) {
        this.title = title;
        this.timestamp = timestamp;
        this.second = second;
        this.curation = curation;
    }
}
