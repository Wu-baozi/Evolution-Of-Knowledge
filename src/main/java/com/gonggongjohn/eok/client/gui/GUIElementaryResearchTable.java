package com.gonggongjohn.eok.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.capabilities.IResearchData;
import com.gonggongjohn.eok.handlers.CapabilityHandler;
import com.gonggongjohn.eok.inventory.ContainerElementaryResearchTable;
import com.gonggongjohn.eok.network.PacketGuiButton;
import com.gonggongjohn.eok.network.PacketInverseReseachData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class GUIElementaryResearchTable extends GuiContainer {
	private static final String TEXTURE_BACK = EOK.MODID + ":" + "textures/gui/container/elementary_research_table.png";
	private static final String TEXTURE_COMP = EOK.MODID + ":"
			+ "textures/gui/container/elementary_research_table_components.png";
	private static final String TEXTURE_PAPER = EOK.MODID + ":" + "textures/gui/container/paper_background.png";
	private static final ResourceLocation TEXTUREBACK = new ResourceLocation(TEXTURE_BACK);
	private static final ResourceLocation TEXTURECOMP = new ResourceLocation(TEXTURE_COMP);
	private static final ResourceLocation TEXTUREPAPER = new ResourceLocation(TEXTURE_PAPER);
	private List<Integer> lastFinList = new ArrayList<Integer>();
	private List<Integer> finList = new ArrayList<Integer>();
	private Slot invPaperSlot;
	private int offsetX, offsetY;
	private int btnPage = 0;

	public GUIElementaryResearchTable(ContainerElementaryResearchTable inventorySlotsIn) {
		super(inventorySlotsIn);
		this.xSize = 256;
		this.ySize = 166;
		this.invPaperSlot = inventorySlotsIn.getPaperSlot();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTUREBACK);
		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

		this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
		this.mc.getTextureManager().bindTexture(TEXTURECOMP);
		this.drawTexturedModalRect(offsetX + 159, offsetY + 145, 0, 22, 88, 8);
		if (this.invPaperSlot != null
				&& this.invPaperSlot.getStack().getItem().getUnlocalizedName().equals("item.papyrus")) {
			this.mc.getTextureManager().bindTexture(TEXTUREPAPER);
			this.drawTexturedModalRect(offsetX + 67, offsetY + 10, 0, 0, 153, 126);
		}
		if (finList != null && !finList.isEmpty() && lastFinList != null && !lastFinList.isEmpty()) {
			if (finList.size() > lastFinList.size()) {
				if(finList.size() <= btnPage * 8) {
					int indexNew = finList.size() - (btnPage - 1) * 8;
					this.buttonList.add(new ButtonElementaryResearchTable(2 + indexNew, finList.get(finList.size() - 1),
							offsetX + calcButtonLeftPos(indexNew), offsetY + calcButtonTopPos(indexNew), 18, 18, offsetY));
				}
				lastFinList = finList;
			}
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	}

	@Override
	public void initGui() {
		super.initGui();
		offsetX = (this.width - this.xSize) / 2;
		offsetY = (this.height - this.ySize) / 2;
		this.buttonList.add(new GuiButton(0, offsetX + 225, offsetY + 114, 22, 22, "") {
			@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
				if (this.visible) {
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.getTextureManager().bindTexture(TEXTURECOMP);
					int relx = mouseX - this.x, rely = mouseY - this.y;
					if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height)
						this.drawTexturedModalRect(this.x, this.y, 44, 0, this.width, this.height);
					else
						this.drawTexturedModalRect(this.x, this.y, 0, 0, this.width, this.height);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
				}
			}
		});
		this.buttonList.add(new GuiButton(1, offsetX + 12, offsetY + 114, 18, 18, ""){
			@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
				if (this.visible) {
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.getTextureManager().bindTexture(TEXTURECOMP);
					int relx = mouseX - this.x, rely = mouseY - this.y;
					if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height)
						this.drawTexturedModalRect(this.x, this.y, 36, 30, this.width, this.height);
					else
						this.drawTexturedModalRect(this.x, this.y, 0, 30, this.width, this.height);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
				}
			}
		});
		this.buttonList.add(new GuiButton(2, offsetX + 34, offsetY + 114, 18, 18, ""){
			@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
				if (this.visible) {
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.getTextureManager().bindTexture(TEXTURECOMP);
					int relx = mouseX - this.x, rely = mouseY - this.y;
					if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height)
						this.drawTexturedModalRect(this.x, this.y, 36, 48, this.width, this.height);
					else
						this.drawTexturedModalRect(this.x, this.y, 0, 48, this.width, this.height);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
				}
			}
		});

		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player.hasCapability(CapabilityHandler.capResearchData, null)) {
			IResearchData researchData = player.getCapability(CapabilityHandler.capResearchData, null);
			finList = researchData.getFinishedResearch();
			if (finList.size() != 0) {
				for (int i = 1; i <= Math.min(finList.size(), 8); i++) {
					this.buttonList.add(new ButtonElementaryResearchTable(2 + i, finList.get(i - 1),
							offsetX + calcButtonLeftPos(i), offsetY + calcButtonTopPos(i), 18, 18, offsetY));
					btnPage = 1;
				}
			}
			lastFinList = finList;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if(button.id == 1){
			if(btnPage <= 1) return;
			buttonList.removeIf(index -> index.id >= 3 && index.id <= 10);
			for (int i = 1; i <= 8; i++) {
				this.buttonList.add(new ButtonElementaryResearchTable(2 + i, finList.get((btnPage - 2) * 8 + i - 1),
						offsetX + calcButtonLeftPos(i), offsetY + calcButtonTopPos(i), 18, 18, offsetY));
			}
			btnPage -= 1;
		}
		if(button.id == 2){
			if(btnPage * 8 >= finList.size()) return;
			buttonList.removeIf(index -> index.id >= 3 && index.id <= 10);
			for (int i = 1; i <= Math.min(finList.size() - btnPage * 8, 8); i++) {
				this.buttonList.add(new ButtonElementaryResearchTable(2 + i, finList.get(btnPage * 8 + i - 1),
						offsetX + calcButtonLeftPos(i), offsetY + calcButtonTopPos(i), 18, 18, offsetY));
			}
			btnPage += 1;
		}
		if (button.id >= 3 && button.id <= Math.min(2 + finList.size(), 10)) {
			int activeResearchID = ((ButtonElementaryResearchTable) button).getResearchId();
			EOK.getNetwork().sendToServer(new PacketGuiButton(activeResearchID));
		}
		if (button.id == 0) {
			ItemStack stack = this.invPaperSlot.getStack();
			int[] temp;
			if (stack == null || stack.isEmpty())
				return;
			NBTTagCompound compound = stack.getTagCompound();
			if (compound == null)
				return;
			else {
				temp = compound.getIntArray("data.research");
				HashSet<Integer> relation = new HashSet<Integer>();
				int result;
				for (int i = 0; i < temp.length; i++) {
					relation.add(temp[i]);
				}
				if (EOK.researchDict.researchRelationDict.containsKey(relation)) {
					result = EOK.researchDict.researchRelationDict.get(relation);
					if (!finList.contains(result))
						finList.add(result);
					// Add result to player's capability
					EntityPlayer player = Minecraft.getMinecraft().player;
					if (player.hasCapability(CapabilityHandler.capResearchData, null)) {
						IResearchData researchData = player.getCapability(CapabilityHandler.capResearchData, null);
						IStorage storage = CapabilityHandler.capResearchData.getStorage();
						List<Integer> finListT = researchData.getFinishedResearch();
						if (!finListT.contains(result)) {
							finListT.add(result);
							researchData.setFinishedResearch(finListT);
							NBTTagCompound nbt = (NBTTagCompound) storage.writeNBT(CapabilityHandler.capResearchData,
									researchData, null);
							storage.readNBT(CapabilityHandler.capResearchData,
									player.getCapability(CapabilityHandler.capResearchData, null), null, nbt);
							PacketInverseReseachData message = new PacketInverseReseachData(nbt);
							EOK.getNetwork().sendToServer(message);
						}
					}
				}
			}
		}
	}

	private int calcButtonLeftPos(int index) {
		if (index % 2 != 0)
			return 13;
		else
			return 37;
	}

	private int calcButtonTopPos(int index) {
		if (index % 2 == 0)
			return 14 + 25 * (index / 2 - 1);
		else
			return 14 + 25 * (index / 2);
	}
}
