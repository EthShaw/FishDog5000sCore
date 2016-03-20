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
package net.fishdog5000.core

import net.fishdog5000.core.basestuff.{BaseItem, IBaseBlock, IBaseItem}
import net.fishdog5000.core.handler._
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.{ModelBakery, ModelResourceLocation}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.crafting.{CraftingManager, IRecipe}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.{ITextComponent, TextComponentTranslation}
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent, FMLServerStartingEvent}
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.common.{Mod, SidedProxy}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import org.apache.logging.log4j.Level

//@formatter:off
@Mod(modid = FishdogsCore.MODID, name = "Fishdog5000's Core", acceptedMinecraftVersions = "", version = FishdogsCore.VERSION, modLanguage = "scala")
//@formatter:on
object FishdogsCore {
    final val VERSION = "@COREVERSION@"
    final val MODID = "fishdog5000score"
    final val MCVERSION = "@MCVERSION@"
    var powerblock: BlockPowerBlock = _
    var registeredpower = false
    //var testitem: IBaseItem = _

    @SidedProxy(clientSide = "net.fishdog5000.core.handler.ClientProxy", serverSide = "net.fishdog5000.core.handler.ServerProxy", modId = MODID)
    var proxy: CommonProxy = _

    @EventHandler
    def preInit(event: FMLPreInitializationEvent) = {
        Log.logger = event.getModLog
        Log.info("###################### FISHDOG5000'S CORE STARTING ######################")

        //Log.info("attempting to add version checker support...")
        //FMLInterModComms.sendRuntimeMessage(MODID, "VersionChecker", "addVersionCheck", CoreConstants.VERSIONS_URL)
        MinecraftForge.EVENT_BUS.register(FishdogsCoreEventHandler)

        Log.info("registering a few basic things in the ore dictionary...")
        OreDictionaryHandler.registerItemOres()

    }

    @EventHandler
    def load(event: FMLInitializationEvent) = {
        Log.info("Initializing stuff...")
        proxy.init()
        proxy.registerRenderers()

        //testitem = new BaseItem("testitem", CreativeTabs.tabMaterials, true, MODID)
        //registerItem(testitem, MODID)
        //setItemMultitexture(testitem, "item.testitem", MODID, Array("minecraft:flint", "minecraft:diamond"), "minecraft:diamond")

        if (registeredpower) {
            powerblock = new BlockPowerBlock(Material.iron)

            registerBlock(powerblock, "BlockPowerBlock", MODID)

            GameRegistry.addRecipe(new ItemStack(powerblock), "XXX", "***", "XXX",
                new Character('X'), Blocks.redstone_block, new Character('*'), Items.coal)
            GameRegistry.addRecipe(new ItemStack(powerblock), "XXX", "***", "XXX",
                new Character('X'), Blocks.redstone_block, new Character('*'), new ItemStack(Items.coal, 1, 1))
        }
    }

    def registerBlock(block: IBaseBlock, name: String, MODID: String): Unit =
        registerBlock(block, name, MODID, 0)

    def registerBlock(block: IBaseBlock, name: String, MODID: String, metadata: Int): Unit = {
        GameRegistry.registerBlock(block, name)
        proxy.registerBlockRenderer(block, metadata, name, MODID)
    }

    @EventHandler
    def postInit(event: FMLPostInitializationEvent) = {
        Log.info("###################### FISHDOG5000'S CORE READY ######################")
    }

    def flingEntity(xmotion: Double, ymotion: Double, zmotion: Double, entity: Entity, world: World): Unit =
        flingEntity(xmotion, ymotion, zmotion, entity, world, vset = true)

    def flingEntity(xmotion: Double, ymotion: Double, zmotion: Double, entity: Entity, world: World, vset: Boolean) = {
        if (world.isRemote && vset)
            entity.setVelocity(xmotion, ymotion, zmotion)

        entity.motionX = xmotion
        entity.motionY = ymotion
        entity.motionZ = zmotion
    }

    def registerPowerBlock() =
        registeredpower = true

    def registerBlock(block: IBaseBlock, MODID: String, meta: Int): Unit =
        registerBlock(block, block.getName, MODID, meta)

    def registerBlock(block: IBaseBlock, MODID: String): Unit =
        registerBlock(block, block.getName, MODID, 0)

    def removeRecipe(result: ItemStack) = {
        val recipes: java.util.List[IRecipe] = CraftingManager.getInstance.getRecipeList
        var i = 0

        while (i < recipes.size) {
            val to_remove = recipes.get(i)
            val result_recipe = to_remove.getRecipeOutput
            if (ItemStack.areItemStacksEqual(result, result_recipe)) {
                recipes.remove(i)
                i -= 0
            }
            i += 1
        }
    }

    def removeRecipe(result: Item) = {
        val recipes: java.util.List[IRecipe] = CraftingManager.getInstance.getRecipeList
        var i = 0

        while (i < recipes.size) {
            val to_remove = recipes.get(i)
            val result_recipe = to_remove.getRecipeOutput
            if (result_recipe != null && result == result_recipe.getItem) {
                recipes.remove(i)
                i -= 0
            }
            i += 1
        }
    }

    @SideOnly(Side.SERVER)
    @EventHandler
    def serverLoad(event: FMLServerStartingEvent) =
        event.registerServerCommand(OreDictionaryLister)

    def lightning(chat_msg: String, x: Double, y: Double, z: Double, world: World, times: Int) {
        for (i <- 0 until times)
            world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false))
        if (chat_msg != null)
            chat(world.getMinecraftServer, chat_msg)
    }

    def chat(server: MinecraftServer, msg: String): Unit =
        chat(server, new TextComponentTranslation(msg))

    def chat(server: MinecraftServer, msg: ITextComponent) =
        server.getPlayerList.sendChatMsg(msg)

    def registerItem(item: IBaseItem, MODID: String): Unit = registerItem(item, item.getName, MODID, 0)

    def registerItem(item: IBaseItem, name: String, MODID: String): Unit = registerItem(item, name, MODID, 0)

    def registerItem(item: IBaseItem, name: String, MODID: String, metadata: Int): Unit = {
        GameRegistry.registerItem(item, name)
        proxy.registerItemRenderer(item, metadata, name, MODID)
    }

    def registerItem(item: IBaseItem, MODID: String, metadata: Int): Unit = registerItem(item, item.getName, MODID, metadata)

    @SideOnly(Side.CLIENT)
    def setItemMultitexture(item: IBaseItem, name: String, MODID: String, textures: Array[String], defaultTexture: String): MultiTextureModel =
        setItemMultitexture(item, name, MODID, 0, textures, defaultTexture)

    @SideOnly(Side.CLIENT)
    def setItemMultitexture(item: IBaseItem, name: String, MODID: String, meta: Int, textures: Array[String], defaultTexture: String): MultiTextureModel = {
        val loc = new ModelResourceLocation(MODID + ":" + name, "inventory")
        for (model <- textures)
            ModelBakery.registerItemVariants(item, new ModelResourceLocation(model, "inventory"))
        MultiTextureModels.create(loc, textures, defaultTexture)
    }

    @SideOnly(Side.CLIENT)
    def refreshResources() =
        Minecraft.getMinecraft.refreshResources()

}

object Log {
    private[core] var logger: org.apache.logging.log4j.Logger = _

    def info(msg: String) = logger.log(Level.INFO, msg)

    //def debug(msg: String) = logger.log(Level.DEBUG, msg)

    //def warn(msg: String) = logger.log(Level.WARN, msg)

    //def err(msg: String) = logger.log(Level.ERROR, msg)

}
