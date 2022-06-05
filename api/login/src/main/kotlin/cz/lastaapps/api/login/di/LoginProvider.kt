package cz.lastaapps.api.login.di

import android.app.Application
import cz.lastaapps.api.login.AuthRepo
import cz.lastaapps.api.login.AuthRepoImpl
import cz.lastaapps.api.login.auth.AuthRequests
import cz.lastaapps.api.login.auth.AuthRequestsImpl
import cz.lastaapps.api.login.db.UserDatabase
import cz.lastaapps.api.login.db.UserStorage
import cz.lastaapps.api.login.db.UserStorageImpl
import cz.lastaapps.api.login.school.SchoolRepo
import cz.lastaapps.api.login.school.SchoolRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal class LoginProvider {

    @Provides
    fun provideDatabase(context: Application): UserDatabase = UserDatabase.createDatabase(context)

    @Provides
    fun provideUserStorage(db: UserDatabase): UserStorage = UserStorageImpl(db)

    @Provides
    fun provideSchoolRepo(): SchoolRepo = SchoolRepoImpl()

    @Provides
    fun providesAuthRequests(): AuthRequests = AuthRequestsImpl()

    @Provides
    fun providesAuthRepo(
        authRequests: AuthRequests,
        userStorage: UserStorage
    ): AuthRepo = AuthRepoImpl(authRequests, userStorage, CoroutineScope(Dispatchers.Default))

//    @Provides
//    fun providesUserRepo(): UserRepo = UserRepoImpl()
}