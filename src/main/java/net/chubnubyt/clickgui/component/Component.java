package net.chubnubyt.clickgui.component;

import net.chubnubyt.clickgui.window.Window;
import net.minecraft.client.util.math.MatrixStack;

public abstract class Component
{

	public final Window parent;
	private double x, y;

	protected Component(Window parent, double x, double y)
	{
		this.parent = parent;
		this.x = x;
		this.y = y;
	}

	public abstract void render(MatrixStack matrices, int mouseX, int mouseY, float delta);

	public abstract void onMouseClicked(double mouseX, double mouseY, int button);

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public void setY(double y)
	{
		this.y = y;
	}
}
