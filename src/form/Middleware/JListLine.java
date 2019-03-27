package form.Middleware;


import shared.kitchen.KitchenMenuReply;
import shared.kitchen.KitchenMenuRequest;
import shared.menu.MenuRequest;

class JListLine {

	private MenuRequest menuRequest;
	private KitchenMenuRequest kitchenMenuRequest;
	private KitchenMenuReply kitchenMenuReply;

	public JListLine(MenuRequest menuRequest) {
		this.setMenuRequest(menuRequest);
	}

	public MenuRequest getMenuRequest() {
		return menuRequest;
	}

	public void setMenuRequest(MenuRequest menuRequest) {
		this.menuRequest = menuRequest;
	}

	public KitchenMenuRequest getKitchenMenuRequest() {
		return kitchenMenuRequest;
	}

	public void setKitchenMenuRequest(KitchenMenuRequest kitchenMenuRequest) {
		this.kitchenMenuRequest = kitchenMenuRequest;
	}

	public KitchenMenuReply getKitchenMenuReply() {
		return kitchenMenuReply;
	}

	public void setKitchenMenuReply(KitchenMenuReply kitchenMenuReply) {
		this.kitchenMenuReply = kitchenMenuReply;
	}

	@Override
	public String toString() {
		return menuRequest.toString() + " || " + ((kitchenMenuReply != null) ? kitchenMenuReply.toString() : "waiting for reply...");
	}

}
