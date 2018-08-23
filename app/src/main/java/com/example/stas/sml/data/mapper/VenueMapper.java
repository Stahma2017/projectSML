package com.example.stas.sml.data.mapper;

import com.example.stas.sml.data.model.venuedetailedmodel.PageInfo;
import com.example.stas.sml.data.model.venuedetailedmodel.Venue;
import com.example.stas.sml.domain.entity.venuedetailedentity.Contact;
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
        venueEntity.setId(from.getId());
        // to realize map in a appropriate way
        venueEntity.setHours(new Hours());
        venueEntity.getHours().setStatus(from.getHours().getStatus());
        venueEntity.getHours().setOpen(from.getHours().getOpen());

        venueEntity.setPage(new Page());
        venueEntity.getPage().setPageInfo(new PageInfo());
        venueEntity.getPage().getPageInfo().setBanner(from.getPage().getPageInfo().getBanner());

        venueEntity.setLocation(new Location());
        venueEntity.getLocation().setAddress(from.getLocation().getAddress());
        venueEntity.getLocation().setLat(from.getLocation().getLat());
        venueEntity.getLocation().setLng(from.getLocation().getLng());

        venueEntity.setContact(new Contact());
        venueEntity.getContact().setPhone(from.getContact().getPhone());
        venueEntity.getContact().setTwitter(from.getContact().getTwitter());



        return venueEntity;
    }
}
