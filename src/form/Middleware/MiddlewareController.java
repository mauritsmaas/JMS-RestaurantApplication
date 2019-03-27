package form.Middleware;

import javafx.event.ActionEvent;
import shared.kitchen.KitchenMenuReply;
import shared.kitchen.KitchenMenuRequest;
import shared.menu.MenuReply;
import shared.menu.MenuRequest;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class MiddlewareController {

    private WaiterAppGateway waiterAppGateway;
    private KitchenAppGateway kitchenAppGateway;
    private Map<String, MenuRequest> menuRequests;

    private DefaultListModel<JListLine> listModel = new DefaultListModel<>();
    private JList<JListLine> list = new JList<>(listModel);

    public void initialize(){
        waiterAppGateway = new WaiterAppGateway(this);
        kitchenAppGateway = new KitchenAppGateway(this);
        menuRequests = new HashMap<>();

    }

    public void btnRefreshAction(ActionEvent actionEvent) {

    }

    public void add(MenuRequest menuRequest, String correlationId) {
        menuRequests.put(correlationId, menuRequest);
        listModel.addElement(new JListLine(menuRequest));
        KitchenMenuRequest kitchenMenuRequest = new KitchenMenuRequest(menuRequest.getMenu(), menuRequest.getAmount());
        kitchenAppGateway.sendKitchenRequest(kitchenMenuRequest, correlationId);
        this.add(menuRequest, kitchenMenuRequest);
    }

    public void add(String corrolationId, KitchenMenuReply kitchenMenuReply){
        JListLine rr = getLine(menuRequests.get(corrolationId));
        if (rr!= null && kitchenMenuReply != null){
            rr.setKitchenMenuReply(kitchenMenuReply);
            list.repaint();
        }
        waiterAppGateway.menuReply(new MenuReply(kitchenMenuReply.getRating(), kitchenMenuReply.getKitchenID()), corrolationId);
    }

    public void add(MenuRequest menuRequest, KitchenMenuRequest kitchenMenuRequest){
        JListLine rr = getLine(menuRequest);
        if (rr!= null && kitchenMenuRequest != null){
            rr.setKitchenMenuRequest(kitchenMenuRequest);
            list.repaint();
        }
    }

    private JListLine getLine(MenuRequest request){
        for (int i = 0; i < listModel.getSize(); i++){
            JListLine rr =listModel.get(i);
            if (rr.getMenuRequest() == request){
                return rr;
            }
        }
        return null;
    }

}
