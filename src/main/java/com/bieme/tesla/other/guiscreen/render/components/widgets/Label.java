package com.bieme.tesla.other.guiscreen.render.components.widgets;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.ClientDraw;
import com.bieme.tesla.other.guiscreen.render.components.AbstractWidget;
import com.bieme.tesla.other.guiscreen.render.components.Frame;
import com.bieme.tesla.other.guiscreen.render.components.ModuleButton;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import net.minecraft.client.gui.GuiGraphics;

public class Label extends AbstractWidget {
    private Frame frame;
    private ModuleButton master;
    private Setting setting;

    private String label_name;

    private int x;
    private int y;

    private int width;
    private int height;

    private int save_y;

    private boolean can;
    private boolean info;

    private final ClientDraw font = new ClientDraw(1);

    public Label(Frame frame, ModuleButton master, String tag, int update_postion) {
        this.frame   = frame;
        this.master  = master;
        this.setting = Client.getSettingManager().get_setting_with_tag(master.get_module().get_tag(), tag);

        this.x = master.get_x();
        this.y = update_postion;

        this.save_y = this.y;

        this.width  = master.get_width();
        this.height = font.getStringHeight();

        this.label_name = this.setting.getName();

        // Wurstplus-style: si el displayName fue "info" el tipo queda marcado como "info"
        // y se renderiza solo el valor (sin el prefijo name: "value").
        if ("info".equalsIgnoreCase(this.setting.type)
                || this.setting.getName().equalsIgnoreCase("info")) {
            this.info = true;
        }

        this.can = true;
    }

    public Setting get_setting() {
        return this.setting;
    }

    @Override
    public void does_can(boolean value) {
        this.can = value;
    }

    @Override
    public void set_x(int x) { this.x = x; }

    @Override
    public void set_y(int y) { this.y = y; }

    @Override
    public void set_width(int width) { this.width = width; }

    @Override
    public void set_height(int height) { this.height = height; }

    @Override
    public int get_x() { return this.x; }

    @Override
    public int get_y() { return this.y; }

    @Override
    public int get_width() { return this.width; }

    @Override
    public int get_height() { return this.height; }

    public int get_save_y() { return this.save_y; }

    @Override
    public boolean motion_pass(int mx, int my) {
        return motion(mx, my);
    }

    public boolean motion(int mx, int my) {
        return mx >= get_x() && my >= get_save_y() && mx <= get_x() + get_width() && my <= get_save_y() + get_height();
    }

    public boolean can() {
        return this.can;
    }

    @Override
    public void mouse(int mx, int my, int mouse) {
        if (mouse == 0) {
            if (motion(mx, my) && this.master.is_open() && can()) {
                this.frame.does_can(false);
            }
        }
    }

    @Override
    public void release(int mx, int my, int mouse) {
        if (mouse == 0) {
            this.frame.does_can(true);
        }
    }

    @Override
    public void render(GuiGraphics gui, int master_y, int separe, int absolute_x, int absolute_y) {
        set_width(this.master.get_width() - separe);

        this.save_y = this.y + master_y;

        int ns_r = Client.clickGui.theme_widget_name_r;
        int ns_g = Client.clickGui.theme_widget_name_g;
        int ns_b = Client.clickGui.theme_widget_name_b;
        int ns_a = Client.clickGui.theme_widget_name_a;

        if (this.info) {
            ClientDraw.draw_string(gui, this.setting.getStringValue(), this.x + 2, this.save_y, ns_r, ns_g, ns_b, ns_a);
        } else {
            ClientDraw.draw_string(gui, this.label_name + " \"" + this.setting.getStringValue() + "\"", this.x + 2, this.save_y, ns_r, ns_g, ns_b, ns_a);
        }
    }
}