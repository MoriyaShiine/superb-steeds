/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.common.event;

import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModifyAttributesEvent implements FabricDefaultAttributeRegistry.ModifyDefaultAttribute {
	public static void init() {
		FabricDefaultAttributeRegistry.MODIFY.register(new ModifyAttributesEvent());
	}

	private static final double BASE_CAMEL_SPEED = 0.0766, BASE_CAMEL_JUMP = 0.3933;

	@Override
	public void modify(FabricDefaultAttributeRegistry.ModifyContext context) {
		context.modify(EntityTypes.CAMEL, (_, builder) -> {
			builder.add(Attributes.MOVEMENT_SPEED, BASE_CAMEL_SPEED);
			builder.add(Attributes.JUMP_STRENGTH, BASE_CAMEL_JUMP);
		});
		context.modify(EntityTypes.SKELETON_HORSE, (_, builder) -> {
			builder.add(Attributes.MAX_HEALTH, HorseAttributesComponent.BASE_HEALTH);
			builder.add(Attributes.MOVEMENT_SPEED, HorseAttributesComponent.BASE_HORSE_SPEED);
		});
		context.modify(EntityTypes.ZOMBIE_HORSE, (_, builder) ->
				builder.add(Attributes.MAX_HEALTH, HorseAttributesComponent.BASE_HEALTH));
	}
}
