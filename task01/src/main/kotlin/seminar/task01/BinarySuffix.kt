package seminar.task01

fun transform(n: Int): Int {
    var k = n.toString(2)
    if (n % 2 == 0) {
        k += "00"
    } else {
        k += "11"
    }
    return k.toInt(2)
}

fun maxNBelow(limit: Int): Int {
    for (i in limit downTo 1) {
        if (transform(i) < limit) return i
    }
    return 0
}
