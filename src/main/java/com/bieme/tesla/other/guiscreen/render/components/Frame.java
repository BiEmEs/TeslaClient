package com.bieme.tesla.other.guiscreen.render.components;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.other.guiscreen.render.components.widgets.Toggle;
import com.bieme.tesla.other.guiscreen.render.components.widgets.Slider;
import com.bieme.tesla.other.guiscreen.render.components.widgets.Label;
import com.bieme.tesla.other.guiscreen.render.components.widgets.Combobox;
import com.bieme.tesla.other.guiscreen.render.components.widgets.ButtonBind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;

public class Frame {
    private static final int HEADER_HEIGHT = 20;

    private final Category category;
    private final ArrayList<ModuleButton> moduleButtons = new ArrayList<>();

    private int x, y, width, height;
    private boolean open = true;
    private boolean can = true;

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
                    ModuleButton mb = new ModuleButton(module, this);
                    moduleButtons.add(mb);

                    // FIX: se quitó la exclusión de "gui" para que el módulo GUI
                    // también muestre sus widgets y no se superponga con el siguiente.
                    if (module.getSettings() != null) {
                        for (var setting : module.getSettings()) {
                            String type = setting.type;
                            if ("slider".equals(type)) {
                                mb.add_widget(new Slider(this, mb, setting.get_name(), 0));
                            } else if ("info".equals(type) || "string".equals(type)) {
                                mb.add_widget(new Label(this, mb, setting.get_name(), 0));
                            } else if ("combobox".equals(type)) {
                                mb.add_widget(new Combobox(this, mb, setting.get_name(), 0));
                            } else {
                                mb.add_widget(new Toggle(this, mb, setting.get_name(), 0));
                            }
                        }
                        mb.add_widget(new ButtonBind(this, mb, "Bind", 0));
                    }
                }
            }
        }

        refresh_frame(null, 0);
    }

    public void refresh_frame(ModuleButton button, int offset) {
        this.height = HEADER_HEIGHT;

        int fontHeight = Minecraft.getInstance().font.lineHeight + 2;

        for (ModuleButton buttons : moduleButtons) {
            buttons.set_y(this.height);
            if (buttons.is_open()) {

                this.height += fontHeight + buttons.getSettingsHeight();
            } else {
                this.height += fontHeight;
            }
        }
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, int offset) {
        if (Client.clickGui == null) {
            return;
        }

        refresh_frame(null, 0);

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

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (isBinding()) {
            bindKey(keyCode);
        }
        return false;
    }

    public void mouse(int mouseX, int mouseY, int mouseButton) {
        mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouse_release(int mouseX, int mouseY, int state) {
        for (ModuleButton buttons : moduleButtons) {
            buttons.release(mouseX, mouseY, state);
        }
    }

    public boolean is_binding() {
        return isBinding();
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

    }

    public ArrayList<ModuleButton> get_module_buttons() {
        return moduleButtons;
    }

    public String get_tag() { return category.name(); }
    public String get_name() { return category.name(); }

    public boolean can() { return can; }
    public void can(boolean value) { this.can = value; }
}