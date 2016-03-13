/**
  * The MIT License (MIT)
  *
  * Copyright (c) 2016 FishDog5000
  *
  * Permission is hereby granted, free of charge, to any person obtaining a copy
  * of this software and associated documentation files (the "Software"), to deal
  * in the Software without restriction, including without limitation the rights
  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  * copies of the Software, and to permit persons to whom the Software is
  * furnished to do so, subject to the following conditions:
  *
  * The above copyright notice and this permission notice shall be included in all
  * copies or substantial portions of the Software.
  *
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  * SOFTWARE.
  */
package net.fishdog5000.core.handler

import net.fishdog5000.core.FishdogsCore
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.RenderTNTPrimed
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.registry.EntityRegistry

trait CommonProxy {
    def preInit() = {}

    def registerItemRenderer(item: Item, metadata: Int, name: String, modid: String) = {}

    def registerBlockRenderer(item: Block, metadata: Int, name: String, modid: String) = {}

    def isClient: Boolean

    def registerRenderers() = {}

    def init() = {
        //EntityRegistry.registerModEntity(classOf[EntityFallingTileEntity], "FallingTileEntity", 1, FishdogsCore.MODID, 0, 5, true)
        //EntityRegistry.registerModEntity(classOf[EntityFlexibleFallingBlock], "EntityFallingBlockFlexible", 2, FishdogsCore.MODID, 0, 5, true)
        EntityRegistry.registerModEntity(classOf[Nothing], "FlexiblePrimedTNT", 0, FishdogsCore.MODID, 0, 5, true)
        registerRenderInformation()
    }

    def registerRenderInformation() {}

    def postInit() = {}
}

class ClientProxy extends CommonProxy {

    def isClient = true

    override def preInit() =
        super.preInit()

    override def init() =
        super.init()

    override def registerRenderers() = {
        val rm = Minecraft.getMinecraft.getRenderManager
        //RenderingRegistry.registerEntityRenderingHandler(classOf[EntityFallingTileEntity], new RenderFallingBlock(rm))
        //RenderingRegistry.registerEntityRenderingHandler(classOf[EntityFlexibleFallingBlock], new RenderFallingBlock(rm))
        RenderingRegistry.registerEntityRenderingHandler(classOf[Nothing], new RenderTNTPrimed(rm))
    }

    override def registerBlockRenderer(block: Block, meta: Int, name: String, modid: String) =
        Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(modid + ":" + name, "inventory"))

    override def registerItemRenderer(item: Item, meta: Int, name: String, modid: String) =
        Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(item, meta, new ModelResourceLocation(modid + ":" + name, "inventory"))

    override def postInit() =
        super.postInit()
}

class ServerProxy extends CommonProxy {
    def isClient = false
}
