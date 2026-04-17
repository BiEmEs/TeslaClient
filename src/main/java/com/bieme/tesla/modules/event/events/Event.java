package com.bieme.tesla.modules.event.events;

public class Event {
    private boolean cancelled;
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}