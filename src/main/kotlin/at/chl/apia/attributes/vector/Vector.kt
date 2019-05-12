package at.chl.apia.attributes.vector

import org.hexworks.zircon.api.data.impl.Position3D

class Vector(val x: Double = 0.0, val y: Double = 0.0) {
    companion object {

        fun create (start: Position3D,target: Position3D) : Vector{
            val vector3D = start.minus(target)
            return Vector(vector3D.x.toDouble(), vector3D.y.toDouble())
        }
    }

    fun normalize() : Vector{
        var yFactor = this.y
        if(this.y < 0){
            yFactor *= -1
        }

        if(0.0 == yFactor){
            yFactor = 1.0
        }
        return Vector(this.x / yFactor, this.y / yFactor);
    }

    override fun toString(): String {
        return "Vector(x=$x, y=$y)"
    }

}