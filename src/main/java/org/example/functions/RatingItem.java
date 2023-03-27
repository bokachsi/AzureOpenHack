package org.example.functions;

import java.util.Date;

public class RatingItem {
    public String id;
    public String productId;
    public String userId;
    public Date timestamp;
    public String locationName;
    public int rating;
    public String userNotes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    @Override
    public String toString() {
        return "RatingItem{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", userId='" + userId + '\'' +
                ", timestamp=" + timestamp +
                ", locationName='" + locationName + '\'' +
                ", rating=" + rating +
                ", userNotes='" + userNotes + '\'' +
                '}';
    }

    public static class RatingItemBuilder
    {
        public String id;
        public String productId;
        public String userId;
        public String locationName;
        public int rating;
        public String userNotes;
        public static RatingItemBuilder getInstance()
        {
            return new RatingItemBuilder();
        }

        public RatingItemBuilder withId(String id)
        {
            this.id = id;
            return this;
        }

        public RatingItemBuilder withProductId(String productId)
        {
            this.productId = productId;
            return this;
        }

        public RatingItemBuilder withUserId(String userId)
        {
            this.userId = userId;
            return this;
        }

        public RatingItemBuilder withLocationName(String locationName)
        {
            this.locationName = locationName;
            return this;
        }

        public RatingItemBuilder withRating(String rating)
        {
            try {
                this.rating = Integer.parseInt(rating);
            } catch (Exception ignore) {}
            return this;
        }

        public RatingItemBuilder withUserNotes(String userNotes)
        {
            this.userNotes = userNotes;
            return this;
        }

        public RatingItem build(){
            RatingItem item = new RatingItem();

            item.setId(id);
            item.setProductId(productId);
            item.setUserId(userId);
            item.setRating(rating);
            item.setLocationName(locationName);
            item.setUserNotes(userNotes);
            item.setTimestamp(new Date());
            return item;
        }

    }

}
