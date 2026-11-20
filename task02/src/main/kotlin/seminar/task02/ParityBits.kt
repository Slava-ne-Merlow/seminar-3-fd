package seminar.task02

fun transform(n: Int): Int {
    var s: String = Integer.toBinaryString(n)
    var s1 = 0
    for (c in s) if (c == '1') s1++
    if (s1 % 2 == 0) {
        s += "00"
    }
    else {
        s += "10"
    }
    return Integer.parseInt(s, 2)
}

fun minNAbove(limit: Int): Int {
    for (i in 1..10000) {
        if (transform(i) > limit) return i
    }
    return -1
}
