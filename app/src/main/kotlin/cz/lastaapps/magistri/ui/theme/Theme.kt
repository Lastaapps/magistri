package cz.lastaapps.magistri.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.color.DynamicColors

private val LightThemeColors = lightColorScheme(

    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
)
private val DarkThemeColors = darkColorScheme(

    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useCustomTheme: Boolean = true,
    content: @Composable() () -> Unit
) {
    val dynamicColor = isDynamicThemeSupported() && !useCustomTheme
    val colorScheme = when {
        dynamicColor && useDarkTheme -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColor && !useDarkTheme -> dynamicLightColorScheme(LocalContext.current)
        useDarkTheme -> DarkThemeColors
        else -> LightThemeColors
    }.animated()

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = colorScheme.background,
            darkIcons = !useDarkTheme,
        )
        systemUiController.setNavigationBarColor(
            color = colorScheme.surfaceVariant,
            darkIcons = !useDarkTheme,
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = Shapes,
        content = content,
    )
}

fun isDynamicThemeSupported() = DynamicColors.isDynamicColorAvailable()

private val Shapes = Shapes(
    /*extraSmall = ShapeTokens.CornerExtraSmall
    small = ShapeTokens.CornerSmall,
    medium = ShapeTokens.CornerMedium,
    large = ShapeTokens.CornerLarge,
    extraLarge = ShapeTokens.CornerExtraLarge,*/
)

@Composable
private fun ColorScheme.animated(): ColorScheme {
    return ColorScheme(
        background = animateColorAsState(background).value,
        error = animateColorAsState(error).value,
        errorContainer = animateColorAsState(errorContainer).value,
        inverseOnSurface = animateColorAsState(inverseOnSurface).value,
        inversePrimary = animateColorAsState(inversePrimary).value,
        inverseSurface = animateColorAsState(inverseSurface).value,
        onBackground = animateColorAsState(onBackground).value,
        onError = animateColorAsState(onError).value,
        onErrorContainer = animateColorAsState(onErrorContainer).value,
        onPrimary = animateColorAsState(onPrimary).value,
        onPrimaryContainer = animateColorAsState(onPrimaryContainer).value,
        onSecondary = animateColorAsState(onSecondary).value,
        onSecondaryContainer = animateColorAsState(onSecondaryContainer).value,
        onSurface = animateColorAsState(onSurface).value,
        onSurfaceVariant = animateColorAsState(onSurfaceVariant).value,
        onTertiary = animateColorAsState(onTertiary).value,
        onTertiaryContainer = animateColorAsState(onTertiaryContainer).value,
        outline = animateColorAsState(outline).value,
        primary = animateColorAsState(primary).value,
        primaryContainer = animateColorAsState(primaryContainer).value,
        secondary = animateColorAsState(secondary).value,
        secondaryContainer = animateColorAsState(secondaryContainer).value,
        surface = animateColorAsState(surface).value,
        surfaceVariant = animateColorAsState(surfaceVariant).value,
        surfaceTint = animateColorAsState(surfaceTint).value,
        tertiary = animateColorAsState(tertiary).value,
        tertiaryContainer = animateColorAsState(tertiaryContainer).value,
    )
}

//data class CustomColor(val name:String, val color: Color, val harmonized: Boolean, var roles:ColorRoles)
//data class ExtendedColors(val colors: Array<CustomColor>)
//
//
//fun setupErrorColors(colorScheme: ColorScheme, isLight: Boolean): ColorScheme {
//    val harmonizedError =
//        MaterialColors.harmonize(error, colorScheme.primary)
//    val roles = MaterialColors.getColorRoles(harmonizedError, isLight)
//    //returns a colorScheme with newly harmonized error colors
//    return colorScheme.copy(
//        error = roles.color,
//        onError = roles.onColor,
//        errorContainer = roles.colorContainer,
//        onErrorContainer = roles.onColorContainer
//    )
//}
//val initializeExtended = ExtendedColors(
//	arrayOf(
//))
//
//fun setupCustomColors(
//    colorScheme: ColorScheme,
//    isLight: Boolean
//): ExtendedColors {
//    initializeExtended.colors.forEach { customColor ->
//        // Retrieve record
//        val shouldHarmonize = customColor.harmonized
//        // Blend or not
//        if (shouldHarmonize) {
//            val blendedColor =
//                MaterialColors.harmonize(customColor.color, colorScheme.primary)
//            customColor.roles = MaterialColors.getColorRoles(blendedColor, isLight)
//        } else {
//            customColor.roles = MaterialColors.getColorRoles(customColor.color, isLight)
//        }
//    }
//    return initializeExtended
//}
//
//val LocalExtendedColors = staticCompositionLocalOf {
//    initializeExtended
//}
//
//
//@RequiresApi(Build.VERSION_CODES.S)
//@Composable
//fun HarmonizedTheme(
//    useDarkTheme: Boolean = isSystemInDarkTheme(),
//    isDynamic: Boolean = true,
//    content: @Composable() () -> Unit
//) {
//    val colors = if (isDynamic) {
//        val context = LocalContext.current
//        if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//    } else {
//        if (useDarkTheme) DarkThemeColors else LightThemeColors
//    }
//    val colorsWithHarmonizedError = if(errorHarmonize) setupErrorColors(colors, !useDarkTheme) else colors
//
//    val extendedColors = setupCustomColors(colors, !useDarkTheme)
//    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
//        MaterialTheme(
//            colorScheme = colorsWithHarmonizedError,
//            typography = AppTypography,
//            content = content
//        )
//    }
//}
//
//object Extended {
//}