package com.bieme.tesla.modules.mixins;

import com.bieme.tesla.TeslaMod;
import com.bieme.tesla.other.guiscreen.hud.TPS;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.lang.reflect.Field;

@Mixin(Minecraft.class)
public class MixinMinecraftClient {
    private static boolean lastKeyState = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        Minecraft mc = (Minecraft)(Object)this;

        // Track client ticks for TPS approximation (client tick rate matches server when online)
        if (mc.getConnection() != null) {
            TPS.onServerTick();
        }

        try {
            Field windowField = mc.getWindow().getClass().getDeclaredField("handle");
            windowField.setAccessible(true);
            long window = windowField.getLong(mc.getWindow());

            if (window != 0L) {
                int keyState = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT);
                boolean isPressed = keyState == GLFW.GLFW_PRESS;

                if (isPressed && !lastKeyState) {
                    TeslaMod.onKeyPress();
                }

                lastKeyState = isPressed;
            }
        } catch (Exception e) {
        }
    }
}