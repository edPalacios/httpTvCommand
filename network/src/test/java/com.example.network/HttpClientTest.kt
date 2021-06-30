import com.example.network.Request
import com.example.network.executeOkHttp
import org.junit.Test

class HttpClientTest {

    @Test
    fun testExecuteOkHttpCall(){
        val response = executeOkHttp(Request(url="www.google.com"))
        println(response)
    }
}