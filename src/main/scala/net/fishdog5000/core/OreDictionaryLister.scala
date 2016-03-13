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

import net.minecraft.command.{CommandBase, ICommand, ICommandSender}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagList, NBTTagString}
import net.minecraft.util.ChatComponentTranslation
import net.minecraftforge.oredict.OreDictionary


object OreDictionaryLister extends CommandBase with ICommand {
    //private static ChatStyle styleRed = new ChatStyle().setColor(EnumChatFormatting.RED)

    private var aliases = List("getOreDictionary")

    def getCommandName = "getOreDictionary"

    def getCommandUsage(sender: ICommandSender): String = "getOreDictionary"

    def processCommand(icommandsender: ICommandSender, args: Array[String]) = {
        /*String str
        if(args.length < 1)
        {
            icommandsender.addChatMessage(new ChatComponentTranslation("Not Enought Args!").setChatStyle(styleRed))
            icommandsender.addChatMessage(new ChatComponentTranslation("Usage: " + getCommandUsage(icommandsender)).setChatStyle(styleRed))
            return
        }
        else
        {
            str = args[0].trim()
            if(!str.equalsIgnoreCase("blocks") && !str.equalsIgnoreCase("fluids"))
            {
                icommandsender.addChatMessage(new ChatComponentTranslation("Invalid Args! (" + args[0] + ")").setChatStyle(styleRed))
                icommandsender.addChatMessage(new ChatComponentTranslation("Usage: " + getCommandUsage(icommandsender)).setChatStyle(styleRed))
                return
            }
        }*/

        icommandsender match {
            case player: EntityPlayer =>

                val book = new ItemStack(Items.written_book)
                val bookPages = new NBTTagList()

                //if(str.equalsIgnoreCase("blocks"))
                //{
                val entries = OreDictionary.getOreNames
                for (s <- entries.sorted) {
                    val ores = OreDictionary.getOres(s)
                    var oresstring = s + " = "
                    for (i <- 0 until ores.size())
                        try {
                            oresstring = oresstring + ores.get(i).getDisplayName + ", "
                        }
                        catch {
                            case e: Exception => Log.logger.info("Exception caught: " + e)
                        }
                    var allores = oresstring.substring(0, oresstring.length() - 2)
                    while (allores.length() > 200) {
                        bookPages.appendTag(new NBTTagString(allores.substring(0, 199)))
                        allores = allores.substring(200)
                    }
                    bookPages.appendTag(new NBTTagString(allores))
                }
                //}
                //else
                //{
                /*FluidDictionary
                    Set<String> entries = map.keySet()
                    for(String s : entries)
                    {
                        List ores = map<s>

                        String fluidstring = s + " = "
                        for(ItemStack item : ores)
                            fluidstring = fluidstring + item.getDisplayName() + ", "
                        bookPages.appendTag(new NBTTagString(fluidstring.substring(0, fluidstring.length() - 2)))
                    }*/
                //}

                book.setTagInfo("pages", bookPages)
                book.setTagInfo("author", new NBTTagString("The Ore Dictionary Auto-Reader"))
                book.setTagInfo("title", new NBTTagString("Copy of the Ore Dictionary"))

                player.inventory.addItemStackToInventory(book)
            case _ =>
                icommandsender.addChatMessage(new ChatComponentTranslation("This is a player only Command!"))
        }
    }

    /*public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
    {
        if(icommandsender instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) icommandsender
            boolean isOp = false

            for(String uuid : MinecraftServer.getServer().getConfigurationManager().func_152603_m(/* User list ops *//*).func_152685_a())
            {
                if(uuid == player.getUniqueID().toString())
                    isOp = true
            }

            MinecraftServer server = MinecraftServer.getServer()

            if (isOp || (server.isSinglePlayer() && server.worldServers[0].getWorldInfo().areCommandsAllowed()))
                return true
            else
                return false
        }
        else
        {
            return false
        }
    }*/*/
}
