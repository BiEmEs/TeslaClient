package com.bieme.tesla.modules.event.events;

public class UpdateWalkingPlayerEvent extends Event {
    private boolean pre;
    
    public UpdateWalkingPlayerEvent(boolean pre) {
        this.pre = pre;
    }
    
    public boolean isPre() {
        return pre;
    }
}