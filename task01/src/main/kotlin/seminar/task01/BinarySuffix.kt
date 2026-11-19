package seminar.task01

fun transform(n: Int): Int {
    var s = n.toString(radix = 2)
    if (n % 2 == 0) {
        s += "10"
    } else {
        s += "01"
    }
    return s.toInt(radix = 2)
}


fun maxNBelow(limit: Int): Int {
    TODO("Найдите максимальное N, для которого R < limit")
}
