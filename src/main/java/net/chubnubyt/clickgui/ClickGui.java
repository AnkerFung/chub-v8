package net.chubnubyt.clickgui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chubnubyt.ChubClient;
import net.chubnubyt.clickgui.component.ButtonComponent;
import net.chubnubyt.clickgui.window.Window;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ClickGui
{

	private final ArrayList<Window> windows = new ArrayList<>();
	private Window draggingWindow = null;
	private double globalShiftX = 0;
	private double globalShiftY = 0;

	private static final Identifier chubLogo = new Identifier("chub", "logo.png");

	public void init()
	{
		Window window = new Window(this, 100, 100, 100, 400);
		Runnable action = () -> System.out.println("clicked");
		String[] names =
				{
						"oolfa moment",
						"anti ddos",
						"ping bypass",
						"consent bypass",
						"auto barrage",
						"anti dtap",
						"auto cope",
						"auto gF",
						"minehut crasher",
						"pinger",
						"anti pop",
						"auto strafe",
						"auto ddos",
						"rblxnoob bypass",
						"marlow bypass",
						"notablenoah bypass",
						"screenshare bypass",
						"anti screenshare",
						"pot macro",
						"safe anchor macro",
						"femboy detector",
						"pp macro",
						"pedo mode",
						"middle click gap",
						"protonblitz totem",
						"auto run",
						"auto drain",
						"robux dupe"
				};

		double y = 20;
		for (String name : names)
		{
			window.addComponent(new ButtonComponent(window, new LiteralText(name), action, 10, (int) y));
			y += 20;
		}

		windows.add(window);
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		//renderLogo(matrices);
		matrices.push();
		matrices.translate(globalShiftX, globalShiftY, 0);
		for (Window window : windows)
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			RenderSystem.lineWidth(1);
			window.render(matrices, (int) (mouseX - globalShiftX), (int) (mouseY - globalShiftY), delta);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
		}
		matrices.pop();
	}

	public void handleMouseClicked(double mouseX, double mouseY, int button)
	{
		int clickedWindowIndex = -1;
		for (int i = windows.size() - 1; i >= 0; i--)
		{
			if (windows.get(i).isHoveringOver(mouseX - globalShiftX, mouseY - globalShiftY))
			{
				clickedWindowIndex = i;
				break;
			}
		}

		if (clickedWindowIndex == -1)
			return;

		Window clickedWindow = windows.get(clickedWindowIndex);

		if (clickedWindow == getTopWindow())
		{
			clickedWindow.onMouseClicked(mouseX - globalShiftX, mouseY - globalShiftY, button);
		}

		if (button != GLFW.GLFW_MOUSE_BUTTON_1)
			return;
		if (!windows.contains(clickedWindow))
			return;

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
		Window top = getTopWindow();
		if (top == null)
			return;
		top.onMouseScrolled(mouseX, mouseY, amount);
	}

	public void handleMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
	{
		if (button != GLFW.GLFW_MOUSE_BUTTON_1)
			return;
		if (GLFW.glfwGetKey(ChubClient.MC.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS)
		{
			globalShiftX += deltaX;
			globalShiftY += deltaY;
			return;
		}
		if (draggingWindow != null)
		{
			draggingWindow.setX(draggingWindow.getX() + deltaX);
			draggingWindow.setY(draggingWindow.getY() + deltaY);
		}
	}

	public Window getTopWindow()
	{
		int size = windows.size();
		if (size == 0)
			return null;
		return windows.get(size - 1);
	}

	public void close(Window window)
	{
		windows.remove(window);
	}

	public void renderLogo(MatrixStack matrices)
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, chubLogo);
		DrawableHelper.drawTexture(matrices, 0, 3, 0, 0, 80, 60, 80, 60);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
	}
}