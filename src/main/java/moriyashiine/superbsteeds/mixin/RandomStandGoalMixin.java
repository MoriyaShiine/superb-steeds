/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.ai.goal.RandomStandGoal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RandomStandGoal.class)
public class RandomStandGoalMixin {
	@Shadow
	@Final
	private AbstractHorse horse;

	@ModifyReturnValue(method = "canUse", at = @At("RETURN"))
	private boolean superbsteeds$preventSaddleBucking(boolean original) {
		if (original && horse.isSaddled()) {
			return false;
		}
		return original;
	}
}
