package com.example.chatapp.module

import com.example.chatapp.helpers.utils.Utils
import com.example.chatapp.remoteRepository.RemoteDataProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule {

    /**
     *  @return Utils class
     *
     * */
    @Provides
    fun provideUtils(): Utils = Utils()

    /**
     *  @param Utils class
     *  @return retrofit builder
     *
     */
    @Provides
    @Singleton
    fun providerRetrofit(utils: Utils): Retrofit = utils.getRetrofitBuilder()


    /**
     * @param Retrofit builder
     * @return ApiProvider
     */
    @Provides
    @Singleton
    fun providerApiRepository(retrofit: Retrofit): RemoteDataProvider = RemoteDataProvider(retrofit)

}