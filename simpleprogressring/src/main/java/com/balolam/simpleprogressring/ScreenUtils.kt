package com.balolam.simpleprogressring

import android.content.Context
import android.util.DisplayMetrics

@Suppress("NOTHING_TO_INLINE")
object ScreenUtils {

    @JvmStatic
    fun px2dp(ctx: Context, px: Int): Float = forDensity(ctx) { px / it }

    @JvmStatic
    fun dp2px(ctx: Context, dp: Int): Float = forDensity(ctx) { dp * it }

    @JvmStatic
    fun px2dp(ctx: Context, px: Float): Float = forDensity(ctx) { px / it }

    @JvmStatic
    fun dp2px(ctx: Context, dp: Float): Float = forDensity(ctx) { dp * it }

    @JvmStatic
    fun px2dp(ctx: Context, px: Double): Float = (forDensity(ctx) { px / it }).toFloat()

    @JvmStatic
    fun dp2px(ctx: Context, dp: Double): Float = (forDensity(ctx) { dp * it }).toFloat()

    @JvmStatic
    fun widthDpi(ctx: Context): Float = forDisplayMetrics(ctx) { it.widthPixels / it.density }

    @JvmStatic
    fun heightDpi(ctx: Context): Float = forDisplayMetrics(ctx) { it.heightPixels / it.density }

    @JvmStatic
    fun widthPx(ctx: Context): Int = forDisplayMetrics(ctx) { it.widthPixels }

    @JvmStatic
    fun heightPx(ctx: Context): Int = forDisplayMetrics(ctx) { it.heightPixels }

    @JvmStatic
    fun widthCount(ctx: Context, dpi: Int): Int {
        val count = Math.ceil((widthDpi(ctx) / dpi).toDouble()).toInt()

        return if (count < 1) 1 else count
    }

    @JvmStatic
    fun heightCount(ctx: Context, dpi: Int): Int {
        val count = Math.ceil((heightDpi(ctx) / dpi).toDouble()).toInt()

        return if (count < 1) 1 else count
    }

    private inline fun displayMetrics(ctx: Context): DisplayMetrics = ctx.resources.displayMetrics

    private inline fun <R> forDisplayMetrics(ctx: Context, action: (DisplayMetrics) -> R): R = action(displayMetrics(ctx))

    private inline fun <R> forDensity(ctx: Context, action: (Float) -> R): R = action(displayMetrics(ctx).density)
}