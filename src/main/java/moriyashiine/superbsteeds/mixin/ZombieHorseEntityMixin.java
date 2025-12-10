/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.mixin;

import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import net.minecraft.entity.mob.ZombieHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.DoubleSupplier;

@Mixin(ZombieHorseEntity.class)
public class ZombieHorseEntityMixin {
	@ModifyArg(method = "createZombieHorseAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/registry/entry/RegistryEntry;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;"))
	private static double superbsteeds$baseHealth(double value) {
		return HorseAttributesComponent.BASE_HEALTH;
	}

	@Inject(method = "getBaseJumpStrength", at = @At("HEAD"), cancellable = true)
	private static void superbsteeds$baseJump(DoubleSupplier randomDoubleGetter, CallbackInfoReturnable<Double> cir) {
		cir.setReturnValue(HorseAttributesComponent.BASE_HORSE_JUMP);
	}

	@Inject(method = "getBaseMovementSpeed", at = @At("HEAD"), cancellable = true)
	private static void superbsteeds$baseSpeed(DoubleSupplier randomDoubleGetter, CallbackInfoReturnable<Double> cir) {
		cir.setReturnValue(HorseAttributesComponent.BASE_HORSE_SPEED);
	}
}
