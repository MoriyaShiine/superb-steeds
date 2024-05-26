/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.ai.goal.AmbientStandGoal;
import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AmbientStandGoal.class)
public class AmbientStandGoalMixin {
	@Shadow
	@Final
	private AbstractHorseEntity entity;

	@ModifyReturnValue(method = "canStart", at = @At("RETURN"))
	private boolean superbsteeds$preventSaddleBucking(boolean original) {
		if (original && entity.isSaddled()) {
			return false;
		}
		return original;
	}
}
