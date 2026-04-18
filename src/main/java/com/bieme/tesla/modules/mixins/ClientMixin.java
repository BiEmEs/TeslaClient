package com.bieme.tesla.modules.mixins;

import com.bieme.tesla.TeslaMod;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class ClientMixin {

    @Inject(method = "keyPress", at = @At("HEAD"))
    private void onKeyPress(long window, int key, KeyEvent event, CallbackInfo ci) {
    }
}