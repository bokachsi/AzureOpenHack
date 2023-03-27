package org.example.functions;

import java.time.Instant;

public class RatingItem {
    public String id;
    public String productId;
    public String userId;
//    public Instant timestamp;
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

//    public Instant getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(Instant timestamp) {
//        this.timestamp = timestamp;
//    }

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
//                ", timestamp=" + timestamp +
                ", locationName='" + locationName + '\'' +
                ", rating=" + rating +
                ", userNotes='" + userNotes + '\'' +
                '}';
    }
}
