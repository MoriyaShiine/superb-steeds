/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import moriyashiine.superbsteeds.common.init.ModEntityComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends LivingEntity {
	@Shadow
	public abstract boolean isSaddled();

	protected AbstractHorseEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "getChildHealthBonus", at = @At("HEAD"), cancellable = true)
	private static void superbsteeds$baseHealth(IntUnaryOperator randomIntGetter, CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(HorseAttributesComponent.BASE_HEALTH);
	}

	@Inject(method = "getChildMovementSpeedBonus", at = @At("HEAD"), cancellable = true)
	private static void superbsteeds$baseSpeed(DoubleSupplier randomDoubleGetter, CallbackInfoReturnable<Double> cir) {
		cir.setReturnValue(HorseAttributesComponent.BASE_HORSE_SPEED);
	}

	@Inject(method = "getChildJumpStrengthBonus", at = @At("HEAD"), cancellable = true)
	private static void superbsteeds$baseJump(DoubleSupplier randomDoubleGetter, CallbackInfoReturnable<Double> cir) {
		cir.setReturnValue(HorseAttributesComponent.BASE_HORSE_JUMP);
	}

	@WrapOperation(method = "setChildAttribute", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;calculateAttributeBaseValue(DDDDLnet/minecraft/util/math/random/Random;)D"))
	private double superbsteeds$baseStats(double parentBase, double otherParentBase, double min, double max, Random random, Operation<Double> original, PassiveEntity other, AbstractHorseEntity child, RegistryEntry<EntityAttribute> attribute) {
		HorseAttributesComponent horseAttributesComponent = ModEntityComponents.HORSE_ATTRIBUTES.getNullable(child);
		if (horseAttributesComponent != null) {
			if (attribute == EntityAttributes.GENERIC_MAX_HEALTH) {
				return HorseAttributesComponent.BASE_HEALTH;
			}
			if (attribute == EntityAttributes.GENERIC_MOVEMENT_SPEED) {
				return HorseAttributesComponent.BASE_HORSE_SPEED;
			}
			if (attribute == EntityAttributes.GENERIC_JUMP_STRENGTH) {
				return HorseAttributesComponent.BASE_HORSE_JUMP;
			}
		}
		return original.call(parentBase, otherParentBase, min, max, random);
	}

	@ModifyExpressionValue(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I"))
	private int superbsteeds$preventSaddleBucking(int value) {
		if (isSaddled()) {
			return -1;
		}
		return value;
	}

	@ModifyExpressionValue(method = "getControllingPassenger", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;isSaddled()Z"))
	private boolean superbsteeds$allowMovingWithoutSaddle(boolean value) {
		return true;
	}

	@Inject(method = "canJump", at = @At("HEAD"), cancellable = true)
	private void superbsteeds$allowJumpingWithoutSaddle(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}

	@ModifyExpressionValue(method = "setJumpStrength", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;isSaddled()Z"))
	private boolean superbsteeds$allowJumpingWithoutSaddle(boolean value) {
		return true;
	}
}
