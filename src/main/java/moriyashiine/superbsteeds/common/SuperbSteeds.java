/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.common;

import moriyashiine.strawberrylib.api.SLib;
import moriyashiine.superbsteeds.common.event.FeedMountedHorseEvent;
import moriyashiine.superbsteeds.common.init.ModCriterion;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.util.Identifier;

public class SuperbSteeds implements ModInitializer {
	public static final String MOD_ID = "superbsteeds";

	@Override
	public void onInitialize() {
		SLib.init(MOD_ID);
		initRegistries();
		initEvents();
	}

	public static Identifier id(String value) {
		return Identifier.of(MOD_ID, value);
	}

	private void initRegistries() {
		ModCriterion.init();
	}

	private void initEvents() {
		UseItemCallback.EVENT.register(new FeedMountedHorseEvent());
	}
}