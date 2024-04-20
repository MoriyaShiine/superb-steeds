/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import moriyashiine.superbsteeds.mixin.AbstractHorseEntityAccessor;
import moriyashiine.superbsteeds.mixin.LlamaEntityAccessor;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;

public class LLamaTrainingComponent implements ServerTickingComponent {
	private static final int MAX_EXPERIENCE = 180;

	private final LlamaEntity obj;
	private int experience = 0;

	public LLamaTrainingComponent(LlamaEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		experience = tag.getInt("Experience");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("Experience", experience);
	}

	@Override
	public void serverTick() {
		if (obj.getWorld().getTime() % 20 == 0 && obj.isTame()) {
			int strength = getStrength();
			if (strength < 5) {
				if (obj.getVelocity().length() >= 0.08 && obj.getHoldingEntity() != null) {
					experience++;
					if (experience >= MAX_EXPERIENCE) {
						experience = 0;
						((LlamaEntityAccessor) obj).superbsteeds$setStrength(strength + 1);
						((AbstractHorseEntityAccessor) obj).superbsteeds$onChestedStatusChanged();
						obj.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						obj.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100));
					}
				}
			}
		}
	}

	public int getStrength() {
		return obj.getStrength();
	}
}
