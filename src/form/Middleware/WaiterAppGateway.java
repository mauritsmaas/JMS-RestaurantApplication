package form.Middleware;

import form.Waiter.WaiterController;
import shared.gateway.Gateway;
import shared.menu.MenuReply;
import shared.menu.MenuRequest;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class WaiterAppGateway extends Gateway {
    private MiddlewareController frame;

    public WaiterAppGateway(MiddlewareController frame) {
        super("menuReplyQueue", "menuRequestQueue");
        this.frame = frame;
    }

    public void menuReply(MenuReply reply, String corrolationId) {
        sender.send(sender.createTextMessage(reply, corrolationId));
    }

    @Override
    protected void processMessage(TextMessage message, String CorrelationId) throws JMSException {
        MenuRequest menuRequest = new MenuRequest();
        menuRequest.fillFromCommaSeperatedValue(message.getText());
        frame.add(menuRequest, CorrelationId);
    }
}
