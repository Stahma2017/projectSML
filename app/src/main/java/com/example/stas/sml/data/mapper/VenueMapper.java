package com.example.stas.sml.data.mapper;

import com.example.stas.sml.domain.entity.VenueEntity;
import com.example.stas.sml.data.model.Venue;

public class VenueMapper {

    public VenueEntity map(Venue from){
        VenueEntity venueEntity = new VenueEntity();
        venueEntity.setPhotos(from.getPhotos());
        return venueEntity;
    }
}
