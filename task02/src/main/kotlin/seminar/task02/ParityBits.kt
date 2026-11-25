package seminar.task02

fun transform(n: Int): Int {
    var r = n.toString(2)
    for(i in 1..2){
        var ones = 0
        for(ch in r){
            if (ch == '1') ones++
        }
        r += (ones%2).toString()
    }
    return r.toInt(2)
}

fun minNAbove(limit: Int): Int {
    for (n in 1..10000){
        if(transform(n) > limit){
            return n
        }
    }
    return 0
}
