package form.Middleware;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import shared.kitchen.KitchenMenuReply;
import shared.kitchen.KitchenMenuRequest;
import shared.menu.MenuReply;
import shared.menu.MenuRequest;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class MiddlewareController {

    @FXML
    private ListView lvRequest;

    private WaiterAppGateway waiterAppGateway;
    private KitchenAppGateway kitchenAppGateway;
    private Map<String, MenuRequest> menuRequests;

    private ObservableList<JListLine> listModel;
    //private JList<JListLine> list = new JList<>(listModel);

    public void initialize(){
        listModel = FXCollections.observableArrayList();
        waiterAppGateway = new WaiterAppGateway(this);
        kitchenAppGateway = new KitchenAppGateway(this);
        menuRequests = new HashMap<>();

    }

    public void btnRefreshAction(ActionEvent actionEvent) {
        lvRequest.refresh();
    }

    public void add(final MenuRequest menuRequest, String correlationId) {
        menuRequests.put(correlationId, menuRequest);
        Platform.runLater(new Runnable() {
            @Override public void run() {
                listModel.add(new JListLine(menuRequest));
            }
        });
        KitchenMenuRequest kitchenMenuRequest = new KitchenMenuRequest(menuRequest.getMenu(), menuRequest.getAmount());
        kitchenAppGateway.sendKitchenRequest(kitchenMenuRequest, correlationId);
        this.add(menuRequest, kitchenMenuRequest);
    }

    public void add(String corrolationId, KitchenMenuReply kitchenMenuReply){
        JListLine rr = getLine(menuRequests.get(corrolationId));
        if (rr!= null && kitchenMenuReply != null){
            rr.setKitchenMenuReply(kitchenMenuReply);
            lvRequest.setItems(listModel);
            lvRequest.refresh();
        }
        waiterAppGateway.menuReply(new MenuReply(kitchenMenuReply.getRating(), kitchenMenuReply.getKitchenID()), corrolationId);
    }

    public void add(MenuRequest menuRequest, KitchenMenuRequest kitchenMenuRequest){
        JListLine rr = getLine(menuRequest);
        if (rr!= null && kitchenMenuRequest != null){
            rr.setKitchenMenuRequest(kitchenMenuRequest);
            lvRequest.setItems(listModel);
            lvRequest.refresh();
        }
    }

    private JListLine getLine(MenuRequest request){
        for (int i = 0; i < listModel.size(); i++){
            JListLine rr =listModel.get(i);
            if (rr.getMenuRequest() == request){
                return rr;
            }
        }
        return null;
    }

}
