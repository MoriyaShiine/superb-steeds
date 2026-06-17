/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.common;

import moriyashiine.strawberrylib.api.SLib;
import moriyashiine.superbsteeds.common.event.FeedMountedHorseEvent;
import moriyashiine.superbsteeds.common.init.SuperbSteedsTriggers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;

public class SuperbSteeds implements ModInitializer {
	public static final String MOD_ID = "superb-steeds";

	@Override
	public void onInitialize() {
		SLib.init(MOD_ID);
		initRegistries();
		initEvents();
	}

	public static Identifier id(String value) {
		return Identifier.fromNamespaceAndPath(MOD_ID, value);
	}

	private void initRegistries() {
		SuperbSteedsTriggers.init();
	}

	private void initEvents() {
		FeedMountedHorseEvent.init();
	}
}