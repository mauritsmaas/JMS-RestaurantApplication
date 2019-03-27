package shared.menu;

import shared.IMessage;

public class MenuReply implements IMessage {

    private int rating; // the rating of menu
    private String kitchenID; // the unique kitchen identification

    public MenuReply() {
        super();
        this.rating = 0;
        this.kitchenID = "";
    }
    public MenuReply(int rating, String kitchenID) {
        super();
        this.rating = rating;
        this.kitchenID = kitchenID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getKitchenID() {
        return kitchenID;
    }

    public void setKitchenID(String kitchenID) {
        this.kitchenID = kitchenID;
    }

    @Override
    public String toString(){
        return " rating="+String.valueOf(rating) + " kitchenID="+String.valueOf(kitchenID);
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
