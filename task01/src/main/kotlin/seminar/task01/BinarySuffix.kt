package seminar.task01

fun transform(n: Int): Int {
    var s = n.toString(radix = 2)
    if (n % 2 == 0) {
        s += "00"
    } else {
        s += "11"
    }
    return s.toInt(radix = 2)
}

fun maxNBelow(limit: Int): Int {
    var answer = 0
    for (i in 1..10000){
        if (transform(i) < limit){
            answer = i
        }
    }
    return answer
}

