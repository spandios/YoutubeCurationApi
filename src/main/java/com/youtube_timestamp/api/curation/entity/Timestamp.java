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
public class Timestamp extends AbstractEntity implements Comparable<Timestamp> {
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

    @Override
    public int compareTo(Timestamp time2) {
        if(second != null && time2.getSecond() != null){
            double second1 = Double.parseDouble(this.second);
            double second2 = Double.parseDouble(time2.getSecond());
            if (second1 > second2) {
                return 1;
            } else if (second1 == second2) {
                return 0;
            } else {
                return -1;
            }
        }
        return 0;
    }
}
