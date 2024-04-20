/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.mixin;

import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractHorseEntity.class)
public interface AbstractHorseEntityAccessor {
	@Invoker("onChestedStatusChanged")
	void superbsteeds$onChestedStatusChanged();
}
