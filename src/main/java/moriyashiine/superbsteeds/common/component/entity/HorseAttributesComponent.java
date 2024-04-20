/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import moriyashiine.superbsteeds.common.init.ModEntityComponents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;

public class HorseAttributesComponent implements AutoSyncedComponent, ServerTickingComponent {
	private static final int MAX_EXPERIENCE = 600;

	private final AbstractHorseEntity obj;
	private boolean setAttributes = false;
	private int speed = 1, jump = 1;
	private int experience = 0;

	public HorseAttributesComponent(AbstractHorseEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		setAttributes = tag.getBoolean("SetAttributes");
		setSpeed(tag.getInt("Speed"));
		setJump(tag.getInt("Jump"));
		experience = tag.getInt("Experience");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putBoolean("SetAttributes", setAttributes);
		tag.putInt("Speed", speed);
		tag.putInt("Jump", jump);
		tag.putInt("Experience", experience);
	}

	@Override
	public void serverTick() {
		if (!setAttributes) {
			setAttributes = true;
			int val = obj.getRandom().nextInt(4);
			int speed = 1, jump = 1;
			while (val > 0) {
				if (obj.getRandom().nextBoolean()) {
					speed++;
				} else {
					jump++;
				}
				val--;
			}
			setSpeed(speed);
			setJump(jump);
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
								speed++;
							} else {
								jump++;
							}
						} else {
							if (jump < 5) {
								jump++;

							} else {
								speed++;
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

	public void setSpeed(int speed) {
		this.speed = speed;
		obj.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(getSpeedAdjusted());
	}

	public int getJump() {
		return jump;
	}

	public void setJump(int jump) {
		this.jump = jump;
		obj.getAttributeInstance(EntityAttributes.HORSE_JUMP_STRENGTH).setBaseValue(getJumpAdjusted());
	}

	public float getSpeedAdjusted() {
		if (obj instanceof CamelEntity) {
			return switch (speed) {
				case 5 -> 0.13F;
				case 4 -> 0.1166F;
				case 3 -> 0.1033F;
				case 2 -> 0.09F;
				default -> 0.0766F;
			};
		}
		return switch (speed) {
			case 5 -> 0.3375F;
			case 4 -> 0.2953125F;
			case 3 -> 0.253125F;
			case 2 -> 0.2109375F;
			default -> 0.16875F;
		};
	}

	public double getJumpAdjusted() {
		if (obj instanceof CamelEntity) {
			return switch (jump) {
				case 5 -> 0.5;
				case 4 -> 0.4733;
				case 3 -> 0.4466;
				case 2 -> 0.42;
				default -> 0.3933;
			};
		}
		return switch (jump) {
			case 5 -> 1;
			case 4 -> 0.875;
			case 3 -> 0.75;
			case 2 -> 0.625;
			default -> 0.5;
		};
	}
}
