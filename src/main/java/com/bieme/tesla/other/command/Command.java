package com.bieme.tesla.other.command;

public class Command {

    private String name;
    private String description;
    private String[] aliases;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.aliases = new String[0];
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void execute(String[] args) {
    }
}