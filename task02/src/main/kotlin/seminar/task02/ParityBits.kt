package seminar.task02

fun transform(n: Int): Int {
    var k = n.toString(2)
    var cnt = 0
    for (i in k.length - 1 downTo 0) {
        if (k[i] == '1') ++cnt
    }
    if (cnt % 2 == 0) k += "00"
    else k += "10"
    return k.toInt(2)
}

fun minNAbove(limit: Int): Int {
    for (i in 1..10000) {
        if (transform(i) > limit) return i
    }
    return 0
}
