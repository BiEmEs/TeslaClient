package com.bieme.tesla.other.guiscreen.settings;

import com.bieme.tesla.modules.hacks.Module;

public class Setting {
    private String name;
    private Object value;
    private Object defaultValue;
    public String type = "toggle";
    private double min = 0, max = 100;
    private String currentValue = "";
    private java.util.List<String> values = null;

    public String setting_name;
    public String display_name;
    public boolean slider;
    public Module master;

    public Setting() {
    }

    public Setting(String name, Object value) {
        this.name = name;
        this.value = value;
        this.defaultValue = value;
        this.setting_name = name;
    }

    public String getName() { return name; }
    public String getTag() { return setting_name; }
    public String get_type() { return type; }
    public Object getValue() { return value; }
    public void setValue(Object v) { this.value = v; }
    public Object getDefaultValue() { return defaultValue; }
    public Module getMaster() { return master; }

    /**
     * Returns the human-readable display name if set, otherwise falls back to the internal name (tag).
     * Use this in widgets instead of get_name()/getName() so users see "Frame Name R" instead of "HUDFrameNameR".
     */
    public String getDisplayName() {
        if (display_name != null && !display_name.isEmpty()) {
            return display_name;
        }
        return name;
    }

    public boolean getBoolValue() {
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof String) return Boolean.parseBoolean((String) value);
        return false;
    }

    public double getSliderValue() {
        if (value instanceof Number) return ((Number) value).doubleValue();
        return 0;
    }

    public void setSliderValue(double v) {
        this.value = v;
    }

    public double getMin() { return min; }
    public void setMin(double min) { this.min = min; }
    public void setMax(double max) { this.max = max; }
    public double getMax() { return max; }
    public String getStringValue() {
        if (value != null) return value.toString();
        return "";
    }

    public String get_current_value() { return currentValue.isEmpty() ? getStringValue() : currentValue; }
    public void set_current_value(String v) { this.currentValue = v; }

    public String get_name() { return name; }
    public void set_value(Object v) { this.value = v; }
    public Object get_value(boolean asBool) { return asBool ? getBoolValue() : value; }

    public java.util.List<String> get_values() { return values; }
    public void set_values(java.util.List<String> v) { this.values = v; }
}