package fr.benchaabane.presentationlayer.extensions

import android.os.Bundle

fun bundle(init: Bundle.() -> Unit) = Bundle().apply(init)