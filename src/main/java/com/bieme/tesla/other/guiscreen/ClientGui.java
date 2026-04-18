package com.bieme.tesla.other.guiscreen;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.components.Frame;
import com.bieme.tesla.modules.hacks.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class ClientGui extends Screen {

    private ArrayList<Frame> frame;
    private Minecraft mc;

    public boolean on_gui;
    private int frame_x = 10;
    private Frame current;

    private boolean event_start;
    private boolean event_finish;

    private boolean isDragging = false;
    private int dragStartX, dragStartY;
    private Frame draggingFrame = null;
    private static final int HEADER_HEIGHT = 20;

    public int theme_frame_name_r         = 255;
    public int theme_frame_name_g         = 105;
    public int theme_frame_name_b         = 180;
    public int theme_frame_name_a         = 255;

    public int theme_frame_background_r   = 30;
    public int theme_frame_background_g   = 20;
    public int theme_frame_background_b   = 30;
    public int theme_frame_background_a   = 240;

    public int theme_frame_border_r       = 255;
    public int theme_frame_border_g       = 105;
    public int theme_frame_border_b       = 180;
    public int theme_frame_border_a       = 255;

    public int theme_frame_border_size    = 1;

    public int theme_widget_name_r        = 255;
    public int theme_widget_name_g        = 255;
    public int theme_widget_name_b        = 255;
    public int theme_widget_name_a        = 255;

    public int theme_widget_background_r  = 45;
    public int theme_widget_background_g  = 30;
    public int theme_widget_background_b  = 45;
    public int theme_widget_background_a  = 200;

    public int theme_widget_border_r      = 255;
    public int theme_widget_border_g       = 105;
    public int theme_widget_border_b      = 180;

    private static final int KEY_GUI_ESCAPE = GLFW.GLFW_KEY_ESCAPE;

    public ClientGui() {
        super(Component.literal("Tesla GUI"));
        this.frame = new ArrayList<>();
        this.frame_x = 10;
        this.event_start = true;
        this.event_finish = false;
        this.mc = Minecraft.getInstance();
    }

    public int getAccentColor(int y, int index) {
        return (theme_frame_name_a << 24)
             | (theme_frame_name_r << 16)
             | (theme_frame_name_g << 8)
             |  theme_frame_name_b;
    }

    public void loadSettings() {
        if (Client.getHackManager() == null) return;

        var guiModule = Client.getHackManager().get_module_with_tag("GUI");
        if (guiModule == null) return;

        for (var setting : guiModule.getSettings()) {
            String tag = setting.getTag();
            switch (tag) {
                case "GUITFrameNameR": theme_frame_name_r = (int) setting.getSliderValue(); break;
                case "GUITFrameNameG": theme_frame_name_g = (int) setting.getSliderValue(); break;
                case "GUITFrameNameB": theme_frame_name_b = (int) setting.getSliderValue(); break;

                case "GUITFrameBackgroundR": theme_frame_background_r = (int) setting.getSliderValue(); break;
                case "GUITFrameBackgroundG": theme_frame_background_g = (int) setting.getSliderValue(); break;
                case "GUITFrameBackgroundB": theme_frame_background_b = (int) setting.getSliderValue(); break;

                case "GUITWidgetNameR": theme_widget_name_r = (int) setting.getSliderValue(); break;
                case "GUITWidgetNameG": theme_widget_name_g = (int) setting.getSliderValue(); break;
                case "GUITWidgetNameB": theme_widget_name_b = (int) setting.getSliderValue(); break;

                case "GUITWidgetBackgroundR": theme_widget_background_r = (int) setting.getSliderValue(); break;
                case "GUITWidgetBackgroundG": theme_widget_background_g = (int) setting.getSliderValue(); break;
                case "GUITWidgetBackgroundB": theme_widget_background_b = (int) setting.getSliderValue(); break;
            }
        }
    }

    public boolean isBlurEnabled() {
        if (Client.getHackManager() == null) return true;

        var guiModule = Client.getHackManager().get_module_with_tag("GUI");
        if (guiModule == null) return true;

        for (var setting : guiModule.getSettings()) {
            if (setting.getTag().equals("GUITBackgroundBlur")) {
                return setting.getBoolValue();
            }
        }
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        this.on_gui = true;
        
        if (!this.frame.isEmpty()) {
            return;
        }
        
        for (Category categorys : Category.values()) {
            if (categorys.is_hidden()) {
                continue;
            }

            Frame frames = new Frame(categorys, this.frame_x, 10);
            this.frame.add(frames);
            this.frame_x += frames.get_width() + 5;
            this.current = frames;
        }
    }

    @Override
    public void onClose() {
        if (Client.getHackManager() != null) {
            var guiModule = Client.getHackManager().get_module_with_tag("GUI");
            if (guiModule != null) {
                guiModule.setActive(false);
            }
        }

        if (Client.getConfigManager() != null) {
            Client.getConfigManager().saveSettings();
        }
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        int keyCode = event.key();

        for (Frame frame : this.frame) {
            frame.bind(keyCode);

            if (keyCode == KEY_GUI_ESCAPE && !frame.is_binding()) {
                this.onClose();
                this.mc.setScreen(null);
                return true;
            }
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean consumed) {
        int mx = (int) event.x();
        int my = (int) event.y();
        int button = event.button();

        if (button == 0) {
            for (Frame frames : this.frame) {
                if (frames.motion(mx, my) && frames.can()) {
                    if (my >= frames.get_y() && my <= frames.get_y() + HEADER_HEIGHT) {
                        this.isDragging = true;
                        this.draggingFrame = frames;
                        this.dragStartX = mx - frames.get_x();
                        this.dragStartY = my - frames.get_y();

                        frames.does_can(false);
                        set_current(frames);
                        return super.mouseClicked(event, consumed);
                    }
                }
            }

            for (Frame frames : this.frame) {
                frames.mouse(mx, my, button);
            }
        } else {
            for (Frame frames : this.frame) {
                frames.mouse(mx, my, button);
            }
        }
        return super.mouseClicked(event, consumed);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        int mx = (int) event.x();
        int my = (int) event.y();
        int button = event.button();

        if (isDragging && button == 0) {
            isDragging = false;
            draggingFrame = null;
            for (Frame frames : this.frame) {
                frames.does_can(true);
            }
        } else {
            for (Frame frames : this.frame) {
                frames.does_can(true);
                frames.mouse_release(mx, my, button);
            }
        }

        return super.mouseReleased(event);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (isDragging && draggingFrame != null) {
            int mx = (int) mouseX;
            int my = (int) mouseY;

            draggingFrame.set_x(mx - dragStartX);
            draggingFrame.set_y(my - dragStartY);
        }
        super.mouseMoved(mouseX, mouseY);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        for (Frame frames : this.frame) {
            frames.render(guiGraphics, mouseX, mouseY, 0);
        }
    }

    public void set_current(Frame current) {
        this.frame.remove(current);
        this.frame.add(current);
        this.current = current;
    }

    public Frame get_current() {
        return this.current;
    }

    public ArrayList<Frame> get_array_frames() {
        return this.frame;
    }

    public Frame get_frame_with_tag(String tag) {
        Frame frame_requested = null;
        for (Frame frames : get_array_frames()) {
            if (frames.get_tag().equals(tag)) {
                frame_requested = frames;
            }
        }
        return frame_requested;
    }
}