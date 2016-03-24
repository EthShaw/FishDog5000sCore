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
package net.fishdog5000.core.basestuff

import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.Logger

abstract class BaseConfig(log: Logger) extends IBaseConfig {
    val logger = log

    def getConfigInteger(name: String, comment: String, defaultvalue: Int, config: Configuration, catagory: String): Int = {
        logger.info("Getting String..." + name + "...value:")
        val property = config.get(catagory, name, defaultvalue)
        property.setComment(comment)
        val value = property.getInt
        logger.info(value)
        value
    }

    def validateCatagory(validoptions: Array[String], config: Configuration, catagory: String) {
        val iter = config.getCategory(catagory).getValues.values.iterator
        while (iter.hasNext) {
            val optionname = iter.next.getName
            if (!validoptions.contains(optionname)) {
                logger.info("removing invalid option..." + optionname)
                config.getCategory(catagory).remove(optionname)
            }
        }
    }

    def getConfigBool(name: String, comment: String, defaultvalue: Boolean, config: Configuration, catagory: String): Boolean = {
        logger.info("Getting bool..." + name + "...value:")
        val property = config.get(catagory, name, defaultvalue)
        property.setComment(comment)
        val value = property.getBoolean(defaultvalue)
        logger.info(value)
        value
    }

    def getConfigString(name: String, comment: String, defaultvalue: String, config: Configuration, catagory: String): String = {
        logger.info("Getting String..." + name + "...value:")
        val property = config.get(Configuration.CATEGORY_GENERAL, name, defaultvalue)
        property.setComment(comment)
        val value = property.getString
        logger.info(value)
        value
    }

}

trait IBaseConfig {
    def init(event: FMLPreInitializationEvent)

    def configure()

    protected def postConfigure()

    protected def preConfigure()
}
