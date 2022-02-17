package net.chubnubyt;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class Main implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		ChubClient.INSTANCE.init();
	}
}
