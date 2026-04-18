package com.bieme.tesla.other.guiscreen.render.components;

import com.bieme.tesla.Client;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.modules.hacks.client.GUI;
import com.bieme.tesla.other.guiscreen.render.components.widgets.Toggle;
import com.bieme.tesla.other.guiscreen.render.components.widgets.Slider;
import com.bieme.tesla.other.guiscreen.render.components.widgets.ButtonBind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;

public class ModuleButton {
    private final Module module;
    private final Frame master;
    private final ArrayList<AbstractWidget> widget = new ArrayList<>();

    private final String module_name;

    private int x, y;
    private int width, height;
    private int save_y;
    private int save_x;
    private int settings_height;
    private int cachedWidth;
    private int next_widget_y;

    private boolean opened = false;

    public ModuleButton(Module module, Frame master) {
        this.module = module;
        this.master = master;
        this.module_name = module.getName();

        this.width = 100;
        this.cachedWidth = this.width;
        this.height = Minecraft.getInstance().font.lineHeight + 2;
        this.save_y = 0;
        this.save_x = 0;
        this.settings_height = 0;
        this.next_widget_y = this.height;
    }

    public void add_widget(AbstractWidget w) {
        w.set_y(next_widget_y);
        widget.add(w);
        int h = w.get_height() > 0 ? w.get_height() : (Minecraft.getInstance().font.lineHeight + 2);
        next_widget_y += h;
        settings_height += h;
    }

    public Module get_module() { return module; }
    public Frame get_master() { return master; }
    public boolean is_open() { return opened; }
    public boolean get_state() { return module.isEnabled(); }

    public boolean isBinding() {
        for (AbstractWidget w : widget)
            if (w.is_binding()) return true;
        return false;
    }

    public int get_x() { return x; }
    public int get_y() { return y; }
    public int get_save_y() { return save_y; }
    public int get_width() { return width; }
    public int get_height() { return height; }

    public void set_x(int x) { this.x = x; }
    public void set_y(int y) { this.y = y; }
    public void set_width(int w) { this.width = w; }
    public void set_height(int h) { this.height = h; }
    public void set_open(boolean value) { this.opened = value; }
    public void set_pressed(boolean value) { module.setEnabled(value); }

    public int getSettingsHeight() { return settings_height; }

    public void does_widgets_can(boolean can) {
        for (AbstractWidget w : widget) w.does_can(can);
    }

    public void does_button_for_do_widgets_can(boolean can) {
        does_widgets_can(can);
    }

    public boolean motion(int mx, int my) {
        return mx >= x && my >= save_y && mx <= x + width && my <= save_y + height;
    }

    public void mouse(int mx, int my, int mouse) {
        if (motion(mx, my)) {
            master.does_can(false);
            if (mouse == 0) set_pressed(!get_state());
            if (mouse == 1) {
                set_open(!is_open());
                master.refresh_frame(this, 0);
            }
        }

        if (is_open()) {
            for (AbstractWidget w : widget) {
                w.set_x(this.x + 2);
                w.mouse(mx, my, mouse);
            }
        }
    }

    public void bindKey(int key) {
        for (AbstractWidget w : widget) {
            w.bind('\0', key);
        }
    }

    public void release(int mx, int my, int mouse) {
        for (AbstractWidget w : widget) {
            w.release(mx, my, mouse);
        }
        master.does_can(true);
    }

    public void button_release(int mx, int my, int mouse) {
        master.does_can(true);
    }

    public void render(GuiGraphics gui, int mx, int my, int separe) {
        if (Client.clickGui == null) {
            return;
        }

        set_width(cachedWidth - separe);
        this.save_x = this.x;
        this.save_y = master.get_y() + this.y;

        int bg_r = Client.clickGui.theme_widget_background_r;
        int bg_g = Client.clickGui.theme_widget_background_g;
        int bg_b = Client.clickGui.theme_widget_background_b;

        int color_bg_on = Client.clickGui.getAccentColor(save_y, Math.abs(module_name.hashCode() % 20));
        int color_bg_hover = (100 << 24) | (bg_r << 16) | (bg_g << 8) | bg_b;

        if (module.isEnabled()) {
            gui.fill(this.x, this.save_y, this.x + this.width, this.save_y + this.height, color_bg_on);
        } else if (motion(mx, my)) {
            gui.fill(this.x, this.save_y, this.x + this.width, this.save_y + this.height, color_bg_hover);
        }

        int textColor;
        if (module.isEnabled()) {
            textColor = color_bg_on;
        } else {
            int nm_r = Client.clickGui.theme_widget_name_r;
            int nm_g = Client.clickGui.theme_widget_name_g;
            int nm_b = Client.clickGui.theme_widget_name_b;
            int nm_a = Client.clickGui.theme_widget_name_a;
            textColor = (nm_a << 24) | (nm_r << 16) | (nm_g << 8) | nm_b;
        }

        gui.drawString(Minecraft.getInstance().font, this.module_name, this.x + separe, this.save_y + 1, textColor);

        if (is_open()) {
            for (AbstractWidget w : widget) {
                w.set_x(this.x + separe);
                w.render(gui, save_y, 0, mx, my);
            }
        }
    }

    public int size() {
        return widget.size();
    }
}