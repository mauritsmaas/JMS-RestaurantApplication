package shared.menu;

import shared.IMessage;

public class MenuRequest implements IMessage {
    private int ssn; // unique client number.
    private String menu; // menu
    private int amount; // the amount to borrow

    public MenuRequest() {
        super();
        this.ssn = 0;
        this.menu = "";
        this.amount = 0;
    }

    public MenuRequest(int ssn, String menu, int amount) {
        super();
        this.ssn = ssn;
        this.amount = amount;
        this.menu = menu;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "ssn=" + String.valueOf(ssn) + " menu=" + menu+ " amount=" + String.valueOf(amount);
    }

    @Override
    public String getCommaSeperatedValue() {
        return ssn + "," + menu + "," + amount;
    }

    @Override
    public void fillFromCommaSeperatedValue(String value) {
        String[] array = value.split(",");
        if (array.length != 3)
            throw new IllegalArgumentException();

        ssn = Integer.parseInt(array[0]);
        menu = array[1];
        amount = Integer.parseInt(array[2]);

    }
}
