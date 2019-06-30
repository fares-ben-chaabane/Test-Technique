package fr.benchaabane.datalayer.books

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

// Fields should be annotated with var (Realm instruction)
open class BookEntity(@PrimaryKey var uic: String,
                      var title: String?,
                      var author: String?,
                      var summary: String?,
                      var coverUrl: String?,
                      var category: String?,
                      var distribution: String?,
                      var publishYear: String?,
                      var pagesCount: Int,
                      var rate: Int,
                      var isFavorite: Boolean): RealmObject() {
    constructor(): this("", null, null, null, null, null, null,
        null, 0,0, false)
}