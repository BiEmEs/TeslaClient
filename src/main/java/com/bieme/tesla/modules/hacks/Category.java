package com.bieme.tesla.modules.hacks;

public enum Category {
    HUD(true),
    COMBAT(false),
    MOVEMENT(false),
    RENDER(false),
    MISC(false),
    CLIENT(false),
    GUI(true);

    private final boolean hidden;

    Category(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean is_hidden() {
        return this.hidden;
    }
}