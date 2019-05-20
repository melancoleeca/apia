package at.chl.apia

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Sizes

object GameConfig {


    // game
    const val DUNGEON_LEVELS = 1

    // look & feel
    val TILESET = CP437TilesetResources.bisasam16x16()
    val THEME = ColorThemes.monokaiBlue()

    // sizing
    const val WINDOW_WIDTH = 80
    const val WINDOW_HEIGHT = 50

    const val SIDEBAR_WIDTH = 18
    const val CHARINFO_HEIGHT = 20

    const val LOG_AREA_HEIGHT = 8

    const val GAMEAREA_WIDTH = WINDOW_WIDTH - SIDEBAR_WIDTH
    const val GAMEAREA_HEIGTH = WINDOW_HEIGHT - LOG_AREA_HEIGHT

    val WORLD_SIZE = Sizes.create3DSize(GAMEAREA_WIDTH, GAMEAREA_HEIGTH, DUNGEON_LEVELS)

    const val PATH_WIDTH_MIN = 5
    const val PATH_WIDTH_MAX = 16
    const val PATH_SMOOTHING = 2

    // POPULATION
    const val BASE_POPULATION = 5
    const val POPULATION_LIMIT = 25

    // entities
    const val FUNGI_PER_LEVEL = 15
    const val MAXIMUM_FUNGUS_SPREAD = 5

    fun buildAppConfig() = AppConfigs.newConfig()
            .enableBetaFeatures()
            .withDefaultTileset(TILESET)
            .withSize(Sizes.create(WINDOW_WIDTH, WINDOW_HEIGHT))
            .build()

}
