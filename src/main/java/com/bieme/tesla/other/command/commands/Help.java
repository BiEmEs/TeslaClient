package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.other.command.Command;

public class Help extends Command {

    public Help() {
        super("help", "Show help");
    }

    @Override
    public void execute(String[] args) {
    }
}