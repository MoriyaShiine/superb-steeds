/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.data.provider;

import moriyashiine.superbsteeds.common.SuperbSteeds;
import moriyashiine.superbsteeds.common.init.ModCriterion;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
	public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
		consumer.accept(Advancement.Builder.create()
				.parent(Identifier.tryParse("husbandry/tame_an_animal"))
				.display(Items.HAY_BLOCK,
						Text.translatable("advancements.superbsteeds.husbandry.fully_train_horse.title"),
						Text.translatable("advancements.superbsteeds.husbandry.fully_train_horse.description"),
						null,
						AdvancementFrame.CHALLENGE,
						true,
						true,
						false)
				.rewards(AdvancementRewards.Builder.experience(75))
				.criterion("fully_train_horse", ModCriterion.FULLY_TRAIN_HORSE.create(new TickCriterion.Conditions(Optional.empty())))
				.build(consumer, SuperbSteeds.id("husbandry/fully_train_horse").toString()));
	}
}
