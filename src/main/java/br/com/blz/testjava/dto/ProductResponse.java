package br.com.blz.testjava.dto;

public class ProductResponse {
    private Long sku;
    private String name;
    private boolean isMarketable;
    private InventoryResponse inventory;

    public Long getSku() {
        return sku;
    }
    public void setSku(Long sku) {
        this.sku = sku;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isMarketable() {
        return isMarketable;
    }
    public void setMarketable(boolean marketable) {
        isMarketable = marketable;
    }
    public InventoryResponse getInventory() {
        return inventory;
    }
    public void setInventory(InventoryResponse inventory) {
        this.inventory = inventory;
    }
}
