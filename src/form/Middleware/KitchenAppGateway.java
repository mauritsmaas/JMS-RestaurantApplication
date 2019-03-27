package form.Middleware;

import com.sun.istack.NotNull;
import shared.gateway.MessageReceiverGateway;
import shared.gateway.MessageSenderGateway;
import shared.kitchen.KitchenMenuReply;
import shared.kitchen.KitchenMenuRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

public class KitchenAppGateway {

    private List<Aggregation> aggregations;
    private MessageReceiverGateway receiver;
    @NotNull
    private int id = 0;

    private MiddlewareController frame;
    private List<Rule> kitchenRules = new ArrayList<Rule>(){{
        add(new Rule("Indisch",new ArrayList<String>(){{add("bami"); add("nasi"); add("curry"); add("mihoen");}} ));
        add(new Rule("Nederlands", new ArrayList<String>(){{add("kroket"); add("frikandel"); add("boerenkool"); add("rode kool");}} ));
        add(new Rule("Alles", new ArrayList<String>(){{add("bami"); add("nasi"); add("curry"); add("kroket"); add("frikandel"); add("rode kool"); }}));
    }};

    public KitchenAppGateway(MiddlewareController frame) {
        this.frame = frame;
        aggregations = new ArrayList<>();

        for (Rule rule : kitchenRules) {
            rule.setSender(new MessageSenderGateway(rule.getKitchenName() + "RequestQueue"));
        }
        receiver = new MessageReceiverGateway("kitchenMenuReplyQueue");
        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage)
                {
                    try {
                        processMessage((TextMessage)message, message.getJMSCorrelationID());
                    }
                    catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void sendKitchenRequest(KitchenMenuRequest request, String corrolationId) {
        int aggregationId = getAggregationID();
        int expectedReplies = 0;
        List<Rule> rulesToSend = new ArrayList<>();
        for (Rule rule : kitchenRules) {
            if (rule.check(request.getMenu())) {
                expectedReplies++;
                rulesToSend.add(rule);
            }
        }
        aggregations.add(new Aggregation(aggregationId, expectedReplies, corrolationId));
        for (Rule rule : rulesToSend) {
            try {
                MessageSenderGateway sender = rule.getSender();
                Message message = sender.createTextMessage(request, corrolationId);
                message.setIntProperty("aggregationId", aggregationId);
                sender.send(message);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void processMessage(TextMessage message, String CorrelationId) throws JMSException {
        String body = message.getText();
        System.out.println(">>> CorrolationId: " + message.getJMSCorrelationID() + " Message: " + body);
        KitchenMenuReply kitchenMenuReply = new KitchenMenuReply();
        kitchenMenuReply.fillFromCommaSeperatedValue(body);
        Integer aggregationId = message.getIntProperty("aggregationId");
        if (aggregationId == null)
            return;
        Aggregation foundAggregation = null;
        for (Aggregation aggregation : aggregations) {
            if (aggregation.getId() == aggregationId) {
                foundAggregation = aggregation;
                break;
            }
        }
        if (foundAggregation != null) {
            foundAggregation.addKitchenMenuReply(kitchenMenuReply);
            if (foundAggregation.repliesReceived()){
                processAggregation(foundAggregation);
            }
        }
    }

    private void processAggregation(Aggregation aggregation) {
        aggregations.remove(aggregation);
        frame.add(aggregation.getCorrolationId(), aggregation.getBestReply());
    }

    @NotNull
    private int getAggregationID()
    {
        id++;
        return id;
    }
}
