package fr.benchaabane.presentationlayer.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import fr.benchaabane.presentationlayer.extensions.findNavController

class Router {

    fun goTo(caller: Context, intent: Intent) {
        if (caller !is Activity) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        caller.startActivity(intent)
    }

    fun goTo(caller: Context, destinationId: Int, args: Bundle? = null) {
        if (caller is Activity) caller.findNavController().navigate(destinationId, args)
    }

}
interface Navigable {
    val router: Router
}

fun <T> T.goTo(intent: Intent) where T : Context, T : Navigable = router.goTo(this, intent)
fun <T> T.goTo(destinationId: Int, args: Bundle? = null) where T : Context, T : Navigable = router.goTo(this, destinationId, args)