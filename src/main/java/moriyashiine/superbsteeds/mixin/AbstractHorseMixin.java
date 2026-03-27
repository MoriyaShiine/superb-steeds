/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Mob {
	@Shadow
	public abstract boolean isTamed();

	protected AbstractHorseMixin(EntityType<? extends Mob> type, Level level) {
		super(type, level);
	}

	@Inject(method = "generateMaxHealth", at = @At("HEAD"), cancellable = true)
	private static void superbsteeds$baseHealth(IntUnaryOperator integerByBoundProvider, CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(HorseAttributesComponent.BASE_HEALTH);
	}

	@Inject(method = "generateJumpStrength", at = @At("HEAD"), cancellable = true)
	private static void superbsteeds$baseJump(DoubleSupplier probabilityProvider, CallbackInfoReturnable<Double> cir) {
		cir.setReturnValue(HorseAttributesComponent.BASE_HORSE_JUMP);
	}

	@Inject(method = "generateSpeed", at = @At("HEAD"), cancellable = true)
	private static void superbsteeds$baseSpeed(DoubleSupplier probabilityProvider, CallbackInfoReturnable<Double> cir) {
		cir.setReturnValue(HorseAttributesComponent.BASE_HORSE_SPEED);
	}

	@WrapOperation(method = "setOffspringAttribute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/equine/AbstractHorse;createOffspringAttribute(DDDDLnet/minecraft/util/RandomSource;)D"))
	private double superbsteeds$baseStats(double parentAValue, double parentBValue, double attributeRangeMin, double attributeRangeMax, RandomSource random, Operation<Double> original, AgeableMob partner, AbstractHorse baby, Holder<Attribute> attribute) {
		if (attribute == Attributes.MAX_HEALTH) {
			return HorseAttributesComponent.BASE_HEALTH;
		}
		if (attribute == Attributes.MOVEMENT_SPEED) {
			return HorseAttributesComponent.BASE_HORSE_SPEED;
		}
		if (attribute == Attributes.JUMP_STRENGTH) {
			return HorseAttributesComponent.BASE_HORSE_JUMP;
		}
		return original.call(parentAValue, parentBValue, attributeRangeMin, attributeRangeMax, random);
	}

	@ModifyExpressionValue(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"))
	private int superbsteeds$preventSaddleBucking(int original) {
		if (isSaddled()) {
			return -1;
		}
		return original;
	}

	@ModifyExpressionValue(method = "getControllingPassenger", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/equine/AbstractHorse;isSaddled()Z"))
	private boolean superbsteeds$allowMovingWithoutSaddle(boolean original) {
		return isTamed();
	}

	@Inject(method = "canJump", at = @At("HEAD"), cancellable = true)
	private void superbsteeds$allowJumpingWithoutSaddle(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}

	@ModifyExpressionValue(method = "onPlayerJump", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/equine/AbstractHorse;isSaddled()Z"))
	private boolean superbsteeds$allowJumpingWithoutSaddle(boolean original) {
		return true;
	}
}
