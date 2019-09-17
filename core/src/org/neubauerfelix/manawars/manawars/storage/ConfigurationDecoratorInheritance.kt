package org.neubauerfelix.manawars.manawars.storage


class ConfigurationDecoratorInheritance(val config: Configuration, val parentConfig: Configuration,
                                        private var enabled: Boolean = true) : Configuration(config.self, config.defaults) {

    init {
        require(config != parentConfig)
    }

    override fun <T> get(path: String, def: T?): T? {
        val value = super.get<T>(path, null)
        return if (enabled && value == null) {
            parentConfig.get(path, def)
        } else {
            value
        }
    }

    fun containsSelf(path: String?): Boolean {
        enabled = false
        val b = super.contains(path)
        enabled = true
        return b
    }

    override fun getKeys() : MutableCollection<String> {
        val keys = mutableSetOf<String>()
        if (enabled) {
            keys.addAll(parentConfig.keys)
        }
        keys.addAll(super.getKeys())
        return keys
    }
}