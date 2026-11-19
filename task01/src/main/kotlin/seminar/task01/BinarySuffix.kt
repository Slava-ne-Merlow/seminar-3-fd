package seminar.task01

fun transform(n: Int): Int {
    var s = n.toString(2)
    if (n % 2 == 0){
        s += "00"
    } else {
        s += "11"
    }
    val ans = s.toInt(2)
    return ans
}

fun maxNBelow(limit: Int): Int {
    var ans = 0
    for (n in 1..10000){
        val res = transform(n)
        if (res < limit){
            ans = n
        }
    }
    return ans
}
