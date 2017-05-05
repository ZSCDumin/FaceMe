package cn.iome.fakotlin.function

/**
 * Created by haoping on 17/5/3.
 * TODO
 */
interface Consumer<in T> {
    fun accept(t: T)
}
