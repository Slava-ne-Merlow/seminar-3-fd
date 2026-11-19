package seminar.task01

fun transform(n: Int): Int {
    TODO("Преобразуйте двоичную запись числа")
    var s = n.toString(2)

    if(n%2==0){
        s+="00"
    } else {
        s+="11"
    }

    return s.toInt(2)
}

fun maxNBelow(limit: Int): Int {
    TODO("Найдите максимальное N, для которого R < limit")
    var a=0
    for(i in 1..10000){
        if(transform(i)<limit){
            a=i
        }
    }
    return a
}

//fun main(){
//    val lim =readln().toInt()
//    val n=readln().toInt()
//    println(maxNBelow(lim,n))
//}