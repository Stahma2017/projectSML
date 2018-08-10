package com.example.stas.sml.data.mapper;



import com.example.stas.sml.data.model.venuedetailedmodel.PageInfo;
import com.example.stas.sml.data.model.venuedetailedmodel.Venue;
import com.example.stas.sml.domain.entity.venuedetailedentity.Hours;
import com.example.stas.sml.domain.entity.venuedetailedentity.Location;
import com.example.stas.sml.domain.entity.venuedetailedentity.Page;
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
        // to realize map in appropriate way )
        venueEntity.setHours(new Hours());
        venueEntity.getHours().setStatus(from.getHours().getStatus());

        venueEntity.setPage(new Page());
        venueEntity.getPage().setPageInfo(new PageInfo());
        venueEntity.getPage().getPageInfo().setBanner(from.getPage().getPageInfo().getBanner());

        venueEntity.setLocation(new Location());
        venueEntity.getLocation().setAddress(from.getLocation().getAddress());

        return venueEntity;
    }
}
