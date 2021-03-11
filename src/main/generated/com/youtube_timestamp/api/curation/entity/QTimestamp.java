package com.youtube_timestamp.api.curation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTimestamp is a Querydsl query type for Timestamp
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTimestamp extends EntityPathBase<Timestamp> {

    private static final long serialVersionUID = -2019256433L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTimestamp timestamp1 = new QTimestamp("timestamp1");

    public final com.youtube_timestamp.api.common.base.QAbstractEntity _super = new com.youtube_timestamp.api.common.base.QAbstractEntity(this);

    //inherited
    public final BooleanPath active = _super.active;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QCuration curation;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath timestamp = createString("timestamp");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QTimestamp(String variable) {
        this(Timestamp.class, forVariable(variable), INITS);
    }

    public QTimestamp(Path<? extends Timestamp> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTimestamp(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTimestamp(PathMetadata metadata, PathInits inits) {
        this(Timestamp.class, metadata, inits);
    }

    public QTimestamp(Class<? extends Timestamp> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.curation = inits.isInitialized("curation") ? new QCuration(forProperty("curation"), inits.get("curation")) : null;
    }

}

