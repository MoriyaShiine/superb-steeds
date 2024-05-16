/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.mixin;

import net.minecraft.entity.passive.CamelEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CamelEntity.class)
public class CamelEntityMixin {
	@Unique
	private static final double BASE_CAMEL_SPEED = 0.0766, BASE_CAMEL_JUMP = 0.3933;

	@ModifyArg(method = "createCamelAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/registry/entry/RegistryEntry;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", ordinal = 1))
	private static double superbsteeds$baseHealth(double value) {
		return BASE_CAMEL_SPEED;
	}

	@ModifyArg(method = "createCamelAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/registry/entry/RegistryEntry;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", ordinal = 2))
	private static double superbsteeds$baseSpeed(double value) {
		return BASE_CAMEL_JUMP;
	}
}
