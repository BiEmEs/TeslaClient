package com.bieme.tesla.modules.mixins;

import com.bieme.tesla.Client;
import com.bieme.tesla.TeslaMod;
import com.bieme.tesla.modules.hacks.Module;
import com.bieme.tesla.modules.utils.player.ItemUtil;
import com.bieme.tesla.other.guiscreen.hud.TPS;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Mixin(Minecraft.class)
public class MixinMinecraftClient {
    private static boolean lastKeyState = false;
    private static final List<Integer> pressedKeys = new ArrayList<>();

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        Minecraft mc = (Minecraft)(Object)this;

        if (mc.getConnection() != null) {
            TPS.onServerTick();
        }

        if (Client.getHackManager() != null) {
            for (Module module : Client.getHackManager().get_modules()) {
                if (module.isEnabled()) {
                    module.onUpdate();
                }
            }
        }

        try {
            if (ItemUtil.windowHandle == 0L) {
                Field windowField = mc.getWindow().getClass().getDeclaredField("handle");
                windowField.setAccessible(true);
                ItemUtil.windowHandle = windowField.getLong(mc.getWindow());
            }

            if (ItemUtil.windowHandle != 0L) {
                int keyState = GLFW.glfwGetKey(ItemUtil.windowHandle, GLFW.GLFW_KEY_RIGHT_SHIFT);
                boolean isPressed = keyState == GLFW.GLFW_PRESS;

                if (isPressed && !lastKeyState) {
                    TeslaMod.onKeyPress();
                }

                lastKeyState = isPressed;

                if (mc.screen == null) {
                    handleModuleBinds(mc, ItemUtil.windowHandle);
                }
            }
        } catch (Exception e) {
        }
    }

    private void handleModuleBinds(Minecraft mc, long window) {
        if (Client.getHackManager() == null) return;

        List<Module> modules = Client.getHackManager().get_modules();
        for (Module module : modules) {
            int bind = module.getBind();
            if (bind == 0) continue;

            int keyState = GLFW.glfwGetKey(window, bind);
            boolean isPressed = keyState == GLFW.GLFW_PRESS;

            if (isPressed && !pressedKeys.contains(bind)) {
                module.toggle();
                pressedKeys.add(bind);
            } else if (!isPressed && pressedKeys.contains(bind)) {
                pressedKeys.remove(Integer.valueOf(bind));
            }
        }
    }
}
