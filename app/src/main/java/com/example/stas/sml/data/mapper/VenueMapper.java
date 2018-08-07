package com.example.stas.sml.data.mapper;



import com.example.stas.sml.data.model.venuedetailedmodel.Venue;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;

import javax.inject.Inject;

public class VenueMapper {

    @Inject
    public VenueMapper(){

    }

    public VenueEntity map(Venue from){
        VenueEntity venueEntity = new VenueEntity();
        venueEntity.setName(from.getName());
        venueEntity.setRating(from.getRating());
        venueEntity.setDescription(from.getDescription());
        venueEntity.setDistance(from.getDistance());
        venueEntity.setHours(from.getHours());
        venueEntity.setPage(from.getPage());
        venueEntity.setLocation(from.getLocation());
        return venueEntity;
    }
}
