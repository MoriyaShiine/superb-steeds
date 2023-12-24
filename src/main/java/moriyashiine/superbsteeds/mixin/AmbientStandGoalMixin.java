/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.mixin;

import net.minecraft.entity.ai.goal.AmbientStandGoal;
import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AmbientStandGoal.class)
public class AmbientStandGoalMixin {
	@Shadow
	@Final
	private AbstractHorseEntity entity;

	@Inject(method = "canStart", at = @At("RETURN"), cancellable = true)
	private void superbsteeds$preventSaddleBucking(CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValueZ() && entity.isSaddled()) {
			cir.setReturnValue(false);
		}
	}
}
