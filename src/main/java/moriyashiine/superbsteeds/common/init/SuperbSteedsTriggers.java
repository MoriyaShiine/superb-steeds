/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.common.init;

import net.minecraft.advancements.triggers.PlayerTrigger;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerTrigger;

public class SuperbSteedsTriggers {
	public static final PlayerTrigger FULLY_TRAIN_HORSE = registerTrigger("fully_train_horse", new PlayerTrigger());

	public static void init() {
	}
}
