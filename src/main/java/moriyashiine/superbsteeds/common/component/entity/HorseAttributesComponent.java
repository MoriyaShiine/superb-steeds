/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.common.component.entity;

import moriyashiine.superbsteeds.common.init.ModEntityComponents;
import moriyashiine.superbsteeds.mixin.AbstractHorseEntityAccessor;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvents;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class HorseAttributesComponent implements AutoSyncedComponent, ServerTickingComponent {
	public static final float BASE_HEALTH = 30;
	public static final double BASE_HORSE_SPEED = 0.16875, BASE_HORSE_JUMP = 0.5;

	private static final int MAX_EXPERIENCE = 360;

	private final AbstractHorseEntity obj;
	private boolean setAttributes = false;
	private int speed = 1, jump = 1;
	private int experience = 0;

	public HorseAttributesComponent(AbstractHorseEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		setAttributes = tag.getBoolean("SetAttributes");
		speed = tag.getInt("Speed");
		jump = tag.getInt("Jump");
		experience = tag.getInt("Experience");
	}

	@Override
	public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		tag.putBoolean("SetAttributes", setAttributes);
		tag.putInt("Speed", speed);
		tag.putInt("Jump", jump);
		tag.putInt("Experience", experience);
	}

	@Override
	public void serverTick() {
		if (!setAttributes) {
			setAttributes = true;
			((AbstractHorseEntityAccessor) obj).superbsteeds$initAttributes(obj.getRandom());
			for (int i = 0; i < obj.getRandom().nextInt(4); i++) {
				if (obj.getRandom().nextBoolean()) {
					incrementSpeed();
				} else {
					incrementJump();
				}
			}
			sync();
		}
		if (obj.getWorld().getTime() % 20 == 0) {
			if (speed < 5 || jump < 5) {
				if (obj.isSaddled() && obj.getFirstPassenger() instanceof PlayerEntity player && player.getVelocity().length() >= 0.08) {
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
						obj.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						obj.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100));
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
		obj.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addPersistentModifier(new EntityAttributeModifier("Training modifier", obj instanceof CamelEntity ? 0.0133 : 27 / 640D, EntityAttributeModifier.Operation.ADD_VALUE));
		speed++;
	}

	public int getJump() {
		return jump;
	}

	public void incrementJump() {
		obj.getAttributeInstance(EntityAttributes.GENERIC_JUMP_STRENGTH).addPersistentModifier(new EntityAttributeModifier("Training modifier", obj instanceof CamelEntity ? 0.0267 : 1 / 8D, EntityAttributeModifier.Operation.ADD_VALUE));
		jump++;
	}
}
