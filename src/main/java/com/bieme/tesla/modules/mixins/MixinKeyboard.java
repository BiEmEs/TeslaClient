package com.bieme.tesla.modules.mixins;

import com.bieme.tesla.TeslaMod;
import net.minecraft.client.KeyboardHandler;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class MixinKeyboard {

    @Inject(method = "keyPress", at = @At("HEAD"))
    private void onKeyPress(long window, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (key == GLFW.GLFW_KEY_RIGHT_SHIFT && action == GLFW.GLFW_PRESS) {
            TeslaMod.toggleGui();
        }
    }
}
