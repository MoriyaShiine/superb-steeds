/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.mixin;

import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseArmorItem.class)
public class HorseArmorItemMixin {
	@Shadow
	@Mutable
	@Final
	private int bonus;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void superbsteeds$swapGoldAndIronValues(int bonus, String name, Item.Settings settings, CallbackInfo ci) {
		if (name != null) {
			if (name.equals("iron")) {
				this.bonus = 7;
			} else if (name.equals("gold")) {
				this.bonus = 5;
			}
		}
	}
}
