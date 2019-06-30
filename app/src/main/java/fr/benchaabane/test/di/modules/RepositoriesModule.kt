package fr.benchaabane.test.di.modules

import dagger.Module
import dagger.Provides
import fr.benchaabane.datalayer.books.BooksApi
import fr.benchaabane.datalayer.books.LocalBooksRepository
import fr.benchaabane.datalayer.books.NetworkBooksRepository
import fr.benchaabane.domainlayer.books.ILocalBooksRepository
import fr.benchaabane.domainlayer.books.INetworkBooksRepository
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideLocalBooksRepository(): ILocalBooksRepository {
        return LocalBooksRepository()
    }

    @Provides
    @Singleton
    fun provideNetworkBooksRepository(api: BooksApi): INetworkBooksRepository {
        return NetworkBooksRepository(api)
    }
}