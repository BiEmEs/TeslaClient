package com.bieme.tesla.other.manager;

import com.bieme.tesla.other.guiscreen.settings.Setting;
import com.bieme.tesla.modules.hacks.Module;
import java.util.ArrayList;

public class ManagerSetting {

    private ArrayList<Setting> settings = new ArrayList<>();

    public ManagerSetting() {
    }

    public void register(Module module, Setting setting) {
        settings.add(setting);
    }

    public void unregister(Setting setting) {
        settings.remove(setting);
    }

    public void init() {
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public ArrayList<Setting> getSettingsWithModule(Module module) {
        ArrayList<Module> result = new ArrayList<>();
        // TODO: implement when module has tag
        return new ArrayList<>();
    }

    public Setting get_setting_with_tag(String moduleTag, String settingTag) {
        for (Setting s : settings) {
            if (s.getName().equals(settingTag)) {
                return s;
            }
        }
        return new Setting(settingTag, "");
    }

    public Setting getSettingByTag(String moduleTag, String settingTag) {
        return get_setting_with_tag(moduleTag, settingTag);
    }
}