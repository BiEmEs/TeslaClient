package com.bieme.tesla.other.manager;

import com.bieme.tesla.modules.hacks.Module;
import java.util.ArrayList;

public class ManagerHack {

    private ArrayList<Module> modules = new ArrayList<>();

    public ManagerHack() {
    }

    public void init() {
    }

    public void register(Module module) {
        modules.add(module);
    }

    public void unregister(Module module) {
        modules.remove(module);
    }
    
    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module get_module_with_tag(String tag) {
        for (Module m : modules) {
            if (m.get_tag().equalsIgnoreCase(tag)) return m;
        }
        return null;
    }
}