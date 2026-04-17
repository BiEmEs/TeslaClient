package com.bieme.tesla.modules.hacks.combat;

import com.bieme.tesla.modules.hacks.Category;
import com.bieme.tesla.modules.hacks.Module;

public class KillAura extends Module {

    public KillAura() {
        super("KillAura", "Automatically attacks entities", Category.COMBAT);
    }

    @Override
    public void onUpdate() {
        // Will be implemented with proper entity iteration
    }

    public boolean isActive() {
        return isEnabled();
    }
}