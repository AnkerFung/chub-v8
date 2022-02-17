package net.chubnubyt.clickgui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chubnubyt.ChubClient;
import net.chubnubyt.clickgui.component.FeatureButtonComponent;
import net.chubnubyt.clickgui.window.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ClickGui
{

	private final ArrayList<Window> windows = new ArrayList<>();
	private Window draggingWindow = null;
	private double globalShiftX = 0;
	private double globalShiftY = 0;

	public void init()
	{
		Window window = new Window(this, 10, 10, 100, 100);
		window.addComponent(new FeatureButtonComponent(window, new LiteralText("aaa"), 10, 10));
		windows.add(window);
		window = new Window(this, 10, 10, 100, 100);
		window.addComponent(new FeatureButtonComponent(window, new LiteralText("aaa"), 10, 10));
		windows.add(window);
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		matrices.push();
		matrices.translate(globalShiftX, globalShiftY, 0);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderSystem.lineWidth(1);
		for (Window window : windows)
		{
			window.render(matrices, (int) (mouseX - globalShiftX), (int) (mouseY - globalShiftY), delta);
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		matrices.pop();
	}

	public void handleMouseClicked(double mouseX, double mouseY, int button)
	{
		if (button != GLFW.GLFW_MOUSE_BUTTON_1)
			return;
		int clickedWindowIndex = -1;
		for (int i = windows.size() - 1; i >= 0; i--)
		{
			if (windows.get(i).isHoveringOver(mouseX, mouseY))
			{
				clickedWindowIndex = i;
				break;
			}
		}

		if (clickedWindowIndex == -1)
			return;

		Window clickedWindow = windows.get(clickedWindowIndex);

		if (clickedWindow.canDrag(mouseX - globalShiftX, mouseY - globalShiftY))
			draggingWindow = clickedWindow;

		windows.remove(clickedWindowIndex);
		windows.add(clickedWindow);
	}

	public void handleMouseReleased(double mouseX, double mouseY, int button)
	{
		if (button == GLFW.GLFW_MOUSE_BUTTON_1)
			draggingWindow = null;
	}

	public void handleMouseScrolled(double mouseX, double mouseY, double amount)
	{

	}

	public void handleMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
	{
		if (GLFW.glfwGetKey(ChubClient.MC.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS)
		{
			globalShiftX += deltaX;
			globalShiftY += deltaY;
			return;
		}
		if (draggingWindow != null)
		{
			if (button == GLFW.GLFW_MOUSE_BUTTON_1)
			{
				draggingWindow.setX(draggingWindow.getX() + deltaX);
				draggingWindow.setY(draggingWindow.getY() + deltaY);
			}
		}
	}

	public Window getTopWindow()
	{
		int size = windows.size();
		if (size == 0)
			return null;
		return windows.get(size - 1);
	}
}
