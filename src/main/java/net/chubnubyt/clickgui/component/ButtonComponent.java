package net.chubnubyt.clickgui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chubnubyt.ChubClient;
import net.chubnubyt.clickgui.window.Window;
import net.chubnubyt.mixinterface.ITextRenderer;
import net.chubnubyt.util.RenderUtil;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class ButtonComponent extends Component
{

	private final Supplier<Text> textSupplier;
	private final Runnable action;

	public ButtonComponent(Window parent, Text text, Runnable action, int x, int y)
	{
		super(parent, x, y);
		this.textSupplier = () -> text;
		this.action = action;
	}

	public ButtonComponent(Window parent, Supplier<Text> textSupplier, Runnable action, int x, int y)
	{
		super(parent, x, y);
		this.textSupplier = textSupplier;
		this.action = action;
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
		double y = Math.max(getY() + parentY, parentY);
		double x2 = parentX2 - getX();
		double y2 = Math.min(getY() + parentY + 10, parentY2);
		if (getY() + parentY - parentY <= 0)
			return;
		if (parentY2 - (getY() + parentY) <= 0)
			return;
		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0.8f, 0.8f, 0.8f, 0.8f);
		if (parent == parent.parent.getTopWindow() && RenderUtil.isHoveringOver(mouseX, mouseY, x, y, x2, y2))
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderUtil.drawQuad(x, y, x2, y2, matrices);
		double textX = x + 5;
		double textY = y;
		ITextRenderer textRenderer = (ITextRenderer) ChubClient.MC.textRenderer;
		textRenderer.drawTrimmed(textSupplier.get(), (float) textX, (float) textY, (int) (x2 - textX), 0x0, matrices.peek().getModel());
	}

	@Override
	public void onMouseClicked(double mouseX, double mouseY, int button)
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
		if (RenderUtil.isHoveringOver(mouseX, mouseY, x, y, x2, y2))
		{
			action.run();
		}
	}
}
