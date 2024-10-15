package com.oshi.ohsi_back.domain.kitchen.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSpace is a Querydsl query type for Space
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpace extends EntityPathBase<Space> {

    private static final long serialVersionUID = -2037311772L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSpace space = new QSpace("space");

    public final NumberPath<Integer> capacity = createNumber("capacity", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final TimePath<java.time.LocalTime> endTime = createTime("endTime", java.time.LocalTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.oshi.ohsi_back.domain.image.domain.entity.QImageEntity image;

    public final StringPath location = createString("location");

    public final StringPath name = createString("name");

    public final com.oshi.ohsi_back.domain.user.domain.entitiy.QUser owner;

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public final com.oshi.ohsi_back.domain.reservation.domain.entity.QReservation reservations;

    public final ListPath<com.oshi.ohsi_back.domain.tag.domain.entity.SpaceTag, com.oshi.ohsi_back.domain.tag.domain.entity.QSpaceTag> spaceTags = this.<com.oshi.ohsi_back.domain.tag.domain.entity.SpaceTag, com.oshi.ohsi_back.domain.tag.domain.entity.QSpaceTag>createList("spaceTags", com.oshi.ohsi_back.domain.tag.domain.entity.SpaceTag.class, com.oshi.ohsi_back.domain.tag.domain.entity.QSpaceTag.class, PathInits.DIRECT2);

    public final TimePath<java.time.LocalTime> startTime = createTime("startTime", java.time.LocalTime.class);

    public QSpace(String variable) {
        this(Space.class, forVariable(variable), INITS);
    }

    public QSpace(Path<? extends Space> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSpace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSpace(PathMetadata metadata, PathInits inits) {
        this(Space.class, metadata, inits);
    }

    public QSpace(Class<? extends Space> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.image = inits.isInitialized("image") ? new com.oshi.ohsi_back.domain.image.domain.entity.QImageEntity(forProperty("image")) : null;
        this.owner = inits.isInitialized("owner") ? new com.oshi.ohsi_back.domain.user.domain.entitiy.QUser(forProperty("owner")) : null;
        this.reservations = inits.isInitialized("reservations") ? new com.oshi.ohsi_back.domain.reservation.domain.entity.QReservation(forProperty("reservations"), inits.get("reservations")) : null;
    }

}

