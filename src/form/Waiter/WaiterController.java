package form.Waiter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import shared.menu.MenuReply;
import shared.menu.MenuRequest;
import shared.request.RequestReply;

import javax.swing.*;

public class WaiterController {

    @FXML
    private TextField textFieldMenu;
    @FXML
    private TextField tfAmount;
    @FXML
    private TextField textFieldSsn;
    @FXML
    private ListView lvMenus;

    private MiddlewareAppGateway gateway;

    private ObservableList<RequestReply<MenuRequest,MenuReply>> listModel;
    //private JList<RequestReply<MenuRequest,MenuReply>> requestReplyList;

    @FXML
    public void initialize(){
        gateway = new MiddlewareAppGateway(this);
        listModel = FXCollections.observableArrayList();
        //requestReplyList = new JList<RequestReply<MenuRequest,MenuReply>>(listModel);
    }


    public void btnSubmitAction(ActionEvent actionEvent) {
        int ssn = Integer.parseInt(textFieldSsn.getText());
        String menu = textFieldMenu.getText();
        int amount = Integer.parseInt(tfAmount.getText());

        MenuRequest request = new MenuRequest(ssn, menu, amount);
        RequestReply rr = new RequestReply<MenuRequest, MenuReply>(request, null);
        listModel.add(rr);
        gateway.orderAMenu(request);
        lvMenus.setItems(listModel);

    }

    public void addReply(MenuRequest request, MenuReply menuReply) {
        for (int i = 0; i < listModel.size(); i++){
            RequestReply<MenuRequest,MenuReply> rr = listModel.get(i);
            if (rr.getRequest() == request){
                rr.setReply(menuReply);
                lvMenus.setItems(listModel);
                lvMenus.refresh();
                break;
            }
        }

    }
}
