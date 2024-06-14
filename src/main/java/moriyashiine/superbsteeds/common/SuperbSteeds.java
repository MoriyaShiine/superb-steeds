/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.common;

import moriyashiine.superbsteeds.common.event.FeedMountedHorseEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.util.Identifier;

public class SuperbSteeds implements ModInitializer {
	public static final String MOD_ID = "superbsteeds";

	@Override
	public void onInitialize() {
		UseItemCallback.EVENT.register(new FeedMountedHorseEvent());
	}

	public static Identifier id(String value) {
		return Identifier.of(MOD_ID, value);
	}
}