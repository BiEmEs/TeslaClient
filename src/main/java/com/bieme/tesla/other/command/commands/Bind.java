package com.bieme.tesla.other.command.commands;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.other.command.Command;
import org.lwjgl.glfw.GLFW;

public class Bind extends Command {

    public Bind() {
        super("bind", "Bind a key to a module");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            sendMessage("Usage: +bind <module> <key>");
            sendMessage("Example: +bind killaura right");
            return;
        }

        String moduleName = args[0];
        String keyName = args[1].toLowerCase();

        Module module = Client.getHackManager().get_module_with_tag(moduleName.toLowerCase());
        if (module == null) {
            module = findModuleByName(moduleName);
        }

        if (module == null) {
            sendError("Module not found: " + moduleName);
            return;
        }

        int keyCode = getKeyCode(keyName);
        if (keyCode == -1) {
            sendError("Invalid key: " + keyName);
            sendMessage("Keys: left, right, middle, lshift, lctrl, space, ...");
            return;
        }

        module.setBind(keyCode);
        sendSuccess(module.getName() + " bound to " + keyName);
    }

    private Module findModuleByName(String name) {
        for (com.bieme.tesla.modules.hacks.Module m : Client.getHackManager().getModules()) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    private int getKeyCode(String keyName) {
        switch (keyName) {
            case "left": return GLFW.GLFW_KEY_LEFT;
            case "right": return GLFW.GLFW_KEY_RIGHT;
            case "lshift": return GLFW.GLFW_KEY_LEFT_SHIFT;
            case "rshift": return GLFW.GLFW_KEY_RIGHT_SHIFT;
            case "lctrl": return GLFW.GLFW_KEY_LEFT_CONTROL;
            case "rctrl": return GLFW.GLFW_KEY_RIGHT_CONTROL;
            case "lalt": return GLFW.GLFW_KEY_LEFT_ALT;
            case "ralt": return GLFW.GLFW_KEY_RIGHT_ALT;
            case "tab": return GLFW.GLFW_KEY_TAB;
            case "space": return GLFW.GLFW_KEY_SPACE;
            case "enter": return GLFW.GLFW_KEY_ENTER;
            case "backspace": return GLFW.GLFW_KEY_BACKSPACE;
            case "delete": return GLFW.GLFW_KEY_DELETE;
            case "insert": return GLFW.GLFW_KEY_INSERT;
            case "home": return GLFW.GLFW_KEY_HOME;
            case "end": return GLFW.GLFW_KEY_END;
            case "pageup": return GLFW.GLFW_KEY_PAGE_UP;
            case "pagedown": return GLFW.GLFW_KEY_PAGE_DOWN;
            case "up": return GLFW.GLFW_KEY_UP;
            case "down": return GLFW.GLFW_KEY_DOWN;
            case "1": return GLFW.GLFW_KEY_1;
            case "2": return GLFW.GLFW_KEY_2;
            case "3": return GLFW.GLFW_KEY_3;
            case "4": return GLFW.GLFW_KEY_4;
            case "5": return GLFW.GLFW_KEY_5;
            case "6": return GLFW.GLFW_KEY_6;
            case "7": return GLFW.GLFW_KEY_7;
            case "8": return GLFW.GLFW_KEY_8;
            case "9": return GLFW.GLFW_KEY_9;
            case "0": return GLFW.GLFW_KEY_0;
            case "a": return GLFW.GLFW_KEY_A;
            case "b": return GLFW.GLFW_KEY_B;
            case "c": return GLFW.GLFW_KEY_C;
            case "d": return GLFW.GLFW_KEY_D;
            case "e": return GLFW.GLFW_KEY_E;
            case "f": return GLFW.GLFW_KEY_F;
            case "g": return GLFW.GLFW_KEY_G;
            case "h": return GLFW.GLFW_KEY_H;
            case "i": return GLFW.GLFW_KEY_I;
            case "j": return GLFW.GLFW_KEY_J;
            case "k": return GLFW.GLFW_KEY_K;
            case "l": return GLFW.GLFW_KEY_L;
            case "m": return GLFW.GLFW_KEY_M;
            case "n": return GLFW.GLFW_KEY_N;
            case "o": return GLFW.GLFW_KEY_O;
            case "p": return GLFW.GLFW_KEY_P;
            case "q": return GLFW.GLFW_KEY_Q;
            case "r": return GLFW.GLFW_KEY_R;
            case "s": return GLFW.GLFW_KEY_S;
            case "t": return GLFW.GLFW_KEY_T;
            case "u": return GLFW.GLFW_KEY_U;
            case "v": return GLFW.GLFW_KEY_V;
            case "w": return GLFW.GLFW_KEY_W;
            case "x": return GLFW.GLFW_KEY_X;
            case "y": return GLFW.GLFW_KEY_Y;
            case "z": return GLFW.GLFW_KEY_Z;
            case "f1": return GLFW.GLFW_KEY_F1;
            case "f2": return GLFW.GLFW_KEY_F2;
            case "f3": return GLFW.GLFW_KEY_F3;
            case "f4": return GLFW.GLFW_KEY_F4;
            case "f5": return GLFW.GLFW_KEY_F5;
            case "f6": return GLFW.GLFW_KEY_F6;
            case "f7": return GLFW.GLFW_KEY_F7;
            case "f8": return GLFW.GLFW_KEY_F8;
            case "f9": return GLFW.GLFW_KEY_F9;
            case "f10": return GLFW.GLFW_KEY_F10;
            case "f11": return GLFW.GLFW_KEY_F11;
            case "f12": return GLFW.GLFW_KEY_F12;
            default: return -1;
        }
    }
}