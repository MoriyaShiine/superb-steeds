/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.client.event;

import moriyashiine.superbsteeds.common.SuperbSteeds;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class HorseArmorTooltipEvent implements ItemTooltipCallback {
	@Override
	public void getTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
		if (stack.getItem() instanceof HorseArmorItem horseArmor) {
			lines.add(1, Text.empty());
			lines.add(2, Text.translatable("item.modifiers." + SuperbSteeds.MOD_ID + ".horse").formatted(Formatting.GRAY));
			lines.add(3, Text.translatable("attribute.modifier.plus.0", ItemStack.MODIFIER_FORMAT.format(horseArmor.getBonus()), Text.translatable("attribute.name.generic.armor")).formatted(Formatting.BLUE));
		}
	}
}
