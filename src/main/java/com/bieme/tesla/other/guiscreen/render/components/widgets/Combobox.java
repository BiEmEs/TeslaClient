package com.bieme.tesla.other.guiscreen.render.components.widgets;

import com.bieme.tesla.other.guiscreen.render.components.AbstractWidget;
import com.bieme.tesla.other.guiscreen.render.components.Frame;
import com.bieme.tesla.other.guiscreen.render.components.ModuleButton;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import net.minecraft.client.gui.GuiGraphics;
import java.util.ArrayList;

public class Combobox extends AbstractWidget {
    private ArrayList<String> values;
    private Frame frame;
    private ModuleButton master;
    private Setting setting;
    private String combobox_name;
    private int x, y, width, height;
    private int combobox_actual_value;
    private int save_y;
    private boolean can = true;

    public Combobox(Frame frame, ModuleButton master, String tag, int update_position) {
        super(frame, master, tag, update_position);
        this.values = new ArrayList<>();
        this.frame = frame;
        this.master = master;
        this.x = master.get_x();
        this.y = update_position;
        this.save_y = this.y;
        this.width = master.get_width();
        this.height = 10;
        this.combobox_name = tag;
        this.can = true;
    }

    @Override
    public void does_can(boolean value) { this.can = value; }

    @Override
    public void set_x(int x) { this.x = x; }
    @Override
    public void set_y(int y) { this.y = y; }
    @Override
    public void set_width(int w) { this.width = w; }
    @Override
    public void set_height(int h) { this.height = h; }

    @Override
    public int get_x() { return x; }
    @Override
    public int get_y() { return y; }
    @Override
    public int get_width() { return width; }
    @Override
    public int get_height() { return height; }
    public int get_save_y() { return save_y; }

    public boolean motion(int mx, int my) {
        return mx >= get_x() && my >= get_save_y() && mx <= get_x() + get_width() && my <= get_save_y() + get_height();
    }

    @Override
    public void mouse(int mx, int my, int mouse) {
        if (mouse == 0 && motion(mx, my) && master.is_open() && can()) {
            frame.does_can(false);
            this.combobox_actual_value++;
        }
    }

    @Override
    public void render(GuiGraphics gui, int master_y, int separe, int mx, int my) {
        this.width = master.get_width() - separe;
        this.save_y = this.y + master_y;
        
        gui.drawString(mc.font, combobox_name, x + 2, save_y, 0xFFFFFFFF);
    }

    @Override
    public void release(int mx, int my, int mouse) {
        if (mouse == 0) frame.does_can(true);
    }

    private boolean can() { return can; }

    private net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
}