package net.chubnubyt.mixin;

import net.chubnubyt.ChubClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin
{
	@Inject(
			at = {@At(value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V",
					ordinal = 4)},
			method = {"render(Lnet/minecraft/client/util/math/MatrixStack;F)V"})
	private void onRender(MatrixStack matrixStack, float partialTicks,
	                      CallbackInfo ci)
	{
		boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);

		ChubClient.INSTANCE.getClickGui().renderLogo(matrixStack);

		if (blend)
			GL11.glEnable(GL11.GL_BLEND);
		else
			GL11.glDisable(GL11.GL_BLEND);
	}
}
