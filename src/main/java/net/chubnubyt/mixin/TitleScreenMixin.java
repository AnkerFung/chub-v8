package net.chubnubyt.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin
{
	@Nullable
	@Shadow
	private String splashText = null;

	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info)
	{
		splashText = "chub onw youa nd youre pc";
	}
}
