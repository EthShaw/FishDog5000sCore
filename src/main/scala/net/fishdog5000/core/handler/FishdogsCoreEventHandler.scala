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

import net.fishdog5000.core.MultiTextureModel
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.event.entity.player.EntityInteractEvent
import net.minecraftforge.fml.common.eventhandler.{EventPriority, SubscribeEvent}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

object FishdogsCoreEventHandler {
    private var multimodels: Vector[MultiTextureModel] = Vector.empty

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    def onModelBake(event: ModelBakeEvent) = {
        val modelregistry = event.getModelRegistry
        for (model <- multimodels) {
            val modelsloc = model.getBaseModelLoc
            model.modelReload(event)
            modelregistry.putObject(modelsloc, model)
        }
    }

    /*@SubscribeEvent TEST METHOD
    def testThingEvennt(e: EntityInteractEvent) = {
        for (model <- multimodels)
            if (Math.random() > 0.5)
                model.setCurrentTexture("minecraft:diamond")
            else
                model.setCurrentTexture("minecraft:flint")
    }*/

    @SideOnly(Side.CLIENT)
    def registerMultiTexture(model: MultiTextureModel) =
        multimodels = multimodels :+ model
}
