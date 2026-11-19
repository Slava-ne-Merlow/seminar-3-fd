package seminar.task01

fun transform(n: Int): Int {
    var s = n.toString(2)

    if (n % 2 == 0) {
        s += "00"
    } else {
        s += "11"
    }

    return s.toInt(2)
}

fun maxNBelow(limit: Int): Int {
    var answer = 0

    for (i in 1..10_000) {
        if (transform(i) < limit){
            answer = i
        }
    }

    return answer
}
