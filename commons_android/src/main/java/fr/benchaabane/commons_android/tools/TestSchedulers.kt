package fr.benchaabane.commons_android.tools

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


object TestSchedulers {

    lateinit var io: Scheduler
        private set
    lateinit var computation: Scheduler
        private set
    lateinit var trampoline: Scheduler
        private set
    lateinit var ui: Scheduler
        private set

    fun init(computation: Scheduler = Schedulers.computation(),
             io: Scheduler = Schedulers.io(),
             trampoline: Scheduler = Schedulers.trampoline(),
             ui: Scheduler = AndroidSchedulers.mainThread()) {
        TestSchedulers.io = io
        TestSchedulers.computation = computation
        TestSchedulers.trampoline = trampoline
        TestSchedulers.ui = ui
    }
}

fun TestSchedulers.initForTest() {
    init(io = Schedulers.trampoline(),
         computation = Schedulers.trampoline(),
         trampoline = Schedulers.trampoline(),
         ui = Schedulers.trampoline())
}

fun TestSchedulers.initForAndroidTest() {
    initForTest()
}