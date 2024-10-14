package com.oshi.ohsi_back.domain.tag.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSpaceTag is a Querydsl query type for SpaceTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpaceTag extends EntityPathBase<SpaceTag> {

    private static final long serialVersionUID = -441398048L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSpaceTag spaceTag = new QSpaceTag("spaceTag");

    public final QSpaceTag_SpaceTagId id;

    public final com.oshi.ohsi_back.domain.kitchen.domain.entity.QSpace space;

    public final QTag tag;

    public QSpaceTag(String variable) {
        this(SpaceTag.class, forVariable(variable), INITS);
    }

    public QSpaceTag(Path<? extends SpaceTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSpaceTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSpaceTag(PathMetadata metadata, PathInits inits) {
        this(SpaceTag.class, metadata, inits);
    }

    public QSpaceTag(Class<? extends SpaceTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QSpaceTag_SpaceTagId(forProperty("id")) : null;
        this.space = inits.isInitialized("space") ? new com.oshi.ohsi_back.domain.kitchen.domain.entity.QSpace(forProperty("space"), inits.get("space")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

