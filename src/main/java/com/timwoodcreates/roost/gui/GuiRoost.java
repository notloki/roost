package com.timwoodcreates.roost.gui;

import java.text.DecimalFormat;
import java.util.List;

import com.google.common.collect.Lists;
import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.inventory.ContainerRoost;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiRoost extends GuiContainer {

	private static final ResourceLocation ROOST_GUI_TEXTURES = new ResourceLocation(Roost.MODID,
			"textures/gui/roost.png");

	private static final DecimalFormat FORMATTER = new DecimalFormat("0.0%");

	private final IInventory roostInventory;

	public GuiRoost(InventoryPlayer playerInventory, IInventory roostInventory) {
		super(new ContainerRoost(playerInventory, roostInventory));
		this.roostInventory = roostInventory;
		this.xSize = 176;
		this.ySize = 133;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(roostInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
		int x = guiLeft;
		int y = (height - ySize) / 2;

		if (mouseX > x + 48 && mouseX < x + 74 && mouseY > y + 20 && mouseY < y + 36) {
			List<String> list = Lists.<String>newArrayList();
			list.add(FORMATTER.format(getProgress()));
			drawHoveringText(list, mouseX - x, mouseY - y);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(ROOST_GUI_TEXTURES);
		int x = guiLeft;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		drawTexturedModalRect(x + 48, y + 20, 176, 0, getProgressWidth(), 16);
	}

	private int getProgressWidth() {
		double progress = getProgress();
		return progress == 0.0D ? 0 : 1 + (int) (progress * 25);
	}

	private double getProgress() {
		double full = roostInventory.getField(0);
		return full == 0 ? 0 : roostInventory.getField(1) / full;
	}

}