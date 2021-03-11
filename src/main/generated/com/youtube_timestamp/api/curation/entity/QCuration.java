package com.youtube_timestamp.api.curation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCuration is a Querydsl query type for Curation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCuration extends EntityPathBase<Curation> {

    private static final long serialVersionUID = -753696996L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCuration curation = new QCuration("curation");

    public final com.youtube_timestamp.api.common.base.QAbstractEntity _super = new com.youtube_timestamp.api.common.base.QAbstractEntity(this);

    //inherited
    public final BooleanPath active = _super.active;

    public final NumberPath<Integer> commentCnt = createNumber("commentCnt", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath is_private = createBoolean("is_private");

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    public final ListPath<Timestamp, QTimestamp> timestamp = this.<Timestamp, QTimestamp>createList("timestamp", Timestamp.class, QTimestamp.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public final com.youtube_timestamp.api.user.entity.QUserEntity user;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final NumberPath<Integer> viewCnt = createNumber("viewCnt", Integer.class);

    public final StringPath youtubeUrl = createString("youtubeUrl");

    public QCuration(String variable) {
        this(Curation.class, forVariable(variable), INITS);
    }

    public QCuration(Path<? extends Curation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCuration(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCuration(PathMetadata metadata, PathInits inits) {
        this(Curation.class, metadata, inits);
    }

    public QCuration(Class<? extends Curation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.youtube_timestamp.api.user.entity.QUserEntity(forProperty("user")) : null;
    }

}

