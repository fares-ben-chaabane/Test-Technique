package fr.benchaabane.presentationlayer.tools

interface Resources {
    fun getString(id: Int): String
    fun getText(id: Int): CharSequence
    fun getString(id: Int, vararg values: Any): String
}

class AndroidResources(private val resources: android.content.res.Resources) : Resources {
    override fun getString(id: Int): String = resources.getString(id)
    override fun getText(id: Int): CharSequence = resources.getText(id)
    override fun getString(id: Int, vararg values: Any): String = resources.getString(id, *values)
}