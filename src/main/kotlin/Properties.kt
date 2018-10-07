import java.util.prefs.Preferences

//TODO rename
object PropertiesHelper {
    private val preferences = Preferences.userNodeForPackage(this::class.java)

    fun getProperty(key: String): String? = preferences.get(key, null)

    fun setProperty(key: String, value: String) {
        preferences.put(key, value)
    }

    fun clearProperty(key: String) = preferences.remove(key)
}