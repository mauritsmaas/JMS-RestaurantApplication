package form.Kitchen;


import shared.kitchen.KitchenMenuRequest;

public class Request {
    private String corrolationId;
    private KitchenMenuRequest kitchenMenuRequest;
    private int aggregationId;

    public Request(String corrolationId, KitchenMenuRequest kitchenMenuRequest, int aggregationId) {
        this.corrolationId = corrolationId;
        this.kitchenMenuRequest = kitchenMenuRequest;
        this.aggregationId = aggregationId;
    }

    public String getCorrolationId() {
        return corrolationId;
    }

    public KitchenMenuRequest getKitchenMenuRequest() {
        return kitchenMenuRequest;
    }

    public int getAggregationId() {
        return aggregationId;
    }
}
