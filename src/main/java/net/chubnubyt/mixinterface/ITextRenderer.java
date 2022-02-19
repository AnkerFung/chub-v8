package net.chubnubyt.mixinterface;

import net.minecraft.text.StringVisitable;
import net.minecraft.util.math.Matrix4f;

public interface ITextRenderer
{
	void drawTrimmed(StringVisitable text, int x, int y, int maxWidth, int color, Matrix4f matrix);
}
