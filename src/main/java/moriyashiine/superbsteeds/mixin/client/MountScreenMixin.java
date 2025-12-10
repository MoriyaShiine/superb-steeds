/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.superbsteeds.mixin.client;

import moriyashiine.superbsteeds.common.SuperbSteeds;
import moriyashiine.superbsteeds.common.component.entity.HorseAttributesComponent;
import moriyashiine.superbsteeds.common.component.entity.LlamaTrainingComponent;
import moriyashiine.superbsteeds.common.init.ModEntityComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.MountScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.MountScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MountScreen.class)
public abstract class MountScreenMixin<T extends MountScreenHandler> extends HandledScreen<T> {
	@Unique
	private static final Identifier ATTRIBUTES_TEXTURE = SuperbSteeds.id("textures/gui/container/horse_attributes.png");

	@Unique
	private static final Identifier SPEED_ICON = SuperbSteeds.id("textures/gui/speed_icon.png");
	@Unique
	private static final Identifier JUMP_ICON = SuperbSteeds.id("textures/gui/jump_icon.png");

	@Shadow
	protected LivingEntity mount;

	public MountScreenMixin(T handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Inject(method = "drawBackground", at = @At("HEAD"))
	private void superbsteeds$showAttributes(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
		HorseAttributesComponent horseAttributesComponent = ModEntityComponents.HORSE_ATTRIBUTES.getNullable(mount);
		if (horseAttributesComponent != null) {
			context.drawTexture(RenderPipelines.GUI_TEXTURED, ATTRIBUTES_TEXTURE, (width - backgroundWidth) / 2 - 28, (height - backgroundHeight) / 2, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
			drawHorseAttributes(context, horseAttributesComponent);
		} else {
			LlamaTrainingComponent llamaTrainingComponent = ModEntityComponents.LLAMA_TRAINING.getNullable(mount);
			if (llamaTrainingComponent != null) {
				context.drawTexture(RenderPipelines.GUI_TEXTURED, ATTRIBUTES_TEXTURE, (width - backgroundWidth) / 2 - 16, (height - backgroundHeight) / 2, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
				drawLlamaAttributes(context, llamaTrainingComponent);
			}
		}
	}

	@Unique
	private void drawHorseAttributes(DrawContext context, HorseAttributesComponent horseAttributesComponent) {
		context.drawTexture(RenderPipelines.GUI_TEXTURED, SPEED_ICON, context.getScaledWindowWidth() / 2 - 111, context.getScaledWindowHeight() / 2 - 21, 0, 0, 9, 9, 9, 9);
		for (int i = 0; i < 5; i++) {
			context.drawText(MinecraftClient.getInstance().textRenderer, i < horseAttributesComponent.getSpeed() ? "★" : "☆", context.getScaledWindowWidth() / 2 - 110, context.getScaledWindowHeight() / 2 - 33 - (i * 9), 0xFF404040, false);
		}
		context.drawTexture(RenderPipelines.GUI_TEXTURED, JUMP_ICON, context.getScaledWindowWidth() / 2 - 99, context.getScaledWindowHeight() / 2 - 21, 0, 0, 9, 9, 9, 9);
		for (int i = 0; i < 5; i++) {
			context.drawText(MinecraftClient.getInstance().textRenderer, i < horseAttributesComponent.getJump() ? "★" : "☆", context.getScaledWindowWidth() / 2 - 98, context.getScaledWindowHeight() / 2 - 33 - (i * 9), 0xFF404040, false);
		}
	}

	@Unique
	private void drawLlamaAttributes(DrawContext context, LlamaTrainingComponent llamaTrainingComponent) {
		context.drawTexture(RenderPipelines.GUI_TEXTURED, JUMP_ICON, context.getScaledWindowWidth() / 2 - 99, context.getScaledWindowHeight() / 2 - 21, 0, 0, 9, 9, 9, 9);
		for (int i = 0; i < 5; i++) {
			context.drawText(MinecraftClient.getInstance().textRenderer, i < llamaTrainingComponent.getStrength() ? "★" : "☆", context.getScaledWindowWidth() / 2 - 98, context.getScaledWindowHeight() / 2 - 33 - (i * 9), 0xFF404040, false);
		}
	}
}
