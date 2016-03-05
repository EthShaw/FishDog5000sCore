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

import net.fishdog5000.core.handler.FishdogsCoreEventHandler
import net.minecraft.client.resources.model.{IBakedModel, ModelResourceLocation}
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.client.model.{IFlexibleBakedModel, ISmartItemModel}

class MultiTextureModel(texture_strs: Array[String], default_texture: String, baseloc: ModelResourceLocation) extends ISmartItemModel {
    var currentTexture = default_texture
    var basemodels: Map[String, IFlexibleBakedModel] = Map.empty
    var currentModel: IFlexibleBakedModel = null

    def modelReload(event: ModelBakeEvent) = {
        basemodels = Map.empty
        MultiTextureModels.reloadModels(this, event)
        //super.setParent(basemodels.get(currentTexture).get)
        currentModel = basemodels.get(currentTexture).get
    }

    def addModel(str: String, model: IFlexibleBakedModel) =
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

    override def getFaceQuads(face: EnumFacing) = currentModel.getFaceQuads(face)

    override def getGeneralQuads = currentModel.getGeneralQuads

    override def handleItemState(itemStack: ItemStack): IBakedModel = currentModel

    private[core] def getBaseModelLoc: ModelResourceLocation = baseloc
}

object MultiTextureModels {
    def create(baseModel: ModelResourceLocation, textures: Array[String], default: String): MultiTextureModel = {
        val model = new MultiTextureModel(textures, default, baseModel)
        FishdogsCoreEventHandler.registerMultiTexture(model)
        model
    }

    def reloadModels(multimodel: MultiTextureModel, event: ModelBakeEvent) {
        for (model <- multimodel.getTextureStrings)
            multimodel.addModel(model, event.modelManager.getModel(new ModelResourceLocation(model, "inventory")).asInstanceOf[IFlexibleBakedModel])
    }
}
