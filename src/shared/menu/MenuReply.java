package shared.menu;

import shared.IMessage;

public class MenuReply implements IMessage {

    private boolean ready; // the status of menu
    private String kitchenID; // the unique kitchen identification

    public MenuReply() {
        super();
        this.ready = false;
        this.kitchenID = "";
    }
    public MenuReply(boolean ready, String kitchenID) {
        super();
        this.ready = ready;
        this.kitchenID = kitchenID;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getKitchenID() {
        return kitchenID;
    }

    public void setKitchenID(String kitchenID) {
        this.kitchenID = kitchenID;
    }

    @Override
    public String toString(){
        return " ready="+String.valueOf(ready) + " kitchenID="+String.valueOf(kitchenID);
    }

    @Override
    public String getCommaSeperatedValue() {
        return kitchenID + "," + ready;
    }

    @Override
    public void fillFromCommaSeperatedValue(String value) {
        String[] array = value.split(",", 2);
        if (array.length != 2)
            throw new IllegalArgumentException();

        kitchenID = array[0];
        ready = Boolean.parseBoolean(array[1]);
    }
}
