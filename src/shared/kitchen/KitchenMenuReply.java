package shared.kitchen;

import shared.IMessage;

public class KitchenMenuReply implements IMessage {

    private String kitchenID; // the unique kitchen Id
    private int rating;

    public KitchenMenuReply() {
        this.kitchenID = "";
        this.rating = 0;
    }

    public KitchenMenuReply(String kitchenID, int rating) {
        this.kitchenID = kitchenID;
        this.rating = rating;
    }

    public String getKitchenID() {
        return kitchenID;
    }

    public void setKitchenID(String kitchenID) {
        this.kitchenID = kitchenID;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public String toString() {
        return "kitchen=" + this.kitchenID + " rating=" + this.rating;
    }

    @Override
    public String getCommaSeperatedValue() {
        return kitchenID + "," + rating;
    }

    @Override
    public void fillFromCommaSeperatedValue(String value) {
        String[] array = value.split(",", 2);
        if (array.length != 2)
            throw new IllegalArgumentException();

        kitchenID = array[0];
        rating = Integer.parseInt(array[1]);
    }


}
