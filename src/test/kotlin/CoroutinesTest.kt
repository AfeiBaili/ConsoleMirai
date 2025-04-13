import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class CoroutinesTest {
    @Test
    fun test() {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                delay(1000)
                println(1)
            }
            println(2)
        }
        runBlocking {

            delay(2000)
        }
    }
}