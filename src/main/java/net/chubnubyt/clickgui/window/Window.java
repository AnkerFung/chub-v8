package net.chubnubyt.clickgui.window;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chubnubyt.clickgui.ClickGui;
import net.chubnubyt.clickgui.component.Component;
import net.chubnubyt.util.RenderUtil;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public class Window
{
	public final ClickGui parent;
	private double x, y;
	private double width, length;
	private ArrayList<Component> components = new ArrayList<>();
	private boolean isDraggable = true;
	private boolean pinnable = true;
	private boolean closable = true;
	private boolean draggable = true;

	public Window(ClickGui parent, int x, int y, int width, int length)
	{
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.width = width;
		this.length = length;
	}

	public void addComponent(Component component)
	{
		components.add(component);
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0.4f, 0.4f, 0.4f, 0.8f);
		RenderUtil.drawQuad(x, y, x + width, y + length, matrices);
		for (Component component : components)
		{
			component.render(matrices, mouseX, mouseY, delta);
		}
		if (draggable)
		{
			RenderSystem.setShaderColor(0, 0, 0, 1.0f);
			if (parent.getTopWindow() == this)
				RenderSystem.setShaderColor(0.4f, 1.0f, 0.4f, 1.0f);
			RenderUtil.drawQuad(x, y, x + width, y + 10, matrices);
		}
	}

	public void onMouseClicked(double mouseX, double mouseY, int button)
	{
		for (Component component : components)
		{
			component.onMouseClicked(mouseX, mouseY, button);
		}
	}

	public boolean canDrag(double mouseX, double mouseY)
	{
		if (!draggable)
			return false;
		return RenderUtil.isHoveringOver(mouseX, mouseY, x, y, x + width, y + 10);
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getLength()
	{
		return length;
	}

	public void setLength(double length)
	{
		this.length = length;
	}

	public boolean isDraggable()
	{
		return isDraggable;
	}

	public void setIsDraggable(boolean isDraggable)
	{
		this.isDraggable = isDraggable;
	}

	public boolean isHoveringOver(double mouseX, double mouseY)
	{
		return RenderUtil.isHoveringOver(mouseX, mouseY, x, y, x + width, y + length);
	}
}
