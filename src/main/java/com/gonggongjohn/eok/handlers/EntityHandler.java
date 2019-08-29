package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.entity.EntityBullet;
import com.gonggongjohn.eok.entity.EntityChenweilin;
import com.gonggongjohn.eok.entity.EntitySmalldew;
import com.gonggongjohn.eok.entity.render.RenderChenweilin;
import com.gonggongjohn.eok.entity.render.RenderSmalldew;
import com.gonggongjohn.eok.utils.EntityRenderFactory;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHandler {
	public static void register() {
		EntityRegistry.registerModEntity(new ResourceLocation(EOK.MODID, "entity.bullet"), EntityBullet.class,
				"entity_bullet", 0, EOK.instance, 128, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(EOK.MODID, "entity.smalldew"), EntitySmalldew.class,
				"entity_smalldew", 1, EOK.instance, 64, 1, true, 0x0066CC, 0xCCE5FF);
		EntityRegistry.registerModEntity(new ResourceLocation(EOK.MODID, "entity.chenweilin"), EntityChenweilin.class,
				"entity_chenwe_i_lin", 2, EOK.instance, 64, 1, true, 0x916B94, 0x99CCFF);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		registerEntityRender(EntitySmalldew.class, RenderSmalldew.class);
		registerEntityRender(EntityChenweilin.class, RenderChenweilin.class);
	}

	@SideOnly(Side.CLIENT)
	private static <T extends Entity> void registerEntityRender(Class<T> entityClass, Class render) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityRenderFactory(render));
	}
}