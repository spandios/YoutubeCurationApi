package com.youtube_timestamp.api.curation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCurationTodayCnt is a Querydsl query type for CurationTodayCnt
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCurationTodayCnt extends EntityPathBase<CurationTodayCnt> {

    private static final long serialVersionUID = -259351132L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCurationTodayCnt curationTodayCnt = new QCurationTodayCnt("curationTodayCnt");

    public final com.youtube_timestamp.api.common.base.QAbstractEntity _super = new com.youtube_timestamp.api.common.base.QAbstractEntity(this);

    //inherited
    public final BooleanPath active = _super.active;

    public final NumberPath<Integer> cnt = createNumber("cnt", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QCuration curation;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> today = createDate("today", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCurationTodayCnt(String variable) {
        this(CurationTodayCnt.class, forVariable(variable), INITS);
    }

    public QCurationTodayCnt(Path<? extends CurationTodayCnt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCurationTodayCnt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCurationTodayCnt(PathMetadata metadata, PathInits inits) {
        this(CurationTodayCnt.class, metadata, inits);
    }

    public QCurationTodayCnt(Class<? extends CurationTodayCnt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.curation = inits.isInitialized("curation") ? new QCuration(forProperty("curation"), inits.get("curation")) : null;
    }

}

