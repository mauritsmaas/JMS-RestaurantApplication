package form.Waiter;

import com.sun.istack.NotNull;
import shared.gateway.Gateway;
import shared.menu.MenuReply;
import shared.menu.MenuRequest;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;

public class MiddlewareAppGateway extends Gateway {

    private WaiterController frame;
    private Map<String, MenuRequest> menuRequests;
    @NotNull
    private String name = "clientName";
    @NotNull
    private int Id = 0;

    public MiddlewareAppGateway(WaiterController frame) {
        super("menuRequestQueue", "menuReplyQueue");
        this.frame = frame;
        menuRequests = new HashMap<>();
    }

    //order a menu
    public void orderAMenu(MenuRequest request) {
        String corrolationId = getCorrolationId();
        menuRequests.put(corrolationId, request);
        sender.send(sender.createTextMessage(request, corrolationId));
    }

    @Override
    protected void processMessage(TextMessage message, String CorrelationId) throws JMSException {
        MenuReply menuReply = new MenuReply();
        menuReply.fillFromCommaSeperatedValue(message.getText());
        MenuRequest request = menuRequests.get(CorrelationId);
        System.out.println(CorrelationId);
        frame.addReply(request, menuReply);
    }

    @NotNull
    private String getCorrolationId()
    {
        Id++;
        return name + Integer.toString(Id);
    }

}
