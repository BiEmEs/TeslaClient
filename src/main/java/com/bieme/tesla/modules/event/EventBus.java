package com.bieme.tesla.modules.event;

import java.util.ArrayList;
import java.util.function.Consumer;

public class EventBus {

    private static final ArrayList<Consumer<?>> listeners = new ArrayList<>();

    public static void register(Object listener) {
    }

    public static void unregister(Object listener) {
    }

    public static void post(Object event) {
    }
}