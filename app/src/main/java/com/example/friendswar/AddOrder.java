package com.example.friendswar;

import java.util.ArrayList;

public class AddOrder {
    private int OrderID = 0;
    ArrayList<String> order = new ArrayList<String>();
    ArrayList<String> orderDef = new ArrayList<String>();

    public void AddOrder() {
        orderDef.add("1");
        orderDef.add("2");
        orderDef.add("3");
        orderDef.add("4");
        orderDef.add("5");
        orderDef.add("6");
    }

    public void addOrder(String aOrder) {
        order.add(aOrder);
    }

    public void deleteOrder(int ID) {
        if(ID>=0 && ID<order.size() ) {
            order.remove(ID);
        }
    }

    public String getOrderById(int ID) {
        return order.get(ID);
    }

    public ArrayList<String> getOrder() {
        return order;
    }

    public ArrayList<String> getOrderDef() {
        return orderDef;
    }
}
