package com.example.stas.sml.data.mapper;

import com.example.stas.sml.data.model.venuedetailedmodel.Group;
import com.example.stas.sml.data.model.venuedetailedmodel.GroupListed;
import com.example.stas.sml.data.model.venuedetailedmodel.Item;
import com.example.stas.sml.data.model.venuedetailedmodel.ItemListed;
import com.example.stas.sml.data.model.venuedetailedmodel.PageInfo;
import com.example.stas.sml.data.model.venuedetailedmodel.Photo;
import com.example.stas.sml.data.model.venuedetailedmodel.PhotoListed;
import com.example.stas.sml.data.model.venuedetailedmodel.Venue;
import com.example.stas.sml.domain.entity.venuedetailedentity.BestPhoto;
import com.example.stas.sml.domain.entity.venuedetailedentity.Contact;
import com.example.stas.sml.domain.entity.venuedetailedentity.Hours;
import com.example.stas.sml.domain.entity.venuedetailedentity.Location;
import com.example.stas.sml.domain.entity.venuedetailedentity.Page;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;

import java.util.ArrayList;
import java.util.List;

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
        // to implement map in a appropriate way
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

        venueEntity.setBestPhoto(new BestPhoto());
        venueEntity.getBestPhoto().setPrefix(from.getBestPhoto().getPrefix());
        venueEntity.getBestPhoto().setSuffix(from.getBestPhoto().getSuffix());


        List<com.example.stas.sml.domain.entity.venuedetailedentity.Group> groupsEntity = new ArrayList<>();
        for (Group group :from.getPhotos().getGroups()) {
            List<com.example.stas.sml.domain.entity.venuedetailedentity.Item> itemsEntity = new ArrayList<>();
            for (Item item: group.getItems()) {
                com.example.stas.sml.domain.entity.venuedetailedentity.Item itemEntity = new com.example.stas.sml.domain.entity.venuedetailedentity.Item();
                itemEntity.setPrefix(item.getPrefix());
                itemEntity.setSuffix(item.getSuffix());
                itemsEntity.add(itemEntity);
            }
            com.example.stas.sml.domain.entity.venuedetailedentity.Group groupEntity = new com.example.stas.sml.domain.entity.venuedetailedentity.Group();
            groupEntity.setItems(itemsEntity);
            groupsEntity.add(groupEntity);
        }
        venueEntity.getPhotos().setGroups(groupsEntity);
        venueEntity.getPhotos().setCount(from.getPhotos().getCount());


        List<com.example.stas.sml.domain.entity.venuedetailedentity.GroupListed> groupsListedEntity = new ArrayList<>();
        for (GroupListed groupListed: from.getListed().getGroups()) {
            List<com.example.stas.sml.domain.entity.venuedetailedentity.ItemListed> itemsListedEntity = new ArrayList<>();
            for (ItemListed itemListed: groupListed.getItems()) {
                com.example.stas.sml.domain.entity.venuedetailedentity.ItemListed itemListedEntity = new com.example.stas.sml.domain.entity.venuedetailedentity.ItemListed();
                com.example.stas.sml.domain.entity.venuedetailedentity.PhotoListed photoListedEntity = new com.example.stas.sml.domain.entity.venuedetailedentity.PhotoListed();
                photoListedEntity.setPrefix(itemListed.getPhoto().getPrefix());
                photoListedEntity.setSuffix(itemListed.getPhoto().getSuffix());
                itemListedEntity.setPhoto(photoListedEntity);
                itemsListedEntity.add(itemListedEntity);
            }
            com.example.stas.sml.domain.entity.venuedetailedentity.GroupListed groupListedEntity = new com.example.stas.sml.domain.entity.venuedetailedentity.GroupListed();
            groupListedEntity.setItems(itemsListedEntity);
            groupsListedEntity.add(groupListedEntity);
        }
        venueEntity.getListed().setGroups(groupsListedEntity);

        return venueEntity;
    }
}
