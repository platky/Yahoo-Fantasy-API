import java.util.prefs.Preferences

//TODO rename
object PropertiesHelper {
    /*private val input = javaClass.classLoader.getResourceAsStream("prefs.properties")
    private var file : File
    private var output : PrintWriter
    private val properties = Properties()*/
    private val preferences = Preferences.userNodeForPackage(this::class.java)

    init {
        /*println(javaClass.classLoader.getResource("prefs.properties").path)
        file = File(javaClass.classLoader.getResource("prefs.properties").path)
        file.setWritable(true)
        println("can write ${file.canWrite()}")
        output = PrintWriter(file)
        properties.load(input)*/
    }

    //fun getProperty(key: String) = properties.getProperty(key)
    fun getProperty(key: String) = preferences.get(key, null)

    fun setProperty(key: String, value: String) {
        preferences.put(key, value)
    }/*fun setProperty(key: String, value: String) {
        properties.setProperty(key, value)
        saveProperties()
    }*/

    /*private fun saveProperties() {
        properties.store(output, "prefs")
    }*/

    fun clearProperty(key: String) = preferences.remove(key)
}