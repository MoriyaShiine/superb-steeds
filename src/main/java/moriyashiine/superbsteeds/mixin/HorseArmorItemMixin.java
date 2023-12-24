/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.mixin;

import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HorseArmorItem.class)
public class HorseArmorItemMixin {
	@Unique
	private String name;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void superbsteeds$swapGoldAndIronValues(int bonus, String name, Item.Settings settings, CallbackInfo ci) {
		this.name = name;
	}

	@Inject(method = "getBonus", at = @At("HEAD"), cancellable = true)
	private void superbsteeds$swapGoldAndIronValues(CallbackInfoReturnable<Integer> cir) {
		if (name.equals("iron")) {
			cir.setReturnValue(7);
		} else if (name.equals("gold")) {
			cir.setReturnValue(5);
		}
	}
}
