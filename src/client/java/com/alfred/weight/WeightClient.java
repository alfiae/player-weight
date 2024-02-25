package com.alfred.weight;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class WeightClient implements ClientModInitializer {
	public static float currentWeight = 0.0f;
	public static float maxWeight = 300.0f;
	public static boolean affectsCreative = false;
	private static final Identifier[] ICONS = new Identifier[] {
			WeightMod.identifier("t0"),
			WeightMod.identifier("t1"),
			WeightMod.identifier("t2"),
			WeightMod.identifier("t3"),
			WeightMod.identifier("t4"),
			WeightMod.identifier("t5"),
			WeightMod.identifier("t6"),
			WeightMod.identifier("t7"),
			WeightMod.identifier("t8"),
			WeightMod.identifier("t9"),
			WeightMod.identifier("t10"),
			WeightMod.identifier("t11"),
			WeightMod.identifier("t12")
	};

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(WeightMod.WEIGHT_PACKET, (client, handler, buf, responseSender) ->
			currentWeight = buf.readFloat()
		);
		ClientPlayNetworking.registerGlobalReceiver(WeightMod.WEIGHT_MAX_PACKET, (client, handler, buf, responseSender) ->
			maxWeight = buf.readFloat()
		);
		ClientPlayNetworking.registerGlobalReceiver(WeightMod.CREATIVE_MODE_UPDATE, (client, handler, buf, responseSender) ->
			affectsCreative = buf.readBoolean()
		);
	}

	public static void render(DrawContext context, TextRenderer textRenderer, int x, int y) {
		WeightConfig.DisplayType type = WeightConfig.getInstance().displayType;
		if (type == WeightConfig.DisplayType.NUMBERS) // TODO: make text a smaller font
			context.drawText(textRenderer, WeightClient.currentWeight + "/" + WeightClient.maxWeight, x + 147 - (textRenderer.getWidth(WeightClient.currentWeight + "/" + WeightClient.maxWeight) / 2), y + 68, 4210752, false);
		else if (type == WeightConfig.DisplayType.ICON)
			context.drawGuiTexture(ICONS[MathHelper.clamp(Math.round(WeightClient.currentWeight / (WeightClient.maxWeight != 0 ? WeightClient.maxWeight : 1) * (ICONS.length - 2)), 0, ICONS.length - 1)], x + 128, y + 61, 18, 18);
	}
}