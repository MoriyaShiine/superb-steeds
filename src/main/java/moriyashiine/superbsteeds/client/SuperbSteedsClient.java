/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.superbsteeds.client;

import moriyashiine.superbsteeds.client.event.HorseArmorTooltipEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class SuperbSteedsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ItemTooltipCallback.EVENT.register(new HorseArmorTooltipEvent());
	}
}
