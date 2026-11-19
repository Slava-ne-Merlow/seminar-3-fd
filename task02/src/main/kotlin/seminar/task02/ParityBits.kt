package seminar.task02

fun transform(n: Int): Int {
    var binN : String = n.toString(2)
    var s: Int = 0
    for(i in binN) {
        s += i.digitToInt()
    }
    binN += (s % 2).toString()
    for(i in binN) {
        s += i.digitToInt()
    }
    binN += (s % 2).toString()
    return binN.toInt(2)
}

fun minNAbove(limit: Int): Int {
    for(i in 0..10000) {
        if(transform(i) > limit) {
            return i
        }
    }
    return -1
}
