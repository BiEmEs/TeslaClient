package com.bieme.tesla.other.guiscreen;

import com.bieme.tesla.Client;
import com.bieme.tesla.other.guiscreen.render.components.Frame;
import com.bieme.tesla.modules.hacks.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.awt.Color;
import java.util.ArrayList;

public class ClientGui extends Screen {

    private ArrayList<Frame> frame;
    private Minecraft mc;

    public boolean on_gui;
    private int frame_x = 10;
    private Frame current;

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
    public int theme_widget_border_g      = 105;
    public int theme_widget_border_b      = 180;

    public ClientGui() {
        super(Component.literal("Tesla GUI"));
        this.mc = Minecraft.getInstance();
        this.frame = new ArrayList<>();
        this.frame_x = 10;
    }

    public int getAccentColor(int y, int index) {
        return (theme_frame_name_a << 24)
             | (theme_frame_name_r << 16)
             | (theme_frame_name_g << 8)
             |  theme_frame_name_b;
    }

    public void updateThemeColors() {
        com.bieme.tesla.modules.hacks.Module guiModule = Client.getHackManager().get_module_with_tag("GUI");
        if (guiModule != null) {
            com.bieme.tesla.other.guiscreen.settings.Setting frameR = Client.getSettingManager().get_setting_with_tag("GUI", "GUITFrameNameR");
            com.bieme.tesla.other.guiscreen.settings.Setting frameG = Client.getSettingManager().get_setting_with_tag("GUI", "GUITFrameNameG");
            com.bieme.tesla.other.guiscreen.settings.Setting frameB = Client.getSettingManager().get_setting_with_tag("GUI", "GUITFrameNameB");
            if (frameR != null) this.theme_frame_name_r = (int) frameR.getSliderValue();
            if (frameG != null) this.theme_frame_name_g = (int) frameG.getSliderValue();
            if (frameB != null) this.theme_frame_name_b = (int) frameB.getSliderValue();
        }
    }

    @Override
    public void init() {
        super.init();
        this.on_gui = true;

        if (frame.isEmpty()) {
            this.frame_x = 10;
            for (Category category : Category.values()) {
                if (category.is_hidden()) continue;
                Frame f = new Frame(category, this.frame_x, 10);
                this.frame.add(f);
                this.frame_x += f.get_width() + 5;
            }

            if (!this.frame.isEmpty()) {
                this.current = this.frame.get(0);
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void removed() {
        com.bieme.tesla.modules.hacks.Module guiModule = Client.getHackManager().get_module_with_tag("GUI");
        if (guiModule != null) guiModule.setActive(false);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        int keyCode = event.key();
        boolean isDraggingAny = false;

        for (Frame f : this.frame) {
            if (f.isMoving()) {
                isDraggingAny = true;
                break;
            }
        }

        for (Frame f : this.frame) {
            if (!isDraggingAny && f.isBinding()) {
                f.bind(keyCode);
            }

            if (keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE) {
                if (f.isBinding()) {
                    f.bind(1);
                } else if (!isDraggingAny) {
                    mc.setScreen(null);
                    return true;
                }
            }
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean consumed) {
        int mx = (int) event.x();
        int my = (int) event.y();
        int button = event.button();

        boolean handled = false;
        for (Frame f : this.frame) {
            if (f.motion(mx, my)) {
                f.mouseClicked(mx, my, button);
                this.current = f;
                handled = true;
                break;
            }
        }
        return handled || super.mouseClicked(event, consumed);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        int mx = (int) event.x();
        int my = (int) event.y();
        int button = event.button();

        for (Frame f : this.frame) {
            f.mouseReleased(mx, my, button);
            for (com.bieme.tesla.other.guiscreen.render.components.ModuleButton mb : f.get_module_buttons()) {
                mb.release(mx, my, button);
            }
            f.setMove(false);
        }
        set_current(this.current);
        return super.mouseReleased(event);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double deltaX, double deltaY) {
        int mx = (int) event.x();
        int my = (int) event.y();

        for (Frame f : this.frame) {
            f.mouseDragged(mx, my);
        }
        return super.mouseDragged(event, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        int scrollAmount = (int) verticalAmount;
        for (Frame f : this.frame) {
            f.onMouseScroll(scrollAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void render(net.minecraft.client.gui.GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        updateThemeColors();
        for (Frame f : this.frame) {
            f.render(guiGraphics, mouseX, mouseY, 0);
        }
    }

    public void set_current(Frame current) {
        if (current != null) {
            this.frame.remove(current);
            this.frame.add(current);
            this.current = current;
        }
    }

    public Frame get_current() {
        return this.current;
    }

    public ArrayList<Frame> get_array_frames() {
        return this.frame;
    }

    public Frame get_frame_hud() {
        return this.frame.stream()
            .filter(f -> "HUD".equals(f.get_tag()))
            .findFirst()
            .orElse(null);
    }

    public ArrayList<Frame> get_array_huds() {
        return get_array_frames();
    }
}
