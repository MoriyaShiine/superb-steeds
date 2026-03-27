/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.mixin;

import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import net.minecraft.world.entity.animal.equine.AbstractChestedHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractChestedHorse.class)
public class AbstractChestedHorseMixin {
	@ModifyArg(method = "createBaseChestedHorseAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;add(Lnet/minecraft/core/Holder;D)Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;", ordinal = 0))
	private static double superbsteeds$baseHealth(double baseValue) {
		return HorseAttributesComponent.BASE_HORSE_SPEED;
	}

	@ModifyArg(method = "createBaseChestedHorseAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;add(Lnet/minecraft/core/Holder;D)Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;", ordinal = 1))
	private static double superbsteeds$baseSpeed(double baseValue) {
		return HorseAttributesComponent.BASE_HORSE_JUMP;
	}
}
