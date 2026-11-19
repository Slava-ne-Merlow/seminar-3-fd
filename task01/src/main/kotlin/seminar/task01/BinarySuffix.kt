package seminar.task01

fun transform(n: Int): Int {
    val str : String
    if (n % 2 == 0) { str = n.toString(2) + "00" }
    else { str = n.toString(2) + "11" }
    val a : Int = 10
    return str.toInt(2)
}

fun maxNBelow(limit: Int): Int {
    TODO("Найдите максимальное N, для которого R < limit")
}
