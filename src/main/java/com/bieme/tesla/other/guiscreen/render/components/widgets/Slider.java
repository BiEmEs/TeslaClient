package com.bieme.tesla.other.guiscreen.render.components.widgets;

import com.bieme.tesla.other.guiscreen.render.components.AbstractWidget;
import com.bieme.tesla.other.guiscreen.render.components.Frame;
import com.bieme.tesla.other.guiscreen.render.components.ModuleButton;
import net.minecraft.client.gui.GuiGraphics;

public class Slider extends AbstractWidget {
    private Frame frame;
    private ModuleButton master;
    private int x, y, width, height;
    private int save_y;
    private boolean can = true;
    private boolean dragging = false;
    private double percent = 0.5;

    public Slider(Frame frame, ModuleButton master, String tag, int update_position) {
        super(frame, master, tag, update_position);
        this.frame = frame;
        this.master = master;
        this.x = master.get_x();
        this.y = update_position;
        this.save_y = this.y;
        this.width = master.get_width();
        this.height = 10;
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
            dragging = true;
            updateSlider(mx);
        }
    }

    @Override
    public void release(int mx, int my, int mouse) {
        if (mouse == 0) dragging = false;
    }

    @Override
    public void render(GuiGraphics gui, int master_y, int separe, int mx, int my) {
        this.width = master.get_width() - separe;
        this.save_y = this.y + master_y;

        gui.fill(x, save_y, x + width, save_y + height, 0xFF2D1E30);
        
        int filled = (int)(width * percent);
        gui.fill(x, save_y, x + filled, save_y + height, 0xFF6F69B4);
        
        gui.drawString(mc.font, "Slider", x + 2, save_y + 2, 0xFFFFFFFF);

        if (dragging) {
            updateSlider(mx);
        }
    }

    private void updateSlider(int mouseX) {
        percent = (mouseX - x) / (double) width;
        percent = Math.max(0.0, Math.min(1.0, percent));
    }

    private boolean can() { return can; }

    private net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
}