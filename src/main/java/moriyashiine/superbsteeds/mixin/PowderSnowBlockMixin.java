/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
	@ModifyReturnValue(method = "canWalkOnPowderSnow", at = @At("RETURN"))
	private static boolean superbsteeds$leatherHorseArmor(boolean original, Entity entity) {
		if (!original && entity instanceof HorseEntity horse && horse.getBodyArmor().isOf(Items.LEATHER_HORSE_ARMOR)) {
			return true;
		}
		return original;
	}
}
