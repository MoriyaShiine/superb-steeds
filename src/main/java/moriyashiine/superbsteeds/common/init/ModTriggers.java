/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.common.init;

import net.minecraft.advancements.criterion.PlayerTrigger;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerTrigger;

public class ModTriggers {
	public static final PlayerTrigger FULLY_TRAIN_HORSE = registerTrigger("fully_train_horse", new PlayerTrigger());

	public static void init() {
	}
}
