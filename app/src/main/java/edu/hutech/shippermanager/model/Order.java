package edu.hutech.shippermanager.model;

/**
 * Created by jerem on 19/11/2016.
 */

public class Order {
    private Customer sender;
    private Customer receiver;
    private boolean status;
    private long time;
    private long totalPrice;
    private String userID;
    private String orderID;
    private User user;
    private boolean isRunning;

    public Order() {
    }

    public Order(Customer sender, Customer receiver, boolean status, long time, long totalPrice, String userID) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.time = time;
        this.totalPrice = totalPrice;
        this.userID = userID;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getReceiver() {
        return receiver;
    }

    public void setReceiver(Customer receiver) {
        this.receiver = receiver;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object obj) {
        Order objOrder = ((Order) obj);
        return this.getOrderID().trim().equalsIgnoreCase(objOrder.getOrderID().trim());
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
