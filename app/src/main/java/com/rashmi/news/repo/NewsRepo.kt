import com.rashmi.news.helpers.IConstant
import com.rashmi.news.helpers.NewsAPI
import com.rashmi.news.model.APIResponse

class NewsRepo(private val api: NewsAPI) {
    suspend fun getNews(): APIResponse? {
        try {
            val response = api.getNews(1, IConstant.apiKey).execute()
            if (response.isSuccessful) {
                return response.body()
            } else {
                println("Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            println("Exception: ${e.localizedMessage}")
            e.printStackTrace()
        }
        return null
    }
}