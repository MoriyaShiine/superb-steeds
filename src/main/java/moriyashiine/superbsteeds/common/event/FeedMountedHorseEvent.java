/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.common.event;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FeedMountedHorseEvent implements UseItemCallback {
	@Override
	public ActionResult interact(PlayerEntity player, World world, Hand hand) {
		if (player.getPitch() > 45 && player.getControllingVehicle() instanceof AbstractHorseEntity horse && horse.getHealth() < horse.getMaxHealth() && horse.isTame()) {
			ItemStack stack = player.getStackInHand(hand);
			if (horse.isBreedingItem(stack) && !(stack.getItem() instanceof BlockItem) && horse.interactHorse(player, stack) != ActionResult.PASS) {
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}
}
