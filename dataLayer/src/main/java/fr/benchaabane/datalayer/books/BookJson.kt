package fr.benchaabane.datalayer.books

import com.google.gson.annotations.SerializedName

data class BookWrapperJson(@SerializedName("results") val books: List<BookJson>)

data class BookJson(@SerializedName("objectId") val uic: String,
                    @SerializedName("title") val title: String,
                    @SerializedName("author") val author: String,
                    @SerializedName("distribution") val distribution: String?,
                    @SerializedName("summary") val summary: String?,
                    @SerializedName("cover") val coverUrl: String?,
                    @SerializedName("category") val category: String,
                    @SerializedName("year") val publishYear: String,
                    @SerializedName("pages") val pagesCount: Int,
                    @SerializedName("rate") val rate: Int)