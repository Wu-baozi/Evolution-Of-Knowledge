package com.gonggongjohn.eok.api.gui.meta;

import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.gui.Colors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiControl {

	protected final EnumControlType type;
	protected final MetaGuiScreen gui;
	public final int id;
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	private GuiControl(int id, MetaGuiScreen gui, EnumControlType type) {
		this.id = id;
		this.gui = gui;
		this.type = type;
	}
	
	public EnumControlType getType() {
		return this.type;
	}
	
	public void draw(int mouseX, int mouseY, float partialTicks, MetaGuiScreen gui) {
		
	}
	
	public void init() {
		
	}
	
	public void remove() {
		
	}
	
	public static class ControlBuilder {
		private final MetaGuiScreen gui;
		private int nextId = 0;
		
		public ControlBuilder(MetaGuiScreen gui) {
			this.gui = gui;
		}
		
		public GuiButton createButton(int x, int y, int width, int height, int u, int v, int u2, int v2, Consumer<MetaGuiScreen> onClick) {
			return new GuiButton(nextId++, x, y, width, height, u, v, u2, v2, onClick, this.gui);
		}
		
		// TODO more controls
	}

	public static class GuiButton extends GuiControl {
		
		protected int u;
		protected int v;
		protected int u2;
		protected int v2;
		protected Consumer<MetaGuiScreen> func;
		protected String text = "";
		protected int color = 0;
		
		protected GuiButton(int id, int x, int y, int width, int height, int u, int v, int u2, int v2, Consumer<MetaGuiScreen> onClick, MetaGuiScreen gui) {
			super(id, gui, EnumControlType.BUTTON);
			this.x = x + gui.getOffsetX();
			this.y = y + gui.getOffsetY();
			this.width = width;
			this.height = height;
			this.u = u;
			this.v = v;
			this.u2 = u2;
			this.v2 = v2;
			this.func = onClick;
		}

		@Override
		public void init() {

			net.minecraft.client.gui.GuiButton button = new net.minecraft.client.gui.GuiButton(id, x, y, width, height, text) {

				@Override
				public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
					if (this.visible) {

						GL11.glPushMatrix();
						GL11.glEnable(GL11.GL_BLEND);
						GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.getTextureManager().bindTexture(gui.getTexture());

						if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height) {
							Gui.drawModalRectWithCustomSizedTexture(x, y, u2, v2, width, height, gui.getTextureWidth(), gui.getTextureHeight());
						} else {
							Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, gui.getTextureWidth(), gui.getTextureHeight());
						}

						if (!text.equals("")) {

								if (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
										&& mouseY < this.y + this.height) {
									this.drawCenteredString(mc.fontRenderer, text, this.x + this.width / 2,
											this.y + (this.height - 8) / 2, Colors.MOUSEON_TEXT);
								} else {
									this.drawCenteredString(mc.fontRenderer, text, this.x + this.width / 2,
											this.y + (this.height - 8) / 2, color);
								}
						}

						GL11.glDisable(GL11.GL_BLEND);
						GL11.glPopMatrix();

					}
				}

			};
			
			gui.getButtonList().add(button);
		}
		
		@Override
		public void remove() {
			gui.getButtonList().remove(this.getId());
		}
		
		public void setText(String text, int color) {
			this.text = text;
			this.color = color;
		}
		
	}

	// TODO GuiProgressBar
	public static class GuiProgressBar extends GuiControl {
		protected GuiProgressBar(int id, MetaGuiScreen gui) {
			super(id, gui, EnumControlType.PROGRESSBAR);
		}
	}

	// TODO  GuiScrollBar
	public static class GuiScrollBar extends GuiControl {

		public final int direction;
		
		/**
		 * @param type the orientation of the scrollbar, 0 is horizontal, 1 is vertical
		 */
		protected GuiScrollBar(int id, int type, MetaGuiScreen gui) {
			super(id, gui, EnumControlType.SCROLLBAR);
			if (type == 0 || type == 1) {
				this.direction = type;
			} else {
				throwArgumentException("scroll bar type can only be 0 or 1");
				direction = 0;	// 永远不会执行
			}
		}
		
	}

	public static enum EnumControlType {
		BUTTON("button", 0),
		PROGRESSBAR("progressbar", 1),
		SCROLLBAR("scrollbar", 2),
		INPUTBOX("inputbox", 3),
		CHECKBOX("checkbox", 4);

		String name;
		int index;

		private EnumControlType(String name, int index) {
			this.name = name;
			this.index = index;
		}
		
		public int getIndex() {
			return this.index;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	private static void throwArgumentException(String s) {
		EOK.getLogger().error("An error occurred when rendering Gui, " + s + ".");
		throw new IllegalArgumentException(s);
	}
	
	protected int getId() {
		return id;
	}
}