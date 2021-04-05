/*
* #######################################################################
*        ____                  ____     _          __
*       / __ \  ___  _   __   / __ \   (_)  ___   / /_  ___    _____
*      / / / / / _ \| | / /  / /_/ /  / /  / _ \ / __/ / _ \  / ___/
*     / /_/ / /  __/| |/ /  / ____/  / /  /  __// /_  /  __/ / /
*    /_____/  \___/ |___/  /_/      /_/   \___/ \__/  \___/ /_/
*   
*                    This mod was created by DevPieter
*                              © DevPieter
*
*      DevPieter.nl    github.com/DevPieter    twitter.com/DevPieter
* #######################################################################
*/

package nl.devpieter.quickcoordinates;

import java.text.DecimalFormat;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class QuickCoordinates implements ModInitializer {

	private static KeyBinding showCords = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.quickcoordinates.show_coordinates", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "key.category.quickcoordinates.quick_coordinates"));

	private static MinecraftClient minecraft;
	private static DecimalFormat df;
	
	private static int screenposX, screenposY;

	@Override
	public void onInitialize() {
		screenposX = 1;
		screenposY = 1;
		
		df = new DecimalFormat("#0.0");
		minecraft = MinecraftClient.getInstance();
		HudRenderCallback.EVENT.register(new HudRenderCallback() {

			@Override
			public void onHudRender(MatrixStack matrixStack, float tickDelta) {
				
				
				if (showCords.isPressed()) {
					minecraft.getTextureManager().bindTexture(new Identifier("quickcoordinates", "textures/gui/quickcoordinates.png"));
					DrawableHelper.drawTexture(matrixStack, screenposX, screenposY, 0, 0, 185, 46, 185, 46);

					Identifier biome = minecraft.player.world.getRegistryManager().get(Registry.BIOME_KEY).getId(minecraft.player.world.getBiome(minecraft.player.getBlockPos()));
										
					minecraft.textRenderer.draw(matrixStack, String.format("Block: §3%s %s %s", df.format(minecraft.player.getPos().x), df.format(minecraft.player.getPos().y), df.format(minecraft.player.getPos().z)), screenposX + 11, screenposY + 9, 0x35c461);
					minecraft.textRenderer.draw(matrixStack, String.format("Biome: §3%s ", biome.toString().replace(biome.getNamespace() + ":", "").replace("_", " ")) , screenposX + 11, screenposY + 19, 0x35c461);
					minecraft.textRenderer.draw(matrixStack, String.format("Facing: §3%s", minecraft.player.getHorizontalFacing()) , screenposX + 11, screenposY + 29, 0x35c461);
					}
				
			}
		});
	}
	
}
