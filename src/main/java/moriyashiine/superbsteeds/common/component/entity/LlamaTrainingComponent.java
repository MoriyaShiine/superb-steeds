/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.common.component.entity;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class LlamaTrainingComponent implements ServerTickingComponent {
	private static final int MAX_EXPERIENCE = 180;

	private final LlamaEntity obj;
	private int experience = 0;

	public LlamaTrainingComponent(LlamaEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		experience = readView.getInt("Experience", 0);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.putInt("Experience", experience);
	}

	@Override
	public void serverTick() {
		if (obj.getWorld().getTime() % 20 == 0 && obj.isTame()) {
			int strength = getStrength();
			if (strength < 5) {
				if (obj.isLeashed() && obj.getVelocity().length() >= obj.getFinalGravity()) {
					experience++;
					if (experience >= MAX_EXPERIENCE) {
						experience = 0;
						obj.setStrength(strength + 1);
						obj.onChestedStatusChanged();
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
