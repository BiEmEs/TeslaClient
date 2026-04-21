package com.bieme.tesla.other.guiscreen.render.components.widgets;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.components.AbstractWidget;
import com.bieme.tesla.other.guiscreen.render.components.Frame;
import com.bieme.tesla.other.guiscreen.render.components.ModuleButton;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class Slider extends AbstractWidget {
    private Frame frame;
    private ModuleButton master;
    private Setting setting;

    private int x, y, width, height;
    private int save_x, save_y;
    private boolean can = true;
    private boolean dragging = false;

    private String slider_name;

    private float animPercent = 0.0f;
    private static final float ANIM_SPEED = 0.2f;

    public Slider(Frame frame, ModuleButton master, String tag, int update_position) {
        super(frame, master, tag, update_position);
        this.frame = frame;
        this.master = master;
        this.setting = Client.getSettingManager().get_setting_with_tag(master.get_module().get_tag(), tag);

        this.x = 0;
        this.y = update_position;
        this.save_x = 0;
        this.save_y = this.y;
        this.width = 0;
        this.height = Minecraft.getInstance().font.lineHeight + 2;
        this.can = true;

        this.slider_name = this.setting != null ? this.setting.getDisplayName() : tag;

        if (setting != null) {
            double min = setting.getMin();
            double max = setting.getMax();
            double current = setting.getSliderValue();
            this.animPercent = (float) ((current - min) / (max - min));
        }
    }

    @Override
    public void does_can(boolean value) { this.can = value; }

    @Override
    public void set_x(int x) { this.x = x; }
    @Override
    public void set_y(int y) { this.y = y; }
    @Override
    public void set_width(int width) { this.width = width; }
    @Override
    public void set_height(int height) { this.height = height; }

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
        this.save_x = this.x;
        this.save_y = this.y + master_y;

        this.width = this.master.get_width();

        if (setting == null) return;

        double min = setting.getMin();
        double max = setting.getMax();
        double current = setting.getSliderValue();
        float targetPercent = (float) ((current - min) / (max - min));
        targetPercent = Math.max(0.0f, Math.min(1.0f, targetPercent));

        if (animPercent < targetPercent) {
            animPercent = Math.min(targetPercent, animPercent + ANIM_SPEED);
        } else if (animPercent > targetPercent) {
            animPercent = Math.max(targetPercent, animPercent - ANIM_SPEED);
        }

        int bg_r = Client.clickGui.theme_widget_background_r;
        int bg_g = Client.clickGui.theme_widget_background_g;
        int bg_b = Client.clickGui.theme_widget_background_b;

        gui.fill(save_x, save_y, save_x + width, save_y + height, new java.awt.Color(bg_r, bg_g, bg_b, 200).getRGB());

        int accent = Client.clickGui.getAccentColor(save_y, Math.abs(slider_name.hashCode() % 20));
        int ar = (accent >> 16) & 0xFF;
        int ag = (accent >> 8) & 0xFF;
        int ab = accent & 0xFF;

        int filled = (int)(width * animPercent);
        if (filled > 0) {
            gui.fill(save_x, save_y, save_x + filled, save_y + height, new java.awt.Color(ar, ag, ab, 255).getRGB());
        }

        String displayText = slider_name + ": " + String.format("%.0f", current);
        gui.drawString(Minecraft.getInstance().font, displayText, save_x + 2, save_y + 1, 0xFFFFFFFF);

        if (dragging) {
            updateSlider(mx);
        }
    }

    private void updateSlider(int mouseX) {
        if (setting == null || width == 0) return;

        double min = setting.getMin();
        double max = setting.getMax();

        double percent = (mouseX - save_x) / (double) width;
        percent = Math.max(0.0, Math.min(1.0, percent));

        double newValue = min + (max - min) * percent;
        setting.setSliderValue(newValue);
    }

    private boolean can() { return can; }
}