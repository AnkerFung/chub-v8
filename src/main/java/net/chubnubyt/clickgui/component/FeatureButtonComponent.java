package net.chubnubyt.clickgui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chubnubyt.clickgui.window.Window;
import net.chubnubyt.util.RenderUtil;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class FeatureButtonComponent extends Component
{

	private final Supplier<Text> textSupplier;

	public FeatureButtonComponent(Window parent, Text text, int x, int y)
	{
		super(parent, x, y);
		this.textSupplier = () -> text;
	}

	public FeatureButtonComponent(Window parent, Supplier<Text> textSupplier, int x, int y)
	{
		super(parent, x, y);
		this.textSupplier = textSupplier;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		double parentX = parent.getX();
		double parentY = parent.getY();
		double parentWidth = parent.getWidth();
		double parentLength = parent.getLength();
		double parentX2 = parent.getX() + parentWidth;
		double parentY2 = parent.getY() + parentLength;
		double x = getX() + parentX;
		double y = getY() + parentY;
		double x2 = parentX2 - getX();
		double y2 = Math.min(y + 20, parentY2);
		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0.8f, 0.8f, 0.8f, 0.8f);
		if (parent == parent.parent.getTopWindow() && RenderUtil.isHoveringOver(mouseX, mouseY, x, y, x2, y2))
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.8f);
		RenderUtil.drawQuad(x, y, x2, y2, matrices);
	}
}
