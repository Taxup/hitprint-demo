package kz.app.utils

import java.io.Serializable

data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
) : Serializable {

    /**
     * Returns string representation of the [Quadruple] including its [first], [second], [third] and [fourth] values.
     */
    override fun toString(): String = "($first, $second, $third, $fourth)"
}

/**
 * Converts this quadruple into a list.
 * @sample quadrupleToList
 */
fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)

infix fun <T: Pair<A, B>, K, A, B> T.toTriple(that: K) = Triple(this.first, this.second, that)

infix fun <T: Triple<A, B, C>, K, A, B, C> T.toQuad(that: K) = Quadruple(this.first, this.second, this.third, that)
