/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.SkeletonHorse;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {
	@SuppressWarnings("ConstantValue")
	@WrapOperation(method = "getBlockSpeedFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;"))
	private Block superbsteeds$skeletonHorseSoulSandSpeed(BlockState instance, Operation<Block> original) {
		Block block = original.call(instance);
		if (block == Blocks.SOUL_SAND && (Entity) (Object) this instanceof SkeletonHorse) {
			return Blocks.SOUL_SOIL;
		}
		return block;
	}
}
