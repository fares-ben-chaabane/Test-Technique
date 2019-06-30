package fr.benchaabane.presentationlayer.tools

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import fr.benchaabane.presentationlayer.extensions.inflate


class ListAdapter<T>(private val items: List<T>,
                     @LayoutRes private val layout: Int,
                     private val createViewHolder: (View) -> ViewHolder<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createViewHolder(parent.inflate(layout))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) (holder as ViewHolder<T>).bind(items[position])
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        (holder as ViewHolder<T>).unbind()
    }
}


abstract class ViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(item: T) {}
    open fun unbind() {}
}
