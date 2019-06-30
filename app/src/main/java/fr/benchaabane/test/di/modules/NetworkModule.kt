package fr.benchaabane.test.di.modules

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import fr.benchaabane.commons_android.tools.TestSchedulers
import fr.benchaabane.datalayer.books.BooksApi
import fr.benchaabane.test.BuildConfig
import fr.benchaabane.test.network.HeaderInterceptor
import fr.benchaabane.test.network.NetworkConfig
import fr.benchaabane.test.network.buildHttpClient
import fr.benchaabane.test.network.networkConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkConfig(): NetworkConfig {
        return networkConfig(BuildConfig.ENV)
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): HeaderInterceptor {
        return HeaderInterceptor()
    }

    @Provides
    @Singleton
    fun provideHttpCache(context: Context): Cache {
        return Cache(context.cacheDir, NetworkConfig.CACHE_SIZE)
    }

    @Provides
    @Singleton
    fun provideHttpClient(cache: Cache,
                          networkConfig: NetworkConfig,
                          headerInterceptor: HeaderInterceptor): OkHttpClient {
        return OkHttpClient.Builder().buildHttpClient(cache,
                                                      networkConfig,
                                                      headerInterceptor = headerInterceptor)
    }


    @Provides
    @Singleton
    fun provideRetrofit(networkConfig: NetworkConfig,
                        gson: Gson,
                        httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(httpClient)
                .baseUrl(networkConfig.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(TestSchedulers.io))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }


    @Provides
    @Singleton
    fun provideBooksApi(retrofit: Retrofit): BooksApi {
        return retrofit.create(BooksApi::class.java)
    }


}
