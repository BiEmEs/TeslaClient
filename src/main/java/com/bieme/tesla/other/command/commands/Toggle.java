package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.other.command.Command;

public class Toggle extends Command {

    public Toggle() {
        super("toggle", "Toggle a module on/off");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            sendMessage("Usage: +toggle <module>");
            return;
        }

        String moduleName = args[0].toLowerCase();

        Module module = Client.getHackManager().get_module_with_tag(moduleName);
        if (module == null) {
            module = findModuleByName(moduleName);
        }

        if (module == null) {
            sendError("Module not found: " + args[0]);
            return;
        }

        module.toggle();
        if (module.isEnabled()) {
            sendSuccess(module.getName() + " enabled");
        } else {
            sendError(module.getName() + " disabled");
        }
    }

    private Module findModuleByName(String name) {
        for (Module m : Client.getHackManager().getModules()) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }
}