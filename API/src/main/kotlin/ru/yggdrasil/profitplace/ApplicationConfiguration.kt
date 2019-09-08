package ru.yggdrasil.profitplace

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.yggdrasil.profitplace.services.PlacesApiService
import ru.yggdrasil.profitplace.services.RentalOffersApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.swing.text.html.HTML.Tag.BODY

@Configuration
class ApplicationConfiguration {

    @Bean
    fun providePlacesApiService(): PlacesApiService {
        return getRetrofit("https://places.cit.api.here.com/places/v1/discover/").create(PlacesApiService::class.java)
    }

    @Bean
    fun provideRentalOffersService(): RentalOffersApiService{
        return getRetrofit("http://www.ads-api.ru/").create(RentalOffersApiService::class.java)
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .baseUrl(baseUrl)
                .build()
    }
}