package org.neubauerfelix.manawars.manawars.components

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.IComponent

class MComponentTable(x: Float, y: Float, columns: Array<Array<IComponent>>, distanceColumns: Float, distanceRows: Float) :
        MComponentContainer(x, y){



    private val columnWidths: Array<Float>
    private val rowHeights: Array<Float>
    private var offsetHor: Float = 0f
    private var offsetVer: Float = 0f


    init{
        //Find column and row sizes
        columnWidths = Array(columns.size){0f}
        rowHeights = Array(columns[0].size){0f}
        for(indexColumn: Int in 0 until columns.size){
            val column = columns[indexColumn]
            for(indexRow: Int in 0 until column.size){
                var component = column[indexRow]
                columnWidths[indexColumn] = Math.max(columnWidths[indexColumn], component.width)
                rowHeights[indexRow] = Math.max(rowHeights[indexRow], component.height)
            }
        }


        //Place components
        for(indexColumn: Int in 0 until columns.size){
            val column = columns[indexColumn]
            for(indexRow: Int in 0 until column.size){
                var component = column[indexRow]
                val x = indexColumn * (distanceColumns) + offsetHor + columnWidths.filterIndexed { index, value -> index < indexColumn }.sum()
                val y = indexRow * (distanceRows + rowHeights[indexRow]) + offsetVer
                component.setLocation(x, y)
                addComponent(component)
            }
        }
    }


    override fun draw(batcher: Batch, offsetX: Float, offsetY: Float) {
        //batcher.draw(imageHandler.getTextureRegionButton("background"), offsetX + x, offsetY + y, width, height)
        super.draw(batcher, offsetX, offsetY)
    }


}
