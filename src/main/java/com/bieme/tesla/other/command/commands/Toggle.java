package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.other.command.Command;

public class Toggle extends Command {

    public Toggle() {
        super("toggle", "Toggle a module on/off");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            return;
        }
        String moduleName = args[0];
    }
}