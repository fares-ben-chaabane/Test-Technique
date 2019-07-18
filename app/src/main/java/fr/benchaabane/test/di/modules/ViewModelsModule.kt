package fr.benchaabane.test.di.modules

import dagger.Module
import dagger.Provides
import fr.benchaabane.domainlayer.books.RetrieveBookDetailsUseCase
import fr.benchaabane.domainlayer.books.RetrieveLocalBooksUseCase
import fr.benchaabane.domainlayer.books.RetrieveNetworkBooksUseCase
import fr.benchaabane.domainlayer.books.SaveBooksUseCase
import fr.benchaabane.presentationlayer.tools.Resources
import fr.benchaabane.presentationlayer.ui.books.details.BookDetailsViewModel
import fr.benchaabane.presentationlayer.ui.books.listing.BookListViewModel
import fr.benchaabane.presentationlayer.ui.splash.SplashViewModel

@Module
class ViewModelsModule {

    @Provides
    fun provideSplashViewModel(saveBooksUseCase: SaveBooksUseCase,
                               retrieveLocalBooksUseCase: RetrieveLocalBooksUseCase,
                               retrieveNetworkBooksUseCase: RetrieveNetworkBooksUseCase): SplashViewModel {
        return SplashViewModel(saveBooksUseCase, retrieveNetworkBooksUseCase, retrieveLocalBooksUseCase)
    }

    @Provides
    fun provideBookListViewModel(retrieveLocalBooksUseCase: RetrieveLocalBooksUseCase,
                                 retrieveNetworkBooksUseCase: RetrieveNetworkBooksUseCase,
                                 saveBooksUseCase: SaveBooksUseCase,
                                 resources: Resources): BookListViewModel {
        return BookListViewModel(retrieveLocalBooksUseCase, retrieveNetworkBooksUseCase,
            saveBooksUseCase, resources)
    }

    @Provides
    fun provideBookDetailsViewModel(retrieveBookDetailsUseCase: RetrieveBookDetailsUseCase,
                                    resources: Resources): BookDetailsViewModel {
        return BookDetailsViewModel(retrieveBookDetailsUseCase, resources)
    }
}