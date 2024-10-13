package com.oshi.ohsi_back.domain.image.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QImageEntity is a Querydsl query type for ImageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImageEntity extends EntityPathBase<ImageEntity> {

    private static final long serialVersionUID = -1643224653L;

    public static final QImageEntity imageEntity = new QImageEntity("imageEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> relatedId = createNumber("relatedId", Integer.class);

    public final EnumPath<com.oshi.ohsi_back.domain.image.domain.enums.ImageType> relatedType = createEnum("relatedType", com.oshi.ohsi_back.domain.image.domain.enums.ImageType.class);

    public final StringPath url = createString("url");

    public QImageEntity(String variable) {
        super(ImageEntity.class, forVariable(variable));
    }

    public QImageEntity(Path<? extends ImageEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImageEntity(PathMetadata metadata) {
        super(ImageEntity.class, metadata);
    }

}

