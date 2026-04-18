package com.bieme.tesla.other.guiscreen.render.components.widgets;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.components.AbstractWidget;
import com.bieme.tesla.other.guiscreen.render.components.Frame;
import com.bieme.tesla.other.guiscreen.render.components.ModuleButton;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class Combobox extends AbstractWidget {

    private final Frame frame;
    private final ModuleButton master;
    private final Setting setting;

    private final String combobox_name;

    private int x;
    private int y;
    private int save_x;
    private int save_y;

    private int width;
    private int height;

    private boolean can;

    public Combobox(Frame frame, ModuleButton master, String tag, int update_position) {
        this.frame   = frame;
        this.master  = master;
        this.setting = Client.getSettingManager().get_setting_with_tag(master.get_module().get_tag(), tag);

        this.x = 0;
        this.y = update_position;
        this.save_x = 0;
        this.save_y = this.y;

        this.width  = 0;
        this.height = Minecraft.getInstance().font.lineHeight + 2;

        this.combobox_name = this.setting.get_name();
        this.can = true;
    }

    public Setting get_setting() { return this.setting; }

    @Override
    public void does_can(boolean value) { this.can = value; }

    @Override public void set_x(int x) { this.x = x; }
    @Override public void set_y(int y) { this.y = y; }
    @Override public void set_width(int width) { this.width = width; }
    @Override public void set_height(int height) { this.height = height; }

    @Override public int get_x() { return this.x; }
    @Override public int get_y() { return this.y; }
    @Override public int get_width() { return this.width; }
    @Override public int get_height() { return this.height; }
    public int get_save_y() { return this.save_y; }

    @Override
    public boolean motion_pass(int mx, int my) {
        return motion(mx, my);
    }

    public boolean motion(int mx, int my) {
        return mx >= get_x() && my >= get_save_y() && mx <= get_x() + get_width() && my <= get_save_y() + get_height();
    }

    public boolean can() { return this.can; }

    @Override
    public void mouse(int mx, int my, int mouse) {
        if (!motion(mx, my) || !this.master.is_open() || !can()) return;

        List<String> values = this.setting.get_values();
        if (values == null || values.isEmpty()) return;

        String current = this.setting.getStringValue();
        int idx = values.indexOf(current);
        if (idx < 0) idx = 0;

        if (mouse == 0) {
            // left click -> siguiente
            idx = (idx + 1) % values.size();
        } else if (mouse == 1) {
            // right click -> anterior
            idx = (idx - 1 + values.size()) % values.size();
        } else {
            return;
        }

        this.setting.set_value(values.get(idx));
        this.frame.does_can(false);
    }

    @Override
    public void release(int mx, int my, int mouse) {
        this.frame.does_can(true);
    }

    @Override
    public void render(GuiGraphics gui, int master_y, int separe, int absolute_x, int absolute_y) {
        this.width  = this.master.get_width() - separe;
        this.save_x = this.x;
        this.save_y = this.y + master_y;

        int ns_r = Client.clickGui.theme_widget_name_r;
        int ns_g = Client.clickGui.theme_widget_name_g;
        int ns_b = Client.clickGui.theme_widget_name_b;
        int ns_a = Client.clickGui.theme_widget_name_a;

        int textColor = (ns_a << 24) | (ns_r << 16) | (ns_g << 8) | ns_b;

        String display = this.combobox_name + " [" + this.setting.getStringValue() + "]";
        gui.drawString(Minecraft.getInstance().font, display, this.save_x + 2, this.save_y + 1, textColor);
    }
}