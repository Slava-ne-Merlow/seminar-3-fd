package seminar.task01

fun transform(n: Int): Int {
    var s = n.toString(2)

    if (n % 2 == 0){
        s += "00"
    } else {
        s += "11"
    }

    return s.toInt(2)
}

fun maxNBelow(limit: Int): Int {
    var max = 0
    for (n in 1..10000){
        if ((transform(n) < limit) && (n > max)){
            max = n
        }
    }
    return max
}
