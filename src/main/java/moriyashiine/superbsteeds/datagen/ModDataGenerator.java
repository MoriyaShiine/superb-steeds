/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.datagen;

import moriyashiine.superbsteeds.datagen.provider.ModAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModAdvancementProvider::new);
	}
}
