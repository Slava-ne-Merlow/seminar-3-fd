package seminar.task01

fun transform(n: Int): Int {
    var s = Integer.toBinaryString(n)
    if (n % 2 == 0) {
        s += "00"
    } else {
        s += "11"
    }
    return Integer.parseInt(s, 2)
}

fun maxNBelow(limit: Int): Int {
    var ans = 0
    for (i in 1..10000) {
        if (transform(i) < limit) {
            ans = i
        }
    }
    return ans
}
