/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.common.init;

import net.minecraft.advancement.criterion.TickCriterion;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerCriterion;

public class ModCriterion {
	public static final TickCriterion FULLY_TRAIN_HORSE = registerCriterion("fully_train_horse", new TickCriterion());

	public static void init() {
	}
}
