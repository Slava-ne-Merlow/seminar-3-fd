package seminar.task01

fun transform(n: Int): Int {
    val str : String
    if (n % 2 == 0) { str = n.toString(2) + "00" }
    else { str = n.toString(2) + "11" }
    val a : Int = 10
    return str.toInt(2)
}

fun maxNBelow(limit: Int): Int {

    for (a in 0..10000) {
        if(transform(a) >= limit) {
            return a - 1
        }
    }
    return 0
}
