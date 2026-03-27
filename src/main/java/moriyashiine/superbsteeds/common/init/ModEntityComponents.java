/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.common.init;

import moriyashiine.superbsteeds.common.SuperbSteeds;
import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import moriyashiine.superbsteeds.common.component.entity.LlamaTrainingComponent;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.equine.Llama;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class ModEntityComponents implements EntityComponentInitializer {
	public static final ComponentKey<HorseAttributesComponent> HORSE_ATTRIBUTES = ComponentRegistry.getOrCreate(SuperbSteeds.id("horse_attributes"), HorseAttributesComponent.class);
	public static final ComponentKey<LlamaTrainingComponent> LLAMA_TRAINING = ComponentRegistry.getOrCreate(SuperbSteeds.id("llama_training"), LlamaTrainingComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(AbstractHorse.class, HORSE_ATTRIBUTES).filter(clazz -> !Llama.class.isAssignableFrom(clazz)).end(HorseAttributesComponent::new);
		registry.registerFor(Llama.class, LLAMA_TRAINING, LlamaTrainingComponent::new);
	}
}
