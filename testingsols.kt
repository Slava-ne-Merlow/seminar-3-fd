import kotlin.text.toString

fun transform(n: Int): Int {
    var binN : String = n.toString(2)
    var s: Int = 0
    for(i in binN) {
        s += i.digitToInt()
    }
    binN += (s % 2).toString()
    s = 0
    for(i in binN) {
        s += i.digitToInt()
    }
    binN += (s % 2).toString()
    return binN.toInt(2)
}
fun main() {
    println(transform(131))
}