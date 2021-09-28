package practice

import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

object Threadsafe {

    @JvmStatic
    fun main(args: Array<String>) {

    }

}



internal class SuperMap {
    private val map = ConcurrentHashMap<String, String>()

    operator fun set(key: String, value: String): String {
        this.map[key] = value
        return key
    }

    operator fun get(key: String): String? {
        return this.map[key]
    }

    fun remove(key: String): String {
        this.map.remove(key)
        return key
    }

}
