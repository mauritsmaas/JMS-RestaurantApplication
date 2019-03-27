package shared.kitchen;

import shared.IMessage;

public class KitchenMenuRequest implements IMessage {

    private String menu; // the requested menu
    private int amount; // the requested amount

    public KitchenMenuRequest() {
        super();
        this.amount = 0;
        this.menu = "";
    }

    public KitchenMenuRequest(String menu, int amount) {
        super();
        this.amount = amount;
        this.menu = menu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return " menu=" +  menu+ " amount=" + String.valueOf(amount);
    }

    @Override
    public String getCommaSeperatedValue() {
        return menu + "," + amount;
    }

    @Override
    public void fillFromCommaSeperatedValue(String value) {
        String[] array = value.split(",");
        if (array.length != 2)
            throw new IllegalArgumentException();

        menu = array[0];
        amount = Integer.parseInt(array[1]);
    }

}
