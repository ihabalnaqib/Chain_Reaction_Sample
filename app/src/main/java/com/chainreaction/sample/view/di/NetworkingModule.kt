package com.chainreaction.sample.view.di

import android.content.Context
import com.chainreaction.sample.model.network_utils.APIServices
import com.chainreaction.sample.model.network_utils.remote.NetworkResponseAdapterFactory
import com.chainreaction.sample.model.utils.ACCEPT
import com.chainreaction.sample.model.utils.AppUtils
import com.chainreaction.sample.model.utils.BASE_URL
import com.chainreaction.sample.model.utils.REQUEST_TIMEOUT
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit



@Module
@InstallIn(ActivityRetainedComponent::class)
object NetworkingModule {


    @Provides
    fun provideNetworkInterceptor(): Interceptor {

        return Interceptor { chain: Interceptor.Chain ->
            val builder = chain.request()
                .newBuilder()
                .addHeader(ACCEPT, "en")
            chain.proceed(builder.build())
        }
    }


    @Provides
    fun provideRetrofitClient(networkInterceptor: Interceptor): OkHttpClient {


        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(networkInterceptor)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    fun provideRetrofitServices(
        okHttpClient: OkHttpClient,
        @ApplicationContext context: Context
    ): APIServices =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory((NetworkResponseAdapterFactory(context)))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build().create(APIServices::class.java)


}