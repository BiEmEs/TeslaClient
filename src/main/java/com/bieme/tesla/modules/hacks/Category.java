package com.bieme.tesla.modules.hacks;

public enum Category {
    COMBAT(false),
    MISC(false),
    RENDER(false),
    CLIENT(false),
    MOVEMENT(false),
    HUD(true),
    GUI(true);

    private final boolean hidden;

    Category(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean is_hidden() {
        return this.hidden;
    }
}
