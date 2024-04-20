/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.IntUnaryOperator;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin {
	@Unique
	private static final float MAX_HEALTH = 30;

	@Shadow
	public abstract boolean isSaddled();

	@Inject(method = "getChildHealthBonus", at = @At("HEAD"), cancellable = true)
	private static void superbsteeds$flatHealth(IntUnaryOperator randomIntGetter, CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(MAX_HEALTH);
	}

	@Inject(method = "setChildAttribute", at = @At("HEAD"), cancellable = true)
	private void superbsteeds$flatHealth(PassiveEntity other, AbstractHorseEntity child, EntityAttribute attribute, double min, double max, CallbackInfo ci) {
		if (attribute == EntityAttributes.GENERIC_MAX_HEALTH) {
			child.getAttributeInstance(attribute).setBaseValue(MAX_HEALTH);
			ci.cancel();
		}
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
