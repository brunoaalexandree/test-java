package br.com.blz.testjava.model;

import java.util.List;

public class Inventory {
    private List<Warehouse> warehouses;

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
}
