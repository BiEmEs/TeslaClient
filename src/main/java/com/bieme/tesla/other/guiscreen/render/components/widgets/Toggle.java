package com.bieme.tesla.other.guiscreen.render.components.widgets;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.components.AbstractWidget;
import com.bieme.tesla.other.guiscreen.render.components.Frame;
import com.bieme.tesla.other.guiscreen.render.components.ModuleButton;
import com.bieme.tesla.other.guiscreen.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class Toggle extends AbstractWidget {

    private final Frame frame;
    private final ModuleButton master;
    private final Setting setting;

    private final String toggle_name;

    private int x;
    private int y;
    private int width;
    private int height;
    private int save_x;
    private int save_y;
    private boolean can;

    public Toggle(Frame frame, ModuleButton master, String tag, int update_position) {
        this.frame = frame;
        this.master = master;
        this.setting = Client.getSettingManager().get_setting_with_tag(master.get_module().get_tag(), tag);

        this.x = 0;
        this.y = update_position;
        this.save_x = 0;
        this.save_y = this.y;

        this.width = 0;
        this.height = Minecraft.getInstance().font.lineHeight + 2;

        // FIX: usar getDisplayName() para mostrar "Frame Name R" en vez de "HUDFrameNameR"
        this.toggle_name = this.setting.getDisplayName();
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
        if (mouse == 0 && motion(mx, my) && this.master.is_open() && can()) {
            this.frame.does_can(false);
            boolean newValue = !this.setting.getBoolValue();
            this.setting.set_value(newValue);
        }
    }

    @Override
    public void render(GuiGraphics gui, int master_y, int separe, int absolute_x, int absolute_y) {
        this.width = this.master.get_width();
        this.save_x = this.x;
        this.save_y = this.y + master_y;

        boolean is_active = this.setting.getBoolValue();

        int accentColor = Client.clickGui.getAccentColor(this.save_y, Math.abs(this.toggle_name.hashCode() % 20));

        if (is_active) {
            gui.fill(save_x, save_y, save_x + width, save_y + height, (60 << 24) | ((accentColor) & 0x00FFFFFF));
        }

        gui.drawString(Minecraft.getInstance().font, this.toggle_name, this.save_x + 2, this.save_y + 1, 0xFFFFFFFF);

        int boxSize = height - 2;
        int boxX = save_x + width - boxSize - 2;
        int boxY = save_y + 1;
        if (is_active) {
            gui.fill(boxX, boxY, boxX + boxSize, boxY + boxSize, (255 << 24) | (accentColor & 0x00FFFFFF));
        } else {
            gui.fill(boxX, boxY, boxX + boxSize, boxY + boxSize, 0xFF555555);
        }
    }

    @Override
    public void release(int mx, int my, int mouse) {
        if (mouse == 0) {
            this.frame.does_can(true);
        }
    }
}