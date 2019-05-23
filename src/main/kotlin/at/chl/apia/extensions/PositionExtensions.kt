package at.chl.apia.extensions

import org.hexworks.zircon.api.data.impl.Position3D

fun Position3D.sameLevelNeighborsShuffled(): List<Position3D> {
    return (-1..1).flatMap { x ->
        (-1..1).map { y ->
            withRelativeX(x).withRelativeY(y)
        }
    }.minus(this).shuffled()
}
fun Position3D.isInReach(source: Position3D, range: Int): Boolean {

    if((source.x==this.x && ((source.y >= this.y - range) && (source.y <=  this.y + range)))
        ||
        (source.y==this.y && ((source.x >= this.x - range) && (source.x <=  this.x + range)))){
        return true
    }

    return false
}
