package form.Kitchen;

import shared.gateway.Gateway;
import shared.kitchen.KitchenMenuReply;
import shared.kitchen.KitchenMenuRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

public class MiddlewareAppGateway extends Gateway {

    private List<Request> requests;
    private IKitchenController frame;

    public MiddlewareAppGateway(IKitchenController frame, String kitchenName) {
        super("kitchenMenuReplyQueue", kitchenName + "RequestQueue");
        this.frame = frame;
        requests = new ArrayList<>();
    }

    public void sendKitchenMenuReply(KitchenMenuRequest kitchenMenuRequest, KitchenMenuReply reply) {
        for (Request request : requests) {
            if (request.getKitchenMenuRequest() == kitchenMenuRequest) {
                try {
                    Message message = sender.createTextMessage(reply, request.getCorrolationId());
                    message.setIntProperty("aggregationId", request.getAggregationId());
                    sender.send(message);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    protected void processMessage(TextMessage message, String CorrelationId) throws JMSException {
        Integer aggregationId = message.getIntProperty("aggregationId");
        if (aggregationId == null)
            return;

        KitchenMenuRequest kitchenMenuRequest = new KitchenMenuRequest();
        kitchenMenuRequest.fillFromCommaSeperatedValue(message.getText());
        requests.add(new Request(message.getJMSCorrelationID(), kitchenMenuRequest, aggregationId));
        frame.add(kitchenMenuRequest);
    }


}
