/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.common.component.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.equine.Llama;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class LlamaTrainingComponent implements ServerTickingComponent {
	private static final int MAX_EXPERIENCE = 180;

	private final Llama obj;
	private int experience = 0;

	public LlamaTrainingComponent(Llama obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		experience = input.getIntOr("Experience", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putInt("Experience", experience);
	}

	@Override
	public void serverTick() {
		if (obj.level().getGameTime() % 20 == 0 && obj.isTamed()) {
			int strength = getStrength();
			if (strength < 5) {
				if (obj.isLeashed() && obj.getDeltaMovement().length() >= obj.getGravity()) {
					experience++;
					if (experience >= MAX_EXPERIENCE) {
						experience = 0;
						obj.setStrength(strength + 1);
						obj.createInventory();
						obj.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 1);
						obj.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100));
					}
				}
			}
		}
	}

	public int getStrength() {
		return obj.getStrength();
	}
}
