package com.oshi.ohsi_back.domain.tag.domain.entity;

import java.io.Serializable;

import com.oshi.ohsi_back.domain.kitchen.domain.entity.Space;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "space_tags")
public class SpaceTag {

    @EmbeddedId
    private SpaceTagId id = new SpaceTagId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("spaceId")
    private Space space;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    private Tag tag;

    // EmbeddedId class for composite key
    @Embeddable
    public static class SpaceTagId implements Serializable {

        private Long spaceId;
        private Long tagId;

        // Equals and hashCode methods
    }

    // Getters and setters
}