package com.example.friendswar;

import java.util.ArrayList;

public class AddOrder {
    private boolean selectDefOrder = true;
    ArrayList<String> order = new ArrayList<String>();
    ArrayList<String> orderTemplate = new ArrayList<String>();

    public AddOrder() {
        orderTemplate.add("กระโดดตบ 10 ครั้ง");
        orderTemplate.add("วิดพื้น 10 ครั้ง");
        orderTemplate.add("เล่าเรื่องตลก");
        orderTemplate.add("เล่าความลับตัวเอง");
        orderTemplate.add("บอกรักคนข้างๆ");
        orderTemplate.add("เต้นท่าตลกๆ");
        orderTemplate.add("เดินแบบลิง");
        orderTemplate.add("ร้องเพลง");
        orderTemplate.add("ดื่มน้ำ 1 ขวด");
        orderTemplate.add("โดนดีดหน้าผาก 5 ที");
    }
    public void addOrder(String aOrder) {
        order.add(aOrder);
    }
    public void setOrderSelect(String a) {
        if(a.equalsIgnoreCase("1"))
            selectDefOrder = true;
        else
            selectDefOrder = false;
    }


    public void deleteOrder(int ID) {
        if(ID>=0 && ID<order.size() ) {
            order.remove(ID);
        }
    }

    public String getOrderById(int ID) {
        return order.get(ID);
    }

    public ArrayList<String> getOrderEdit() {
        return order;
    }

    public ArrayList<String> getOrder() {
        if(selectDefOrder) {
            return orderTemplate;
        } else {
            return order;
        }
    }
}
