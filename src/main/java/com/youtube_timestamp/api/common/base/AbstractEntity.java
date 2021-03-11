package com.youtube_timestamp.api.common.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {

    @Getter
    @Setter
    @Column(columnDefinition = "boolean default 1")
    public Boolean active = true;

    // 생성한 날짜
    @Getter
    @Setter
    @CreatedDate
    protected LocalDateTime createdAt;

    // 업데이트한 날짜
    @JsonIgnore
    @Getter
    @LastModifiedDate
    @Setter
    protected LocalDateTime updatedAt;

    // 업데이트한 사용자 아이디
    @JsonIgnore
    @LastModifiedBy
    protected String updatedBy;

    @Getter
    @Version
    @Column(columnDefinition = "int(11) default 0")
    @JsonIgnore
    protected int version;


}
