/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.superbsteeds.mixin.client;

import moriyashiine.superbsteeds.common.SuperbSteeds;
import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import moriyashiine.superbsteeds.common.component.entity.LlamaTrainingComponent;
import moriyashiine.superbsteeds.common.init.ModEntityComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractMountInventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractMountInventoryMenu;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMountInventoryScreen.class)
public abstract class AbstractMountInventoryScreenMixin<T extends AbstractMountInventoryMenu> extends AbstractContainerScreen<T> {
	@Unique
	private static final Identifier ATTRIBUTES_TEXTURE = SuperbSteeds.id("textures/gui/container/horse_attributes.png");

	@Unique
	private static final Identifier SPEED_ICON = SuperbSteeds.id("textures/gui/speed_icon.png");
	@Unique
	private static final Identifier JUMP_ICON = SuperbSteeds.id("textures/gui/jump_icon.png");

	@Shadow
	@Final
	protected LivingEntity mount;

	public AbstractMountInventoryScreenMixin(T menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@Inject(method = "extractBackground", at = @At("TAIL"))
	private void superbsteeds$showAttributes(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
		@Nullable HorseAttributesComponent horseAttributesComponent = ModEntityComponents.HORSE_ATTRIBUTES.getNullable(mount);
		if (horseAttributesComponent != null) {
			graphics.blit(RenderPipelines.GUI_TEXTURED, ATTRIBUTES_TEXTURE, (width - imageWidth) / 2 - 28, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight, 256, 256);
			drawHorseAttributes(graphics, horseAttributesComponent);
		} else {
			@Nullable LlamaTrainingComponent llamaTrainingComponent = ModEntityComponents.LLAMA_TRAINING.getNullable(mount);
			if (llamaTrainingComponent != null) {
				graphics.blit(RenderPipelines.GUI_TEXTURED, ATTRIBUTES_TEXTURE, (width - imageWidth) / 2 - 16, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight, 256, 256);
				drawLlamaAttributes(graphics, llamaTrainingComponent);
			}
		}
	}

	@Unique
	private void drawHorseAttributes(GuiGraphicsExtractor graphics, HorseAttributesComponent horseAttributesComponent) {
		graphics.blit(RenderPipelines.GUI_TEXTURED, SPEED_ICON, graphics.guiWidth() / 2 - 111, graphics.guiHeight() / 2 - 21, 0, 0, 9, 9, 9, 9);
		for (int i = 0; i < 5; i++) {
			graphics.text(Minecraft.getInstance().font, i < horseAttributesComponent.getSpeed() ? "★" : "☆", graphics.guiWidth() / 2 - 110, graphics.guiHeight() / 2 - 33 - (i * 9), 0xFF404040, false);
		}
		graphics.blit(RenderPipelines.GUI_TEXTURED, JUMP_ICON, graphics.guiWidth() / 2 - 99, graphics.guiHeight() / 2 - 21, 0, 0, 9, 9, 9, 9);
		for (int i = 0; i < 5; i++) {
			graphics.text(Minecraft.getInstance().font, i < horseAttributesComponent.getJump() ? "★" : "☆", graphics.guiWidth() / 2 - 98, graphics.guiHeight() / 2 - 33 - (i * 9), 0xFF404040, false);
		}
	}

	@Unique
	private void drawLlamaAttributes(GuiGraphicsExtractor graphics, LlamaTrainingComponent llamaTrainingComponent) {
		graphics.blit(RenderPipelines.GUI_TEXTURED, JUMP_ICON, graphics.guiWidth() / 2 - 99, graphics.guiHeight() / 2 - 21, 0, 0, 9, 9, 9, 9);
		for (int i = 0; i < 5; i++) {
			graphics.text(Minecraft.getInstance().font, i < llamaTrainingComponent.getStrength() ? "★" : "☆", graphics.guiWidth() / 2 - 98, graphics.guiHeight() / 2 - 33 - (i * 9), 0xFF404040, false);
		}
	}
}
