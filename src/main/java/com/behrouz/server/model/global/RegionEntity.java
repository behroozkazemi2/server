package com.behrouz.server.model.global;


import com.behrouz.server.model.base.BaseIdNameEntity;
import com.behrouz.server.model.account.ProviderEntity;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "regional", schema = "public")
public class RegionEntity extends BaseIdNameEntity {

    private Geometry location;
    private String description;

    @OneToMany(mappedBy = "region")
    private Set<ProviderEntity> providerLocationRegion;


    public RegionEntity() {
    }

    public RegionEntity(double lat , double lang) {
        this.location =
                new GeometryFactory()
                        .createPoint(
                                new Coordinate(
                                        lang,
                                        lat
                                )
                        );

    }

    @Column(name = "location", columnDefinition = "Geometry")
    public Geometry getLocation() {
        return location;
    }
    public void setLocation(Geometry location) {
        location.setSRID(4326);
        this.location = location;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
