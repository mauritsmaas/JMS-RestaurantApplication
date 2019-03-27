package form.Middleware;

import shared.kitchen.KitchenMenuReply;

import java.util.ArrayList;
import java.util.List;

public class Aggregation {
    private int id;
    private int expectedReplies;
    private String corrolationId;
    private List<KitchenMenuReply> replies;

    public Aggregation(int id, int expectedReplies, String corrolationId) {
        this.expectedReplies = expectedReplies;
        this.id = id;
        this.corrolationId = corrolationId;
        replies = new ArrayList<>();
    }

    public int getExpectedReplies() {
        return expectedReplies;
    }

    public int getId() {
        return id;
    }

    public String getCorrolationId() {
        return corrolationId;
    }

    public int getReceivedReplies() {
        return replies.size();
    }

    public void addKitchenMenuReply(KitchenMenuReply reply) {
        replies.add(reply);
    }

    public boolean repliesReceived() {
        if (replies.size() == expectedReplies) {
            return true;
        }
        return false;
    }

    public KitchenMenuReply getBestReply() {
        KitchenMenuReply bestReply = null;
        for (KitchenMenuReply reply : replies) {
            if (bestReply == null || bestReply.getRating() > reply.getRating()) {
                bestReply = reply;
            }
        }
        return bestReply;
    }
}
