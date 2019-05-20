package at.chl.apia.random

class Random {

    companion object{
        fun randomInt(range: Int): Int {
            return (Math.random()*range).toInt()
        }

        fun randomInt(from: Int, to:Int) : Int{
            val range = to - from
            return from + (Math.random()*range).toInt()
        }
    }

}