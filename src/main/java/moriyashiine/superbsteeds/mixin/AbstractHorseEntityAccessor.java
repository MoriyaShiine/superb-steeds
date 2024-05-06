/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.mixin;

import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractHorseEntity.class)
public interface AbstractHorseEntityAccessor {
	@Invoker("initAttributes")
	void superbsteeds$initAttributes(Random random);

	@Invoker("onChestedStatusChanged")
	void superbsteeds$onChestedStatusChanged();
}
