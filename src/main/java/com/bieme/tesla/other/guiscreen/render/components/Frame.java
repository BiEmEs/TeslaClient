package com.bieme.tesla.other.guiscreen.render.components;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;

public class Frame {
    private static final int HEADER_HEIGHT = 20;

    private final Category category;
    private final ArrayList<ModuleButton> moduleButtons = new ArrayList<>();

    private int x, y, width, height;
    private boolean open = true;
    private boolean dragging = false;
    private boolean move;
    private int dragX, dragY;

    @SuppressWarnings("this-escape")
    public Frame(Category category, int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = HEADER_HEIGHT;

        if (Client.getHackManager() != null) {
            for (Module module : Client.getHackManager().getModules()) {
                if (module.getCategory() == category) {
                    moduleButtons.add(new ModuleButton(module, this));
                }
            }
        }

        refresh_frame(null, 0);
    }

    public void refresh_frame(ModuleButton button, int offset) {
        this.height = HEADER_HEIGHT;

        for (ModuleButton buttons : moduleButtons) {
            buttons.set_y(this.height);
            if (buttons.is_open()) {
                this.height += buttons.getSettingsHeight();
            } else {
                this.height += 10;
            }
        }
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, int offset) {
        if (move && dragging) {
            set_x(mouseX - dragX);
            set_y(mouseY - dragY);
        }

        int bg_r = Client.clickGui.theme_frame_background_r;
        int bg_g = Client.clickGui.theme_frame_background_g;
        int bg_b = Client.clickGui.theme_frame_background_b;
        int bg_a = Client.clickGui.theme_frame_background_a;

        int color_bg = (bg_a << 24) | (bg_r << 16) | (bg_g << 8) | bg_b;
        int color_nc = Client.clickGui.getAccentColor(y, 0);

        gui.fill(x, y, x + width, y + height, color_bg);
        gui.drawString(Minecraft.getInstance().font, category.name(), x + 4, y + 4, color_nc);

        if (open) {
            for (ModuleButton buttons : moduleButtons) {
                buttons.set_x(this.x + 2);
                buttons.render(gui, mouseX, mouseY, 2);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHoverHeader(mouseX, mouseY)) {
            if (mouseButton == 0) {
                dragging = true;
                move = true;
                dragX = mouseX - x;
                dragY = mouseY - y;
            }
            if (mouseButton == 1) {
                open = !open;
            }
        }

        if (open) {
            for (ModuleButton buttons : moduleButtons) {
                buttons.mouse(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseDragged(int mouseX, int mouseY) {
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (isBinding()) {
            bindKey(keyCode);
        }
        return false;
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        move = false;
        for (ModuleButton buttons : moduleButtons) {
            buttons.release(mouseX, mouseY, state);
        }
    }

    private boolean isHoverHeader(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + HEADER_HEIGHT;
    }

    private boolean isHover(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public boolean motion(int mouseX, int mouseY) {
        return isHover(mouseX, mouseY);
    }

    public int get_x() { return x; }
    public int get_y() { return y; }
    public int get_width() { return width; }
    public int get_height() { return height; }
    public boolean isOpen() { return open; }

    public void set_x(int x) { this.x = x; }
    public void set_y(int y) { this.y = y; }

    public void setMove(boolean move) { this.move = move; }
    public boolean isMoving() { return move; }
    public void set_move_x(int x) { this.dragX = x; }
    public void set_move_y(int y) { this.dragY = y; }
    public int get_move_x() { return dragX; }
    public int get_move_y() { return dragY; }

    public void does_can(boolean value) {
        for (ModuleButton buttons : moduleButtons) {
            buttons.does_widgets_can(value);
        }
    }

    public void bindKey(int key) {
        for (ModuleButton buttons : moduleButtons) {
            if (buttons.isBinding()) {
                buttons.bindKey(key);
                break;
            }
        }
    }

    public boolean isBinding() {
        for (ModuleButton buttons : moduleButtons) {
            if (buttons.isBinding()) return true;
        }
        return false;
    }

    public void bind(int key) {
        bindKey(key);
    }

    public void onMouseScroll(int amount) {
        // reserved for future scrollable settings
    }

    public ArrayList<ModuleButton> get_module_buttons() {
        return moduleButtons;
    }

    public String get_tag() { return category.name(); }
    public String get_name() { return category.name(); }
}
