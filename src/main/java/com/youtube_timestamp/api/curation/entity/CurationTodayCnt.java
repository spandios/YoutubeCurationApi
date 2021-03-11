package com.youtube_timestamp.api.curation.entity;

import com.youtube_timestamp.api.common.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class CurationTodayCnt extends AbstractEntity {

    @Id
    @GeneratedValue
    Long id;

    @Column(columnDefinition = "INT(11) default 1")
    int cnt;

    @Column
    LocalDate today;

    @ManyToOne(fetch = FetchType.LAZY)
    Curation curation;

    public void plusCnt(){
        this.cnt = this.cnt+1;
    }
}
