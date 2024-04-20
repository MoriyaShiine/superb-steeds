/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.mixin;

import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SkeletonHorseEntity.class)
public class SkeletonHorseEntityMixin {
	@ModifyArg(method = "createSkeletonHorseAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", ordinal = 0))
	private static double superbsteeds$flatHealth(double value) {
		return HorseAttributesComponent.MAX_HEALTH;
	}
}
