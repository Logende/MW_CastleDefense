package org.neubauerfelix.manawars.manawars.storage


class ConfigurationDecoratorInheritance(val config: Configuration, val parentConfig: Configuration) : Configuration(config.self, config.defaults) {

    override fun <T> get(path: String, def: T?): T? {
        val value = super.get(path, def)
        return if (value == null) {
            parentConfig.get(path, def)
        } else {
            value
        }
    }

}