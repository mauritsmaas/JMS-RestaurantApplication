package form.Middleware;

import shared.gateway.MessageSenderGateway;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String kitchenName;
    private List<String> menus;

    private MessageSenderGateway sender;

    public Rule(String kitchenName, List<String> menus) {
        this.kitchenName = kitchenName;
        this.menus = new ArrayList<>(menus);
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public MessageSenderGateway getSender() {
        return sender;
    }

    public void setSender(MessageSenderGateway sender) {
        this.sender = sender;
    }

     public boolean check(String menu) {
        if (menus.contains(menu)) {
            return true;
        }
        return false;
     }
}
