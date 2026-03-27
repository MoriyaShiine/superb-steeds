/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.common.component.entity;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.superbsteeds.common.SuperbSteeds;
import moriyashiine.superbsteeds.common.init.ModEntityComponents;
import moriyashiine.superbsteeds.common.init.ModTriggers;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class HorseAttributesComponent implements AutoSyncedComponent, ServerTickingComponent {
	public static final float BASE_HEALTH = 30;
	public static final double BASE_HORSE_SPEED = 0.16875, BASE_HORSE_JUMP = 0.5;

	private static final Identifier MOVEMENT_SPEED_ID = SuperbSteeds.id("movement_speed");
	private static final Identifier JUMP_STRENGTH_ID = SuperbSteeds.id("jump_strength");

	private static final int MAX_EXPERIENCE = 360;

	private final AbstractHorse obj;
	private boolean setAttributes = false;
	private int speed = 1, jump = 1;
	private int experience = 0;

	public HorseAttributesComponent(AbstractHorse obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		setAttributes = input.getBooleanOr("SetAttributes", false);
		speed = input.getIntOr("Speed", 1);
		jump = input.getIntOr("Jump", 1);
		experience = input.getIntOr("Experience", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putBoolean("SetAttributes", setAttributes);
		output.putInt("Speed", speed);
		output.putInt("Jump", jump);
		output.putInt("Experience", experience);
	}

	@Override
	public void serverTick() {
		if (!setAttributes) {
			setAttributes = true;
			obj.randomizeAttributes(obj.getRandom());
			for (int i = 0; i < obj.getRandom().nextInt(4); i++) {
				if (obj.getRandom().nextBoolean()) {
					incrementSpeed();
				} else {
					incrementJump();
				}
			}
			sync();
		}
		if (obj.level().getGameTime() % 20 == 0) {
			if (speed < 5 || jump < 5) {
				if (obj.isSaddled() && obj.isVehicle() && obj.getKnownMovement().length() >= obj.getGravity()) {
					experience++;
					if (experience >= MAX_EXPERIENCE) {
						experience = 0;
						if (obj.getRandom().nextBoolean()) {
							if (speed < 5) {
								incrementSpeed();
							} else {
								incrementJump();
							}
						} else {
							if (jump < 5) {
								incrementJump();

							} else {
								incrementSpeed();
							}
						}
						SLibUtils.playSound(obj, SoundEvents.EXPERIENCE_ORB_PICKUP);
						obj.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100));
						if (speed == 5 && jump == 5) {
							for (Entity entity : obj.getPassengers()) {
								if (entity instanceof ServerPlayer player) {
									ModTriggers.FULLY_TRAIN_HORSE.trigger(player);
								}
							}
						}
						sync();
					}
				}
			}
		}
	}

	public void sync() {
		ModEntityComponents.HORSE_ATTRIBUTES.sync(obj);
	}

	public int getSpeed() {
		return speed;
	}

	public void incrementSpeed() {
		double value = obj instanceof Camel ? 0.0133 : 27 / 640D;
		value *= speed;
		AttributeInstance attribute = obj.getAttribute(Attributes.MOVEMENT_SPEED);
		attribute.removeModifier(MOVEMENT_SPEED_ID);
		attribute.addPermanentModifier(new AttributeModifier(MOVEMENT_SPEED_ID, value, AttributeModifier.Operation.ADD_VALUE));
		speed++;
	}

	public int getJump() {
		return jump;
	}

	public void incrementJump() {
		double value = obj instanceof Camel ? 0.0267 : 1 / 8D;
		value *= jump;
		AttributeInstance attribute = obj.getAttribute(Attributes.JUMP_STRENGTH);
		attribute.removeModifier(JUMP_STRENGTH_ID);
		attribute.addPermanentModifier(new AttributeModifier(JUMP_STRENGTH_ID, value, AttributeModifier.Operation.ADD_VALUE));
		jump++;
	}
}
