package fr.benchaabane.test.di.modules

import dagger.Module
import dagger.Provides
import fr.benchaabane.domainlayer.books.*

@Module
class UseCasesModule {

    @Provides
    fun provideRetrieveBooksUseCase(repository: ILocalBooksRepository): RetrieveLocalBooksUseCase {
        return RetrieveLocalBooksUseCase(repository)
    }

    @Provides
    fun provideRetrieveNetworkBooksUseCase(repository: INetworkBooksRepository): RetrieveNetworkBooksUseCase {
        return RetrieveNetworkBooksUseCase(repository)
    }

    @Provides
    fun provideSaveBooksUseCase(localRepository: ILocalBooksRepository): SaveBooksUseCase {
        return SaveBooksUseCase(localRepository)
    }

    @Provides
    fun provideBookDetailsUseCase(localRepository: ILocalBooksRepository): RetrieveBookDetailsUseCase {
        return RetrieveBookDetailsUseCase(localRepository)
    }
}