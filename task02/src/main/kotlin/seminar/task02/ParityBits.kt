package seminar.task02

private fun appendParityBit(x: Int): Int =
    (x shl 1) or (x.countOneBits() and 1)

fun transform(n: Int): Int =
    appendParityBit(appendParityBit(n))

fun minNAbove(limit: Int): Int {
    for (n in 1..10_000) {
        if (transform(n) > limit) return n
    }
    return 0
}