/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.mixin;

import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import net.minecraft.entity.mob.ZombieHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ZombieHorseEntity.class)
public class ZombieHorseEntityMixin {
	@ModifyArg(method = "createZombieHorseAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/registry/entry/RegistryEntry;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", ordinal = 0))
	private static double superbsteeds$baseHealth(double value) {
		return HorseAttributesComponent.BASE_HEALTH;
	}

	@ModifyArg(method = "createZombieHorseAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/registry/entry/RegistryEntry;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", ordinal = 1))
	private static double superbsteeds$baseSpeed(double value) {
		return HorseAttributesComponent.BASE_HORSE_SPEED;
	}
}
