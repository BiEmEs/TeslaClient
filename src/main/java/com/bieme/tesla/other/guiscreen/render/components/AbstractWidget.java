package com.bieme.tesla.other.guiscreen.render.components;

public abstract class AbstractWidget {
    public AbstractWidget() {}
    public AbstractWidget(Object a, Object b, Object c, Object d) {}

    // Setters.
    public void set_x(int x) {}
    public void set_y(int y) {}

    public void set_width(int width) {}
    public void set_height(int height) {}

    // Getters.
    public int get_x() {
        return 0;
    }

    public int get_y() {
        return 0;
    }

    public int get_width() {
        return 0;
    }

    public int get_height() {
        return 0;
    }

    // Binding.
    public boolean is_binding() {
        return false;
    }

    // Motion.
    public boolean motion_pass(int mx, int my) {
        return false;
    }

    // Keyboard.
    public void bind(char char_, int key) {}

    // Can.
    public void does_can(boolean value) {}

    // Mouse click.
    public void mouse(int mx, int my, int mouse) {}

    // Mouse release.
    public void release(int mx, int my, int mouse) {}

    // Render.
    public void render(net.minecraft.client.gui.GuiGraphics gui, int master_y, int separe, int x, int y) {}

    // Render abstract.
    public void render(int master_y, int separe, int x, int y) {}
}
