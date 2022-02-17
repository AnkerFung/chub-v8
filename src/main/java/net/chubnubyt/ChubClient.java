package net.chubnubyt;

import net.chubnubyt.clickgui.ClickGui;
import net.minecraft.client.MinecraftClient;

public enum ChubClient
{
	INSTANCE;

	public static final MinecraftClient MC = MinecraftClient.getInstance();

	private ClickGui clickGui;
	private boolean clickGuiInitialized = false;

	public void init()
	{
		clickGui = new ClickGui();
	}

	public ClickGui getClickGui()
	{
		if (!clickGuiInitialized)
		{
			clickGuiInitialized = true;
			clickGui.init();
		}
		return clickGui;
	}
}
