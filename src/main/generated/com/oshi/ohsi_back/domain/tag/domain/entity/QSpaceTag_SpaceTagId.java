package com.oshi.ohsi_back.domain.tag.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSpaceTag_SpaceTagId is a Querydsl query type for SpaceTagId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSpaceTag_SpaceTagId extends BeanPath<SpaceTag.SpaceTagId> {

    private static final long serialVersionUID = 1289912029L;

    public static final QSpaceTag_SpaceTagId spaceTagId = new QSpaceTag_SpaceTagId("spaceTagId");

    public final NumberPath<Long> spaceId = createNumber("spaceId", Long.class);

    public final NumberPath<Long> tagId = createNumber("tagId", Long.class);

    public QSpaceTag_SpaceTagId(String variable) {
        super(SpaceTag.SpaceTagId.class, forVariable(variable));
    }

    public QSpaceTag_SpaceTagId(Path<? extends SpaceTag.SpaceTagId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSpaceTag_SpaceTagId(PathMetadata metadata) {
        super(SpaceTag.SpaceTagId.class, metadata);
    }

}

