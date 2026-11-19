package seminar.task01

fun transform(n: Int): Int {
    var s = n.toString(2)
    if (n % 2 == 0){
        s += "00"
    } else {
        s += "11"
    }
    val ans = s.toInt()
    return ans
}

fun maxNBelow(limit: Int): Int {
    TODO("Найдите максимальное N, для которого R < limit")
}
