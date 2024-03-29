package com.example.chatapp.module

import android.content.Context
import androidx.room.Room
import com.example.chatapp.helpers.utils.Utils
import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.repositoryLocal.database.AppDataBase
import com.example.chatapp.useCases.AuthUseCase
import com.example.chatapp.useCases.IAuthUseCase
import com.example.chatapp.viewModels.chat.useCase.ChatUseCase
import com.example.chatapp.viewModels.chat.useCase.IChatUseCase
import com.example.chatapp.viewModels.home.BasePresenter
import com.example.chatapp.viewModels.home.useCase.HomeUseCase
import com.example.chatapp.viewModels.home.useCase.IHomeUseCase
import com.example.chatapp.viewModels.network.ConnectivityState
import com.example.chatapp.viewModels.notifications.provider.INotificationUseCase
import com.example.chatapp.viewModels.notifications.provider.NotificationUseCase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule (private var context: Context) {

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

    /**
     * @param remote data provider
     * @return AuthUseCase class that provide all authentication(Login/ SignIn) use cases
     */
    @Provides
    fun providerAuthUseCase(repo: RemoteDataProvider): IAuthUseCase = AuthUseCase(repo)

    /**
     * @param remote data provider
     * @return NotificationUseCase class that provide all use cases
     */
    @Provides
    fun providerNotificationUseCase(repo: RemoteDataProvider) : INotificationUseCase = NotificationUseCase(repo)


    /**
     * @param remote data provider
     * @return HomeUseCase class that provide all use cases
     */
    @Provides
    fun providerHomeUseCase() : IHomeUseCase = HomeUseCase()

    /**
     * @return chatRoom UseCase class that provide all use cases
     */
    @Provides
    fun providerChatUseCase() : IChatUseCase = ChatUseCase()

    @Provides
    @Singleton
    fun provideAppDataBase(): AppDataBase = Room.databaseBuilder(context,
        AppDataBase::class.java, "chat_database"
    ).build()

    @Provides
    @Singleton
    fun provideConnectivityState(): ConnectivityState = ConnectivityState(context)

    @Provides
    fun provideBasePresenter(): BasePresenter = BasePresenter()

}