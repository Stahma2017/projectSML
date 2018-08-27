package com.example.stas.sml.presentation.feature.venueselected;


import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stas.sml.App;
import com.example.stas.sml.R;
import com.example.stas.sml.domain.entity.venuedetailedentity.Group;
import com.example.stas.sml.domain.entity.venuedetailedentity.GroupListed;
import com.example.stas.sml.domain.entity.venuedetailedentity.Item;
import com.example.stas.sml.domain.entity.venuedetailedentity.ItemListed;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.feature.map.MapsFragment;
import com.example.stas.sml.presentation.feature.venueselected.adapter.GalleryRecyclerAdapter;
import com.example.stas.sml.utils.UrlHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import static android.content.Context.MODE_PRIVATE;

public class VenueSelectedFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener,
VenueSelectContract.VenueSelectView{
    private Unbinder unbinder;
    @Inject
    VenueSelectedPresenter presenter;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;
    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    @BindView(R.id.main_toolbar) Toolbar mToolbar;
    @BindView(R.id.main_appbar)AppBarLayout mAppBarLayout;
    @BindView(R.id.image_main) ImageView imageViewMain;
    @BindView(R.id.circleImage) CircleImageView circleImageView;
    @BindView(R.id.main_textview_title)TextView title;
    @BindView(R.id.descriptionSelected)TextView description;
    @BindView(R.id.addressSelected)TextView address;
    @BindView(R.id.workStatusSelected)TextView workStatus;
    @BindView(R.id.workIndicatorSelected)ImageView workIndicator;
    @BindView(R.id.number)TextView number;
    @BindView(R.id.twitter)TextView twitter;
    @BindView(R.id.kmSelected)TextView distanceTW;
    @BindView(R.id.placeRatingSelected)RatingBar rating;
    @BindView(R.id.galleryRecycler)RecyclerView galleryRecycler;
    @Inject
    GalleryRecyclerAdapter galleryRecyclerAdapter;

    public VenueSelectedFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_selected, container, false);
        App.getInstance().addVenueSelectComponent(this).injectVenueSelectedFragment(this);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        SharedPreferences prefs = getActivity().getSharedPreferences(MapsFragment.MY_PREFS, MODE_PRIVATE);
        String venueId = prefs.getString("venueSelect", "none");

        if (!venueId.equals("none")){
            presenter.getLocationForVenueDetailed(venueId);
        }
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        galleryRecycler.setLayoutManager(horizontalLayoutManager);
        galleryRecycler.setAdapter(galleryRecyclerAdapter);
        return view;
    }

    @Override
    public void deliverLocationForpreveious(Location location, String venueId) {
        presenter.getVenuesWithCategory(venueId, location);
    }

    @Override
    public void showVenueSelected(VenueEntity venue, Location location) {
        presenter.saveVenueToDb(venue);
        List<String> galleryUrls = new ArrayList<>();
        if (venue.getListed().getGroups()!= null) {
            for (GroupListed groupListed : venue.getListed().getGroups()) {
                if (groupListed.getItems() != null) {
                    for (ItemListed itemListed : groupListed.getItems()) {
                        if (itemListed.getPhoto().getPrefix() != null) {
                            galleryUrls.add(UrlHelper.getUrlToPhoto(itemListed.getPhoto().getPrefix(), itemListed.getPhoto().getSuffix()));
                            com.example.stas.sml.GlideApp.with(imageViewMain)
                                    .load(UrlHelper.getUrlToPhoto(itemListed.getPhoto().getPrefix(), itemListed.getPhoto().getSuffix()))
                                    .placeholder(R.drawable.no_image)
                                    .into(imageViewMain);
                        }
                    }
                }
            }
        }
                if (venue.getPhotos().getGroups() != null){
                    for (Group group :venue.getPhotos().getGroups()) {
                        if (group.getItems() != null){
                            for (Item item:group.getItems()) {
                                if(item.getPrefix() != null){
                                    galleryUrls.add(UrlHelper.getUrlToPhoto(item.getPrefix(), item.getSuffix()));
                                }
                            }
                        }
                    }
                }
        galleryRecyclerAdapter.setList(galleryUrls);
        galleryRecyclerAdapter.notifyDataSetChanged();

        rating.setRating((float)(venue.getRating()/2));
        double distance = presenter.distanceBetweenPoints(venue.getLocation().getLat(), venue.getLocation().getLng(), location.getLatitude(), location.getLongitude());
        distanceTW.setText(String.format(Locale.US,"%.1f км", (distance)));
        com.example.stas.sml.GlideApp.with(circleImageView)
                .load(UrlHelper.getUrlToPhoto(venue.getBestPhoto().getPrefix(), venue.getBestPhoto().getSuffix()))
                .placeholder(R.drawable.circle_no_image)
                .into(circleImageView);
        title.setText(venue.getName());
        description.setText(venue.getDescription());
        address.setText(venue.getLocation().getAddress());
        if (venue.getHours().getOpen())
        {
            workIndicator.setImageResource(R.drawable.work_indicator);
        }
        workStatus.setText(venue.getHours().getStatus());
        if (venue.getContact().getPhone() != null){
            number.setText(venue.getContact().getPhone());
        }else {
            number.setText("-");
        }
        if (venue.getContact().getTwitter() != null){
            twitter.setText(venue.getContact().getTwitter());
        }else {
            twitter.setText("-");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        App.getInstance().clearVenueSelectComponent();
        presenter.detachView();
    }

    @Override
    public void onStop() {
        super.onStop();
        App.getInstance().clearVenueSelectComponent();
        presenter.detachView();
    }

    @Override
    public void showSuccess(){
        Toast.makeText(getContext(), "Venue added to db", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(i) / (float) maxScroll;
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(title, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(title, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void showError(String errorMessage) {

    }
}
