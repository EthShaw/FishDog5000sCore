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

import java.util

import net.fishdog5000.core.handler.FishdogsCoreEventHandler
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model._
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.world.World
import net.minecraftforge.client.event.ModelBakeEvent

class MultiTextureModel(texture_strs: Array[String], default_texture: String, baseloc: ModelResourceLocation) extends ItemOverrideList(new util.ArrayList[ItemOverride]()) with IBakedModel {
    var currentTexture = default_texture
    var basemodels: Map[String, IBakedModel] = Map.empty
    var currentModel: IBakedModel = null

    def modelReload(event: ModelBakeEvent) = {
        basemodels = Map.empty
        MultiTextureModels.reloadModels(this, event)
        currentModel = basemodels.get(currentTexture).get
    }

    def addModel(str: String, model: IBakedModel) =
        basemodels += (str -> model)

    def getTextureString = currentTexture

    def getTextureStrings = texture_strs

    def setCurrentTexture(texture: String) = {
        if (texture_strs.contains(texture))
            currentTexture = texture
        if (basemodels.keySet.contains(texture))
            currentModel = basemodels.get(currentTexture).get
    }

    override def getParticleTexture = currentModel.getParticleTexture

    override def getItemCameraTransforms = currentModel.getItemCameraTransforms

    override def isAmbientOcclusion = currentModel.isAmbientOcclusion

    override def isBuiltInRenderer = currentModel.isBuiltInRenderer

    override def isGui3d = currentModel.isGui3d

    def getQuads(state: IBlockState, side: EnumFacing, rand: Long) = currentModel.getQuads(state, side, rand)

    private[core] def getBaseModelLoc: ModelResourceLocation = baseloc

    override def getOverrides: ItemOverrideList = this

    override def applyOverride(stack: ItemStack, worldIn: World, entityIn: EntityLivingBase) =
        new ModelResourceLocation(currentTexture, "inventory")

    override def handleItemState(originalModel: IBakedModel, stack: ItemStack, world: World, entity: EntityLivingBase) =
        currentModel
}

object MultiTextureModels {
    def create(baseModel: ModelResourceLocation, textures: Array[String], default: String): MultiTextureModel = {
        val model = new MultiTextureModel(textures, default, baseModel)
        FishdogsCoreEventHandler.registerMultiTexture(model)
        model
    }

    def reloadModels(multimodel: MultiTextureModel, event: ModelBakeEvent) {
        for (model <- multimodel.getTextureStrings)
            multimodel.addModel(model, event.getModelManager.getModel(new ModelResourceLocation(model, "inventory")))
    }
}
