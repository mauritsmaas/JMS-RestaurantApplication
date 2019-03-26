package shared.gateway;

import javax.jms.*;


public abstract class Gateway {
    protected MessageSenderGateway sender;
    protected MessageReceiverGateway receiver;

    public Gateway(String senderChannel, String receiverChannel) {
        sender = new MessageSenderGateway(senderChannel);
        receiver = new MessageReceiverGateway(receiverChannel);

        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage)
                {
                    try {
                        String body = ((TextMessage)message).getText();
                        System.out.println(">>> CorrolationId: " + message.getJMSCorrelationID() + " Message: " + body);
                        processMessage((TextMessage)message, message.getJMSCorrelationID());
                    }
                    catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected abstract void processMessage(TextMessage message, String CorrelationId) throws JMSException;
}
