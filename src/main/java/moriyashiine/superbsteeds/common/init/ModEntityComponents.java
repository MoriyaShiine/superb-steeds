/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.common.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import moriyashiine.superbsteeds.common.SuperbSteeds;
import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import net.minecraft.entity.passive.AbstractHorseEntity;

public class ModEntityComponents implements EntityComponentInitializer {
	public static final ComponentKey<HorseAttributesComponent> HORSE_ATTRIBUTES = ComponentRegistry.getOrCreate(SuperbSteeds.id("horse_attributes"), HorseAttributesComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(AbstractHorseEntity.class, HORSE_ATTRIBUTES, HorseAttributesComponent::new);
	}
}
