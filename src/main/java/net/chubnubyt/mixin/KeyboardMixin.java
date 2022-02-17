package net.chubnubyt.mixin;

import net.chubnubyt.ChubClient;
import net.chubnubyt.render.screen.clickgui.GuiScreen;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin
{
	@Inject(at = @At("HEAD"), method = "onKey(JIIII)V")
	private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci)
	{
		MinecraftClient mc = ChubClient.MC;
		if (window == mc.getWindow().getHandle())
		{
			if (key == GLFW.GLFW_KEY_RIGHT_SHIFT && action == GLFW.GLFW_PRESS)
			{
				if (mc.currentScreen == null)
					mc.setScreen(new GuiScreen());
			}
		}
	}
}
