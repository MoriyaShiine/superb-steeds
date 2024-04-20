/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {
	@SuppressWarnings("ConstantValue")
	@WrapOperation(method = "getVelocityMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
	private Block superbsteeds$skeletonHorseSoulSandSpeed(BlockState instance, Operation<Block> original) {
		Block block = original.call(instance);
		if (block == Blocks.SOUL_SAND && ((Entity) (Object) this instanceof SkeletonHorseEntity)) {
			return Blocks.SOUL_SOIL;
		}
		return block;
	}
}
