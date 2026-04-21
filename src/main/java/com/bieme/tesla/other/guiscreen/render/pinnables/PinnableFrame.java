package com.bieme.tesla.other.guiscreen.render.pinnables;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import java.util.ArrayList;

public class PinnableFrame {
    private ArrayList<PinnableButton> pinnable_button;

    private String name;
    private String tag;

    private int x, y;
    private int move_x, move_y;
    private int width, height;
    private boolean move;
    private boolean can = true;
    private int border_size = 2;

    public static int nc_r = 0, nc_g = 0, nc_b = 0, nc_a = 0;
    public static int bg_r = 30, bg_g = 20, bg_b = 30, bg_a = 240;
    public static int bd_r = 0, bd_g = 0, bd_b = 0, bd_a = 120;
    public static int bdw_r = 0, bdw_g = 0, bdw_b = 0, bdw_a = 255;

    private final Minecraft mc = Minecraft.getInstance();

    public PinnableFrame(String name, String tag, int initial_x, int initial_y) {
        this.pinnable_button = new ArrayList<>();
        this.name = name;
        this.tag = tag;
        this.x = initial_x;
        this.y = initial_y;
        this.width = 100;
        this.height = 25;
    }

    public void set_move(boolean value) { this.move = value; }
    public void does_can(boolean value) { this.can = value; }
    public void set_x(int x) { this.x = x; }
    public void set_y(int y) { this.y = y; }
    public void set_move_x(int x) { this.move_x = x; }
    public void set_move_y(int y) { this.move_y = y; }
    public void set_width(int w) { this.width = w; }
    public void set_height(int h) { this.height = h; }

    public String get_name() { return name; }
    public String get_tag() { return tag; }
    public boolean is_moving() { return move; }
    public boolean can() { return can; }
    public int get_x() { return x; }
    public int get_y() { return y; }
    public int get_width() { return width; }
    public int get_height() { return height; }

    public boolean motion(int mx, int my) {
        return mx >= get_x() && my >= get_y() && mx <= get_x() + get_width() && my <= get_y() + get_height();
    }

    public void crush(int mx, int my) {
        set_x(mx - move_x);
        set_y(my - move_y);
    }

    public void add_button(PinnableButton btn) {
        pinnable_button.add(btn);
    }

    public void clear_buttons() {
        pinnable_button.clear();
    }

    public void click(int mx, int my, int mouse) {
        for (PinnableButton pb : pinnable_button) {
            pb.click(mx, my, mouse);
        }
    }

    public void release(int mx, int my, int mouse) {
        for (PinnableButton pb : pinnable_button) {
            pb.release(mx, my, mouse);
        }
        set_move(false);
    }

    public void render(GuiGraphics gui, int mx, int my, int separate) {
        if (is_moving()) crush(mx, my);

        int total_h = height;
        for (PinnableButton pb : pinnable_button) {
            total_h += pb.get_height() + 1;
        }

        // FIX: draw border first so background covers the interior, leaving only 1px edge visible
        gui.fill(x - 1, y, x + width + 1, y + total_h, (bd_a << 24) | (bd_r << 16) | (bd_g << 8) | bd_b);
        // FIX: was (... | bg_r) — blue channel was wrong, should be bg_b
        gui.fill(x, y, x + width, y + total_h, (bg_a << 24) | (bg_r << 16) | (bg_g << 8) | bg_b);
        gui.drawString(mc.font, name, x + 4, y + 4, 0xFFFFFFFF);

        int button_y = y + height;
        for (PinnableButton pb : pinnable_button) {
            pb.set_x(x + separate);
            pb.set_save_y(button_y);
            pb.render(gui, mx, my, separate);

            if (pb.motion(mx, my)) {
                // FIX: was get_width()+1 (not x2) and pb.get_height() (not y2)
                gui.fill(x - 1, button_y, x + width + 1, button_y + pb.get_height(), (bdw_a << 24) | (bdw_r << 16) | (bdw_g << 8) | bdw_b);
            }
            button_y += pb.get_height() + 1;
        }
    }
}