package com.balolam.simpleprogressring

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View


class SimpleProgressRing : View {

    private object Default {
        val WIDTH_DPI = 10f
        val PROGRESS = 0
        val MAX_PROGRESS = 100
        val START_ANGLE = -90f

        object colors {
            val LINE_PROGRESS = Color.RED
            val LINE_BACKGROUND = Color.BLACK
        }
    }

    private var isInitiated = false

    private var lineWidth: Float = 0f

    private var progress = Default.PROGRESS
    private var maxProgress = Default.MAX_PROGRESS

    private var lineBackgroundColor = Default.colors.LINE_BACKGROUND
    private var lineProgressColor = Default.colors.LINE_PROGRESS

    private val lineBackgroundPaint: Paint
    private val lineProgressPaint: Paint

    init {
        lineBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        lineProgressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    constructor(context: Context) : super(context) {
        if (isInitiated) {
            return
        }

        isInitiated = true

        lineWidth = ScreenUtils.dp2px(context, Default.WIDTH_DPI)

        initComponents()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        if (isInitiated) {
            return
        }

        isInitiated = true

        val ta = context.resources.obtainAttributes(attrs, R.styleable.SimpleProgressRing)

        try {
            lineWidth = ta.getDimension(R.styleable.SimpleProgressRing_simple_progress_ring_line_width, ScreenUtils.dp2px(context, Default.WIDTH_DPI))

            progress = ta.getInteger(R.styleable.SimpleProgressRing_simple_progress_ring_progress, Default.PROGRESS)
            maxProgress = ta.getInteger(R.styleable.SimpleProgressRing_simple_progress_ring_max_progress, Default.MAX_PROGRESS)

            lineBackgroundColor = ta.getColor(R.styleable.SimpleProgressRing_simple_progress_ring_line_background_color, Default.colors.LINE_BACKGROUND)
            lineProgressColor = ta.getColor(R.styleable.SimpleProgressRing_simple_progress_ring_line_progress_color, Default.colors.LINE_PROGRESS)
        } catch (thr: Throwable) {
            thr.printStackTrace()
            ta.recycle()
        }

        initComponents()
    }

    private fun initComponents() {
        lineBackgroundPaint.color = lineBackgroundColor
        lineBackgroundPaint.strokeWidth = lineWidth
        lineBackgroundPaint.style = Paint.Style.STROKE

        lineProgressPaint.color = lineProgressColor
        lineProgressPaint.strokeWidth = lineWidth
        lineProgressPaint.style = Paint.Style.STROKE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }

    private val backgroundOval = RectF()
    private var progressOval = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = (width - (paddingLeft + paddingRight) - lineWidth) / 2f
        val center_x = width.toFloat() / 2f

        backgroundOval.left = center_x - radius
        backgroundOval.top = center_x - radius
        backgroundOval.right = center_x + radius
        backgroundOval.bottom = center_x + radius

        canvas.drawArc(backgroundOval, 0f, 360f, false, lineBackgroundPaint)

        progressOval.left = center_x - radius
        progressOval.top = center_x - radius
        progressOval.right = center_x + radius
        progressOval.bottom = center_x + radius

        val progress_angle = (progress.toFloat() / maxProgress.toFloat()) * 360f

        canvas.drawArc(progressOval, Default.START_ANGLE, progress_angle, false, lineProgressPaint)
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    fun setMaxProgress(maxProgress: Int) {
        this.maxProgress = maxProgress
        invalidate()
    }
}
