package com.bieme.tesla.other.guiscreen.render.pinnables;

import com.bieme.tesla.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font;

public abstract class Pinnable {
    private final String title;
    private final String tag;
    
    private boolean state;
    private boolean move;
    
    private int x, y;
    private int width, height;
    private int move_x, move_y;
    private boolean dock = true;
    
    protected final Minecraft mc = Minecraft.getInstance();

    public Pinnable(String title, String tag, int x, int y) {
        this.title = title;
        this.tag = tag;
        this.x = x;
        this.y = y;
        this.width = 1;
        this.height = 10;
    }

    public void set_move(boolean value) { this.move = value; }
    public void set_active(boolean value) { this.state = value; }
    public void set_x(int x) { this.x = x; }
    public void set_y(int y) { this.y = y; }
    public void set_width(int width) { this.width = width; }
    public void set_height(int height) { this.height = height; }
    public void set_move_x(int x) { this.move_x = x; }
    public void set_move_y(int y) { this.move_y = y; }
    public void set_dock(boolean value) { this.dock = value; }

    public boolean is_moving() { return this.move; }
    public boolean is_active() { return this.state; }

    public int get_x() { return this.x; }
    public int get_y() { return this.y; }
    public int get_width() { return this.width; }
    public int get_height() { return this.height; }
    public boolean get_dock() { return this.dock; }

    public String get_title() { return this.title; }
    public String get_tag() { return this.tag; }

    public int get_title_height() {
        return 10;
    }

    public boolean motion(int mx, int my) {
        return mx >= get_x() && my >= get_y() && mx <= get_x() + get_width() && my <= get_y() + get_height();
    }

    public void crush(int mx, int my) {
        set_x(mx - move_x);
        set_y(my - move_y);
    }

    public void click(int mx, int my, int mouse) {
        if (mouse == 0 && is_active() && motion(mx, my)) {
            set_move(true);
            set_move_x(mx - get_x());
            set_move_y(my - get_y());
        }
    }

    public void release(int mx, int my, int mouse) {
        set_move(false);
    }

    public void render(GuiGraphics gui, int mx, int my) {
        if (is_moving()) crush(mx, my);
        if (is_active()) {
            render(gui);
        }
    }

    public abstract void render(GuiGraphics gui);

    protected void create_line(GuiGraphics gui, String text, int x, int y) {
        gui.drawString(mc.font, text, this.x + x, this.y + y, 0xFFFFFFFF);
    }

    protected void create_rect(GuiGraphics gui, int x, int y, int w, int h, int color) {
        gui.fill(this.x + x, this.y + y, this.x + x + w, this.y + y + h, color);
    }

    public int get_text_width(String text) {
        if (mc.font == null) return 1;
        return mc.font.width(text);
    }
}