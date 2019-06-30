package fr.benchaabane.datalayer.books

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface BooksApi {

    @GET("classes/Book")
    fun getBooks(): Single<BookWrapperJson>

    @PUT("classes/Book/{uic}")
    fun updateBook(@Path("uic") uic: String, @Body book: BookUpdateJson): Completable
}