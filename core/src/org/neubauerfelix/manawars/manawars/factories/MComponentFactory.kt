package org.neubauerfelix.manawars.manawars.factories

import com.badlogic.gdx.graphics.g2d.GlyphLayout
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.IImageHandler
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.components.MComponentContainer
import org.neubauerfelix.manawars.manawars.components.MComponentTable
import org.neubauerfelix.manawars.manawars.components.MImage
import org.neubauerfelix.manawars.manawars.components.MTextLabel
import org.neubauerfelix.manawars.manawars.handlers.FontHandler.MWFont

class MComponentFactory(imageHandler: IImageHandler) : IComponentFactory{

    private val imageHandler = imageHandler


    override fun createTable(keys: List<String>, values: List<String>, x: Float, y: Float,
                    width: Float, height: Float, maximumKeyColumnPercentage: Float, distanceColumns: Float,
                             distanceRows: Float): IComponent{
        assert(keys.size == values.size)

        val columns: Array<Array<IComponent?>> = Array(2){
            arrayOfNulls<IComponent?>(keys.size)
        }

        val keysWidthMax = width * maximumKeyColumnPercentage
        var keysWidth = 0f
        for(index in 0 until keys.size){
            val key = keys[index]
            val component = createComponentText(key, MWFont.HEADING, keysWidthMax)
            keysWidth = Math.max(keysWidth, component.width)
            columns[0][index] = component
        }
        assert(keysWidth <= keysWidthMax)

        var valuesWidth = width - keysWidth

        for(index in 0 until values.size){
            val value = values[index]
            val component = createComponentText(value, MWFont.TEXT, valuesWidth)
            columns[1][index] = component
        }

        //todo: if not enough height then add arrows to navigate between pages
        return MComponentTable(x, y, columns as Array<Array<IComponent>>, distanceColumns, distanceRows)
    }

    override fun createComponentText(text: String, font: MWFont, maximumWidth: Float, distanceLines: Float,
                            fontScale: Float): IComponent{
        val layout = GlyphLayout()
        val lineHeight = MConstants.getVisualLineHeight(font, fontScale)

        if(!text.contains("#icon.")) { //Text without icon that fits in one line
            layout.setText(font.getFont(), text)
            if (layout.width <= maximumWidth){
                return MTextLabel(0f, 0f, text, font, scale = fontScale)
            }
        }
        if(text.startsWith("#icon.") && !text.contains(" ")){ //Icon without text
            return createComponentIcon(0f, 0f, text.replace("#icon.", ""), lineHeight, maximumWidth)
        }

        //Container which can contain text, icons and multiple lines
        val container = MComponentContainer(0f, 0f)
        val words = text.split(" ")
        var currentLine: String? = null
        var currentX = 0f
        var currentY = 0f

        for(word in words){
            var widthLeft = maximumWidth - currentX
            val newLineProposal = if (currentLine == null) word else currentLine + " " + word
            layout.setText(font.getFont(fontScale), newLineProposal)
            val icon = word.startsWith("#icon.")
            val linebreak = layout.width > widthLeft
            if(icon || linebreak){ //finish current line
                if(linebreak && currentLine == null){
                    currentLine = newLineProposal
                }
                if(currentLine != null) {
                    val component = MTextLabel(currentX, currentY, currentLine, font, fontScale)
                    container.addComponent(component)
                    currentX = component.right
                    currentLine = null
                }
                if(icon) { //Place icon on right of existing components or in new line if necessary
                    val component = createComponentIcon(currentX, currentY, word.replace("#icon.", ""),
                            lineHeight, widthLeft)
                    if(component.width > widthLeft){
                        currentX = 0f
                        currentY += (lineHeight + distanceLines)
                        component.setLocation(currentX, currentY)
                    }
                    container.addComponent(component)
                    currentX = component.right
                }
                if(linebreak && !icon){
                    currentLine = word
                    currentX = 0f
                    currentY += (lineHeight + distanceLines)
                }
            }else{
                currentLine = newLineProposal
            }
        }
        //Add last text that was not forced to be added within the loop
        if(currentLine != null) {
            val component = MTextLabel(currentX, currentY, currentLine, font, fontScale)
            container.addComponent(component)
        }
        return container
    }

    fun createComponentIcon(x: Float, y: Float, iconName: String, height: Float, maximumWidth: Float = Float.MAX_VALUE): IComponent{
        val texture = imageHandler.getTextureRegionButton(iconName)
        var width = texture.regionWidth * (height / texture.regionHeight)
        var height = height
        if(width > maximumWidth){
            width = maximumWidth
            height = texture.regionHeight * (width / texture.regionWidth)
        }
        return MImage(x, y, width, height, texture)
    }
}