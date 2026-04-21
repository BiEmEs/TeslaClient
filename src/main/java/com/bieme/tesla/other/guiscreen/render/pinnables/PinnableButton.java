package com.bieme.tesla.other.guiscreen.render.pinnables;

import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import com.bieme.tesla.other.guiscreen.render.pinnables.PinnableFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class PinnableButton {
    private Pinnable pinnable;
    private PinnableFrame master;

    private int x, y, save_y, width, height;
    private final Minecraft mc = Minecraft.getInstance();

    public static int nc_r = 255, nc_g = 255, nc_b = 255, nc_a = 255;
    public static int bg_r = 45, bg_g = 30, bg_b = 45, bg_a = 200;
    public static int bd_r = 255, bd_g = 105, bd_b = 180;

    public PinnableButton(PinnableFrame master, String name, String tag) {
        this.master = master;
        this.pinnable = null;
        this.x = master.get_x();
        this.y = master.get_y();
        this.save_y = this.y;
        this.width = master.get_width();
        this.height = 10;
    }

    public void set_x(int x) { this.x = x; }
    public void set_y(int y) { this.y = y; }
    public void set_save_y(int y) { this.save_y = y; }
    public void set_width(int w) { this.width = w; }
    public void set_height(int h) { this.height = h; }

    public int get_x() { return x; }
    public int get_y() { return y; }
    public int get_save_y() { return save_y; }
    public int get_width() { return width; }
    public int get_height() { return height; }

    public boolean motion(int mx, int my) {
        return mx >= get_x() && my >= get_save_y() && mx <= get_x() + get_width() && my <= get_save_y() + get_height();
    }

    public void click(int mx, int my, int mouse) {
        if (pinnable != null) pinnable.click(mx, my, mouse);
        if (mouse == 0 && motion(mx, my)) {
            master.does_can(false);
            if (pinnable != null) pinnable.set_active(!pinnable.is_active());
        }
    }

    public void release(int mx, int my, int mouse) {
        if (pinnable != null) pinnable.release(mx, my, mouse);
        master.does_can(true);
    }

    public void render(GuiGraphics gui, int mx, int my, int separate) {
        this.width = master.get_width() - separate;
        // save_y is set externally by PinnableFrame.render() via set_save_y() before this call

        String label = pinnable != null ? pinnable.get_title() : "";
        if (pinnable != null && pinnable.is_active()) {
            gui.fill(x, save_y, x + width, save_y + height, 0xFF2D1E30);
        }
        // FIX: text drawn at save_y+1 for 1px top padding instead of flush at top edge
        gui.drawString(mc.font, label, x + separate, save_y + 1, 0xFFFFFFFF);
    }
    
    public Pinnable getPinnable() { return pinnable; }
    public void setPinnable(Pinnable p) { this.pinnable = p; }
}