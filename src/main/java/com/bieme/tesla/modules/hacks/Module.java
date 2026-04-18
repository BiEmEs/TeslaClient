package com.bieme.tesla.modules.hacks;

import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import com.bieme.tesla.Client;

import java.util.ArrayList;
import java.util.List;

public class Module {

    private String name;
    private String description;
    private boolean enabled;
    private Category category;
    private int bind = 0;
    private final List<Setting> settings = new ArrayList<>();

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
    }

    public String getName() {
        return name;
    }

    public String get_name() {
        return getName();
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

    public void setActive(boolean value) { setEnabled(value); }
    public boolean isActive() { return enabled; }

    public String get_tag() { return name.toLowerCase().replace(" ", "_"); }
    public String getTag() { return get_tag(); }
    public void setBind(int key) { this.bind = key; }
    public int getBind() { return this.bind; }
    public String getBind(String type) {
        if ("string".equals(type)) return bind == 0 ? "None" : String.valueOf(bind);
        return String.valueOf(bind);
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void create(String displayName, String settingName, boolean value) {
        Setting setting = new Setting(settingName, value);
        setting.display_name = displayName;
        setting.type = "toggle";
        settings.add(setting);
        
        if (Client.getSettingManager() != null) {
            Client.getSettingManager().register(this, setting);
        }
    }

    public void create(String displayName, String settingName, double value, double min, double max) {
        Setting setting = new Setting(settingName, value);
        setting.setMin(min);
        setting.setMax(max);
        setting.display_name = displayName;
        setting.type = "slider";
        settings.add(setting);
        
        if (Client.getSettingManager() != null) {
            Client.getSettingManager().register(this, setting);
        }
    }
}