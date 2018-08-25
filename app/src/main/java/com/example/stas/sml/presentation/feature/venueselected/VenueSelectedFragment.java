package com.example.stas.sml.presentation.feature.venueselected;


import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.stas.sml.App;
import com.example.stas.sml.R;
import com.example.stas.sml.domain.entity.venuedetailedentity.VenueEntity;
import com.example.stas.sml.presentation.feature.map.MapsFragment;

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
        Log.d("PREF", venueId);
        if (!venueId.equals("none")){
            presenter.getLocationForVenueDetailed(venueId);
        }
        return view;
    }

    @Override
    public void deliverLocationForpreveious(Location location, String venueId) {
        presenter.getVenuesWithCategory(venueId, location);
    }

    @Override
    public void showVenueSelected(VenueEntity venue, Location location) {
        //TODO set bold font according design
        //TODO add gallery
        //TODO find rounded placeholder for avatar image
        //TODO set rating

        double distance = presenter.distanceBetweenPoints(venue.getLocation().getLat(), venue.getLocation().getLng(), location.getLatitude(), location.getLongitude());
        distanceTW.setText(String.format(Locale.US,"%.1f км", (distance)));

        com.example.stas.sml.GlideApp.with(circleImageView)
                .load(venue.getPage().getPageInfo().getBanner())
                .placeholder(R.drawable.ic_image_placeholder_24dp)
                .into(circleImageView);

        com.example.stas.sml.GlideApp.with(imageViewMain)
                .load(venue.getPage().getPageInfo().getBanner())
                .placeholder(R.drawable.placeholder_main)
                .into(imageViewMain);

        title.setText(venue.getName());
        description.setText(venue.getDescription());
        address.setText(venue.getLocation().getAddress());
        if (venue.getHours().getOpen())
        {
            workIndicator.setImageResource(R.drawable.work_indicator);
        }
        workStatus.setText(venue.getHours().getStatus());
        number.setText(venue.getContact().getPhone());
        twitter.setText(venue.getContact().getTwitter());
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
