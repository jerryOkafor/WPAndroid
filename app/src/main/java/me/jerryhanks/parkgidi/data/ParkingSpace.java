package me.jerryhanks.parkgidi.data;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import me.jerryhanks.parkgidi.data.model.Location;

/**
 * Created by Potencio on 5/4/2017. @ 8:17 PM
 * For ParkGidi
 */

public class ParkingSpace {
    private String apartmentName;
    private String sqNumber;
    private Location location;
    private String owner;
    private String key;


    private ParkingSpace() {

    }

    private ParkingSpace(String apartmentName, String sqNumber, Location location, String owner) {
        this.apartmentName = apartmentName;
        this.sqNumber = sqNumber;
        this.location = location;
        this.owner = owner;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public String getSqNumber() {
        return sqNumber;
    }

    public Location getLocation() {
        return location;
    }

    public String getOwner() {
        return owner;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Parking Space")
                .add("ApartmentName", apartmentName)
                .add("SQnumber", sqNumber)
                .add("Location", location)
                .add("Owner", owner)
                .toString();
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(apartmentName, sqNumber, owner);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ParkingSpace space = (ParkingSpace) obj;
        return Objects.equal(apartmentName, space.getApartmentName())
                && Objects.equal(sqNumber, space.getSqNumber())
                && Objects.equal(owner, space.getOwner());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {


        private Location mLoc;
        private String mApartName;
        private String mSQNumber;
        private String mOwner;

        public Builder withLocation(Location location) {
            mLoc = location;
            return this;
        }

        public Builder setApartmrntName(String name) {
            mApartName = name;
            return this;
        }

        public Builder setSQNumber(String sqNumber) {
            mSQNumber = sqNumber;
            return this;
        }

        public Builder setOwner(String uuid) {
            mOwner = uuid;
            return this;
        }

        public ParkingSpace build() {
            return new ParkingSpace(mApartName, mSQNumber, mLoc, mOwner);
        }

    }
}
