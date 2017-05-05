package me.jerryhanks.parkgidi.data.model;

import android.support.annotation.NonNull;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Potencio on 5/5/2017. @ 7:27 AM
 * For ParkGidi
 */

public class Location {
    private String address;
    private String attribution;
    private String id;
    private LatLng latLng;
    private Locale locale;
    private String name;
    private String phoneNumber;
    private List<Integer> placeType;
    private int priceLevel;
    private float rating;
    private LatLngBounds viewport;
    private String webSiteUri;


    private Location() {
    }

    public Location(String address, String attribution, String id, LatLng latLng, Locale locale, String name, String phoneNumber, List<Integer> placeTypes, int priceLevel, float rating, LatLngBounds viewPorts, String uri) {
        this.address = address;
        this.attribution = attribution;
        this.id = id;
        this.latLng = latLng;
        this.locale = locale;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.placeType = placeTypes;
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.viewport = viewPorts;
        webSiteUri = uri;
    }


    public String getAddress() {
        return address;
    }

    public String getAttribution() {
        return attribution;
    }

    public String getId() {
        return id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Integer> getPlaceType() {
        return placeType;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public float getRating() {
        return rating;
    }

    public LatLngBounds getViewport() {
        return viewport;
    }

    public String getWebSiteUri() {
        return webSiteUri;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Location")
                .add("Id", id)
                .add("Address", address)
                .add("Phone", phoneNumber)
                .add("Attribution", attribution)
                .add("Name", name)
                .add("LatLng", latLng)
                .add("Viewport", viewport)
                .add("PlaceType", placeType)
                .add("Price Level", priceLevel)
                .add("Web url", webSiteUri)
                .add("Local", locale)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location place = (Location) obj;
        return Objects.equal(id, place.getId());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String mName;
        private String mAddress;
        private String mId;
        private String mPhoneNumber;
        private String mAttribution;
        private LatLngBounds mViewport;
        private LatLng mLatLng;
        private String mUri;
        private List<Integer> mTypes;
        private float mRating;
        private int mPriceLevel;
        private Locale mLocal;

        public Builder withId(@NonNull String id) {
            this.mId = id;
            return this;
        }

        public Builder withName(String name) {
            this.mName = name;
            return this;
        }

        public Builder withAddress(String address) {
            this.mAddress = address;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.mPhoneNumber = phoneNumber;
            return this;
        }

        public Builder setAttribution(String attr) {
            this.mAttribution = attr;
            return this;
        }

        public Builder setLatLng(LatLng latLng) {
            this.mLatLng = latLng;
            return this;
        }

        public Builder setViewport(LatLngBounds viewport) {
            this.mViewport = viewport;
            return this;
        }

        public Builder setWebSiteUri(String uri) {
            this.mUri = uri;
            return this;
        }

        public Builder setPlacetTypes(List<Integer> types) {
            this.mTypes = types;
            return this;
        }

        public Builder setRating(float rating) {
            this.mRating = rating;
            return this;
        }

        public Builder setPriceLevel(int level) {
            this.mPriceLevel = level;
            return this;
        }

        public Builder setLoacal(Locale loacal) {
            this.mLocal = loacal;
            return this;
        }


        public Location fromPlace(Place place) {

            return new Location(
                    place.getAddress() != null ? place.getAddress().toString() : "",
                    place.getAttributions() != null ? place.getAttributions().toString() : "",
                    place.getId(),
                    place.getLatLng(),
                    place.getLocale(),
                    place.getName() != null ? place.getName().toString() : "",
                    place.getPhoneNumber() != null ? place.getPhoneNumber().toString() : "",
                    place.getPlaceTypes(),
                    place.getPriceLevel(),
                    place.getRating(),
                    place.getViewport(),
                    place.getWebsiteUri() != null ? place.getWebsiteUri().toString() : "");
        }

        public Location build() {
            return new Location(
                    Optional.fromNullable(mAddress).or(""),
                    Optional.fromNullable(mAttribution).or(""),
                    Optional.fromNullable(mId).or(""),
                    Optional.fromNullable(mLatLng).or(new LatLng(0, 0)),
                    Optional.fromNullable(mLocal).or(Locale.getDefault()),
                    Optional.fromNullable(mName).or(""),
                    Optional.fromNullable(mPhoneNumber).or(""),
                    Optional.fromNullable(mTypes).or(new ArrayList<Integer>()),
                    Optional.fromNullable(mPriceLevel).or(0),
                    Optional.fromNullable(mRating).or(0f),
                    Optional.fromNullable(mViewport).or(new LatLngBounds(new LatLng(0, 0), new LatLng(0, 0))),
                    Optional.fromNullable(mUri).or(""));
        }
    }
}
