package com.bieme.tesla.other.manager;

import com.bieme.tesla.other.guiscreen.hud.Coordinates;
import com.bieme.tesla.other.guiscreen.hud.FPS;
import com.bieme.tesla.other.guiscreen.hud.Ping;
import com.bieme.tesla.other.guiscreen.hud.PvpHud;
import com.bieme.tesla.other.guiscreen.hud.Speedometer;
import com.bieme.tesla.other.guiscreen.hud.TPS;
import com.bieme.tesla.other.guiscreen.hud.Time;
import com.bieme.tesla.other.guiscreen.hud.TotemCount;
import com.bieme.tesla.other.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.Comparator;

public class ManagerHud {

    public static ArrayList<Pinnable> array_hud = new ArrayList<>();

    public ManagerHud() {
        add_component_pinnable(new FPS());
        add_component_pinnable(new Ping());
        add_component_pinnable(new TPS());
        add_component_pinnable(new Time());
        add_component_pinnable(new Speedometer());
        add_component_pinnable(new Coordinates());
        add_component_pinnable(new TotemCount());
        add_component_pinnable(new PvpHud());

        array_hud.sort(Comparator.comparing(Pinnable::get_title));
    }

    public void add_component_pinnable(Pinnable pinnable) {
        array_hud.add(pinnable);
    }

    public ArrayList<Pinnable> get_array_huds() {
        return array_hud;
    }

    public void render(GuiGraphics gui) {
        for (Pinnable pinnable : get_array_huds()) {
            if (pinnable.is_active()) {
                pinnable.render(gui);
            }
        }
    }

    public Pinnable get_pinnable_with_tag(String tag) {
        for (Pinnable pinnable : get_array_huds()) {
            if (pinnable.get_tag().equalsIgnoreCase(tag)) {
                return pinnable;
            }
        }
        return null;
    }
}
