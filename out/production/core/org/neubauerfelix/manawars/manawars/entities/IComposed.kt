package org.neubauerfelix.manawars.manawars.entities



interface IComposed: IJumpable, IAnimated {


    fun hasProperty(c: Class<*>): Boolean
    fun <T> getProperty(c: Class<T>): T?

}