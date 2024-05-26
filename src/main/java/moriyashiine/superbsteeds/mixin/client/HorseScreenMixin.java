/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.mixin.client;

import moriyashiine.superbsteeds.common.SuperbSteeds;
import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import moriyashiine.superbsteeds.common.component.entity.LLamaTrainingComponent;
import moriyashiine.superbsteeds.common.init.ModEntityComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HorseScreen;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseScreen.class)
public abstract class HorseScreenMixin extends HandledScreen<HorseScreenHandler> {
	@Unique
	private static final Identifier ATTRIBUTES_TEXTURE = SuperbSteeds.id("textures/gui/container/horse_attributes.png");

	@Unique
	private static final Identifier SPEED_ICON = SuperbSteeds.id("textures/gui/speed_icon.png");
	@Unique
	private static final Identifier JUMP_ICON = SuperbSteeds.id("textures/gui/jump_icon.png");

	@Shadow
	@Final
	private AbstractHorseEntity entity;

	public HorseScreenMixin(HorseScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Inject(method = "drawBackground", at = @At("HEAD"))
	private void superbsteeds$showAttributes(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
		HorseAttributesComponent horseAttributesComponent = ModEntityComponents.HORSE_ATTRIBUTES.getNullable(entity);
		if (horseAttributesComponent != null) {
			context.drawTexture(ATTRIBUTES_TEXTURE, (width - backgroundWidth) / 2 - 28, (height - backgroundHeight) / 2, 0, 0, backgroundWidth, backgroundHeight);
			drawHorseAttributes(context, horseAttributesComponent);
		} else {
			LLamaTrainingComponent llamaTrainingComponent = ModEntityComponents.LLAMA_TRAINING.getNullable(entity);
			if (llamaTrainingComponent != null) {
				context.drawTexture(ATTRIBUTES_TEXTURE, (width - backgroundWidth) / 2 - 16, (height - backgroundHeight) / 2, 0, 0, backgroundWidth, backgroundHeight);
				drawLlamaAttributes(context, llamaTrainingComponent);
			}
		}
	}

	@Unique
	private void drawHorseAttributes(DrawContext context, HorseAttributesComponent horseAttributesComponent) {
		context.drawTexture(SPEED_ICON, context.getScaledWindowWidth() / 2 - 111, context.getScaledWindowHeight() / 2 - 21, 0, 0, 9, 9, 9, 9);
		for (int i = 0; i < 5; i++) {
			context.drawText(MinecraftClient.getInstance().textRenderer, i < horseAttributesComponent.getSpeed() ? "★" : "☆", context.getScaledWindowWidth() / 2 - 110, context.getScaledWindowHeight() / 2 - 33 - (i * 9), 0x404040, false);
		}
		context.drawTexture(JUMP_ICON, context.getScaledWindowWidth() / 2 - 99, context.getScaledWindowHeight() / 2 - 21, 0, 0, 9, 9, 9, 9);
		for (int i = 0; i < 5; i++) {
			context.drawText(MinecraftClient.getInstance().textRenderer, i < horseAttributesComponent.getJump() ? "★" : "☆", context.getScaledWindowWidth() / 2 - 98, context.getScaledWindowHeight() / 2 - 33 - (i * 9), 0x404040, false);
		}
	}

	@Unique
	private void drawLlamaAttributes(DrawContext context, LLamaTrainingComponent llamaTrainingComponent) {
		context.drawTexture(JUMP_ICON, context.getScaledWindowWidth() / 2 - 99, context.getScaledWindowHeight() / 2 - 21, 0, 0, 9, 9, 9, 9);
		for (int i = 0; i < 5; i++) {
			context.drawText(MinecraftClient.getInstance().textRenderer, i < llamaTrainingComponent.getStrength() ? "★" : "☆", context.getScaledWindowWidth() / 2 - 98, context.getScaledWindowHeight() / 2 - 33 - (i * 9), 0x404040, false);
		}
	}
}
