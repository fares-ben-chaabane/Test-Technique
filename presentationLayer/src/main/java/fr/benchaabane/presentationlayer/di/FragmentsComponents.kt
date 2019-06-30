package fr.benchaabane.presentationlayer.di

import dagger.Subcomponent
import fr.benchaabane.presentationlayer.ui.books.details.BookDetailFragment
import fr.benchaabane.presentationlayer.ui.books.listing.BooksListFragment

@Subcomponent
interface FragmentsComponents {
    fun inject(fragment: BooksListFragment)
    fun inject(fragment: BookDetailFragment)
}