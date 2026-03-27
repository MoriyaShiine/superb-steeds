/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.common.event;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FeedMountedHorseEvent implements UseItemCallback {
	@Override
	public InteractionResult interact(Player player, Level level, InteractionHand hand) {
		if (player.getXRot() > 45 && player.getControlledVehicle() instanceof AbstractHorse horse && horse.getHealth() < horse.getMaxHealth() && horse.isTamed()) {
			ItemStack stack = player.getItemInHand(hand);
			if (horse.isFood(stack) && !(stack.getItem() instanceof BlockItem) && horse.fedFood(player, stack).consumesAction()) {
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
