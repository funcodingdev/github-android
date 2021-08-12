package cn.funcoding.github.mvp.impl

import androidx.fragment.app.Fragment
import cn.funcoding.github.mvp.IMvpView
import cn.funcoding.github.mvp.IPresenter
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

abstract class BaseFragment<out P : BasePresenter<IMvpView<P>>> :
    IMvpView<P>, Fragment() {
    override val presenter: P

    init {
        presenter = createPresenterKt()
        presenter.view = this
    }

    fun createPresenterKt(): P {
        sequence<List<KType>> {
            var thisClass: KClass<*> = this@BaseFragment::class
            while (true) {
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap {
            it.flatMap {
                it.arguments
            }.asSequence()
        }.first {
            it.type?.jvmErasure?.isSubclassOf(IPresenter::class) ?: false
        }.let {
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }
    }
}