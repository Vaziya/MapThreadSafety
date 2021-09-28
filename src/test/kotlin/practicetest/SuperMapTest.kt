package practice

import java.util.ArrayList
import java.util.HashSet
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.Test
import kotlin.test.assertEquals


class SuperMapTest {

    @Test
    fun addsAndRetrieveSuperMap_Success() {
        val superMap = SuperMap()
        val threads = 1000
        val service = Executors.newFixedThreadPool(threads)
        val latch = CountDownLatch(1)
        val running = AtomicBoolean()
        val overlaps = AtomicInteger()
        val futures = ArrayList<Future<String>>(threads)
        for (t in 0 until threads) {
            val title = String.format("Book #%d", t)
            val key = t.toString()
            futures.add(
                service.submit<String> {
                    latch.await()
                    if (running.get()) {
                        overlaps.incrementAndGet()
                    }
                    running.set(true)
                    val id = superMap.set(key, title)
                    running.set(false)
                    id
                }
            )
        }
        latch.countDown()
        val ids = HashSet<String>()
        for (f in futures) {
            try {
                ids.add(f.get())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }

        }
        assertEquals(ids.size, threads)
    }
}

