package moriyashiine.superbsteeds.mixin;

import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import net.minecraft.world.entity.animal.equine.ZombieHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.DoubleSupplier;

@Mixin(ZombieHorse.class)
public class ZombieHorseMixin {
	@Inject(method = "generateZombieHorseJumpStrength", at = @At("HEAD"), cancellable = true)
	private static void superbsteeds$baseJump(DoubleSupplier probabilityProvider, CallbackInfoReturnable<Double> cir) {
		cir.setReturnValue(HorseAttributesComponent.BASE_HORSE_JUMP);
	}

	@Inject(method = "generateZombieHorseSpeed", at = @At("HEAD"), cancellable = true)
	private static void superbsteeds$baseSpeed(DoubleSupplier probabilityProvider, CallbackInfoReturnable<Double> cir) {
		cir.setReturnValue(HorseAttributesComponent.BASE_HORSE_SPEED);
	}
}
