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
import moriyashiine.superbsteeds.common.component.entity.LLamaTrainingComponent;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.LlamaEntity;

public class ModEntityComponents implements EntityComponentInitializer {
	public static final ComponentKey<HorseAttributesComponent> HORSE_ATTRIBUTES = ComponentRegistry.getOrCreate(SuperbSteeds.id("horse_attributes"), HorseAttributesComponent.class);
	public static final ComponentKey<LLamaTrainingComponent> LLAMA_TRAINING = ComponentRegistry.getOrCreate(SuperbSteeds.id("llama_training"), LLamaTrainingComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(AbstractHorseEntity.class, HORSE_ATTRIBUTES).filter(clazz -> !clazz.isAssignableFrom(LlamaEntity.class)).end(HorseAttributesComponent::new);
		registry.registerFor(LlamaEntity.class, LLAMA_TRAINING, LLamaTrainingComponent::new);
	}
}
