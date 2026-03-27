/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.data.provider;

import moriyashiine.superbsteeds.common.SuperbSteeds;
import moriyashiine.superbsteeds.common.init.ModTriggers;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
	public ModAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer) {
		Advancement.Builder.advancement()
				.parent(Identifier.tryParse("husbandry/tame_an_animal"))
				.display(Items.HAY_BLOCK,
						Component.translatable("advancements.superbsteeds.husbandry.fully_train_horse.title"),
						Component.translatable("advancements.superbsteeds.husbandry.fully_train_horse.description"),
						null,
						AdvancementType.CHALLENGE,
						true,
						true,
						false)
				.rewards(AdvancementRewards.Builder.experience(75))
				.addCriterion("fully_train_horse", ModTriggers.FULLY_TRAIN_HORSE.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
				.save(consumer, SuperbSteeds.id("husbandry/fully_train_horse").toString());
	}
}
