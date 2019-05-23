package at.chl.apia.view

import org.hexworks.zircon.api.mvc.base.BaseView

class Director {

    var current : BaseView = StartView()

    companion object{
        var default : Director = Director()
    }

    fun start() : BaseView{
        val start = StartView()
//        current.replaceWith(current)
        current = start
        return current
    }

    fun backToStart() : BaseView{
        val start = StartView()
        current.replaceWith(start)
        current = start
        return current
    }

    fun play(): BaseView {
        val play = PlayView()
        current.replaceWith(play)
        current = play
        return current
    }

    fun camp() : BaseView{
        val camp = CampView()
        current.replaceWith(camp)
        current = camp
        return current
    }

    fun gameOver() : BaseView{
        val gameOver = LoseView()
        current.replaceWith(gameOver)
        current = gameOver
        return current
    }
}