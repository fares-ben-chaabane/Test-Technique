package fr.benchaabane.presentationlayer.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import fr.benchaabane.presentationlayer.extensions.bundle
import kotlinx.android.parcel.Parcelize

class BaseDialogFragment : DialogFragment() {

    private val configuration by lazy { arguments!!.getParcelable<Configuration>(ARG_CONFIGURATION) }
    private val listener by lazy { DialogListener(configuration.id, (targetFragment ?: activity) as? DialogCallbacks) }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = with(AlertDialog.Builder(activity!!)) {
        configuration.title?.let { setTitle(it) }
        setMessage(configuration.message)
        configuration.positiveLabel?.let { setPositiveButton(it) { _, _ -> listener.onPositive() } }
        configuration.negativeLabel?.let { setNegativeButton(it) { _, _ -> listener.onNegative() } }
        setCancelable(configuration.isCancelable)
        create().apply {
            setCanceledOnTouchOutside(configuration.isCancelable)
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        listener.onDismiss()
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        listener.onCancel()
    }

    fun show(fragmentManager: FragmentManager?) {
        fragmentManager?.let { show(it, TAG) }
    }


    /* ------------------------------------- */
    /*               Listener                */
    /* ------------------------------------- */

    class DialogListener(dialogId: String?, callbacks: DialogCallbacks?) {

        init {
            if (dialogId != null && callbacks == null) {
                throw IllegalArgumentException("Dialog id is set but caller doesn't implement DialogCallbacks interface")
            }
        }

        val onPositive = { dialogId?.let { id -> callbacks?.onDialogPositive(id) } }
        val onNegative = { dialogId?.let { id -> callbacks?.onDialogNegative(id) } }
        val onCancel = { dialogId?.let { id -> callbacks?.onDialogCancel(id) } }
        val onDismiss = { dialogId?.let { id -> callbacks?.onDialogDismiss(id) } }
    }


    /* ------------------------------------- */
    /*            Configuration              */
    /* ------------------------------------- */

    @Parcelize
    private data class Configuration(val id: String?,
                                     val title: String?,
                                     val message: String,
                                     val positiveLabel: String?,
                                     val negativeLabel: String?,
                                     val isCancelable: Boolean) : Parcelable


    /* ------------------------------------- */
    /*                Static                 */
    /* ------------------------------------- */

    companion object {
        fun newInstance(message: String,
                        id: String? = null,
                        title: String? = null,
                        positiveLabel: String? = null,
                        negativeLabel: String? = null,
                        isCancelable: Boolean = true): BaseDialogFragment {
            return BaseDialogFragment().apply {
                arguments = bundle {
                    putParcelable(ARG_CONFIGURATION, Configuration(id = id,
                                                                   title = title,
                                                                   message = message,
                                                                   positiveLabel = positiveLabel,
                                                                   negativeLabel = negativeLabel,
                                                                   isCancelable = isCancelable))
                }
            }
        }
    }
}

interface DialogCallbacks {
    fun onDialogPositive(id: String) = Unit
    fun onDialogNegative(id: String) = Unit
    fun onDialogCancel(id: String) = Unit
    fun onDialogDismiss(id: String) = Unit
}

fun <T : FragmentActivity> T.showDialog(message: String,
                                         id: String? = null,
                                         title: String? = null,
                                         positiveLabel: String? = null,
                                         negativeLabel: String? = null,
                                         isCancelable: Boolean = true) {
    BaseDialogFragment.newInstance(message, id, title, positiveLabel, negativeLabel, isCancelable)
            .show(supportFragmentManager)
}

fun <T : Fragment> T.showDialog(message: String,
                                id: String? = null,
                                title: String? = null,
                                positiveLabel: String? = null,
                                negativeLabel: String? = null,
                                isCancelable: Boolean = true) {
    BaseDialogFragment.newInstance(message, id, title, positiveLabel, negativeLabel, isCancelable).let {
        it.setTargetFragment(this, USELESS_REQUEST_CODE)
        it.show(fragmentManager)
    }
}

@VisibleForTesting
val TAG: String = BaseDialogFragment::class.java.simpleName
private const val ARG_CONFIGURATION = "arg-configuration"
private const val USELESS_REQUEST_CODE = 847573451
