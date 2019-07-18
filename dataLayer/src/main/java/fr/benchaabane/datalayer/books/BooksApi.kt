package fr.benchaabane.datalayer.books

import io.reactivex.Single
import retrofit2.http.GET

interface BooksApi {

    @GET("classes/Book")
    fun getBooks(): Single<BookWrapperJson>
}