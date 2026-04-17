package com.bieme.tesla.modules.hacks;

import com.bieme.tesla.modules.hacks.Category;

public class Module {

    private String name;
    private String description;
    private boolean enabled;
    private Category category;
    private int bind = 0;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onUpdate() {
    }

    public String get_tag() { return name.toLowerCase().replace(" ", "_"); }
    public void setBind(int key) { this.bind = key; }
    public int getBind() { return this.bind; }
    public String getBind(String type) {
        if ("string".equals(type)) return bind == 0 ? "None" : String.valueOf(bind);
        return String.valueOf(bind);
    }
}