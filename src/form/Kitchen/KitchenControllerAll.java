package form.Kitchen;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import shared.kitchen.KitchenMenuReply;
import shared.kitchen.KitchenMenuRequest;
import shared.request.RequestReply;

public class KitchenControllerAll implements IKitchenController{

    @FXML
    private TextField tfRating;
    @FXML
    private ListView listKitchen;

    private String kitchenName = "Alles";
    private MiddlewareAppGateway gateway;
    private ObservableList<RequestReply<KitchenMenuRequest, KitchenMenuReply>> listModel;
    private ObservableList<RequestReply<KitchenMenuRequest, KitchenMenuReply>> list;

    public void initialize(){
        gateway = new MiddlewareAppGateway(this, kitchenName);
        listModel = FXCollections.observableArrayList();
    }

    public void btn1Action(ActionEvent actionEvent) {
        RequestReply<KitchenMenuRequest, KitchenMenuReply> rr = (RequestReply<KitchenMenuRequest, KitchenMenuReply>) listKitchen.getSelectionModel().getSelectedItem();
        int rating = Integer.parseInt((tfRating.getText()));
        KitchenMenuReply reply = new KitchenMenuReply(kitchenName, rating);
        if (rr!= null && reply != null){
            rr.setReply(reply);

            listKitchen.setItems(listModel);
            gateway.sendKitchenMenuReply(rr.getRequest(), reply);
            listKitchen.refresh();
        }
    }

    public void btn2Action(ActionEvent event){

    }

    @Override
    public void add(KitchenMenuRequest kitchenMenuRequest) {
        final RequestReply<KitchenMenuRequest, KitchenMenuReply> rr = new RequestReply<>(kitchenMenuRequest, (KitchenMenuReply) null);
        Platform.runLater(new Runnable() {
            @Override public void run() {
                listModel.add(rr);
            }
        });
        listKitchen.setItems(listModel);
        listKitchen.refresh();
    }
}
