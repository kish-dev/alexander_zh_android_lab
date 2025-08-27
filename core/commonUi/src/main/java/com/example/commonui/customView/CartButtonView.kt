package com.example.commonui.customView

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.core.graphics.drawable.DrawableCompat
import com.example.commonui.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

open class CartButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    enum class Mode {
        SIMPLE,
        EXPANDABLE
    }

    enum class State {
        ADD_TO_CART,
        QUANTITY_CONTROL
    }

    private val blueColor = Color.parseColor("#1976D2")
    private val darkBlueColor = Color.parseColor("#1565C0")
    private val lightGreenColor = Color.parseColor("#4CAF50")
    private val whiteColor = Color.WHITE

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val checkMarkPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val plusMinusPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val cornerRadius = 8.dpToPx()
    private val checkMarkRadius = 12.dpToPx()
    private val padding = 8.dpToPx()

    private var iconDrawable: Drawable? = null
    private var iconSize = 16.dpToPx().toInt()
    private var iconPadding = 4.dpToPx().toInt()
    private var iconTint: Int? = null
    private val iconRect = Rect()

    private var mode = Mode.SIMPLE
    private var currentState = State.ADD_TO_CART
    private var quantity = 1
    private var inCartCount = 0
    private var animationProgress = 0f

    private var cartChangeListener: ((Int) -> Unit)? = null
    var onFirstAddToCartListener: (() -> Unit)? = null
    var onAddToCartFromQuantityControlListener: (() -> Unit)? = null

    private var longPressJob: Job? = null
    private var isPlusPressed = false
    private var isMinusPressed = false
    private var isAddToCartPressed = false

    private val badgePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
    }
    private val badgeTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 10.spToPx()
        textAlign = Paint.Align.CENTER
    }
    private var badgeCount = 0

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CartButtonView, defStyleAttr, 0)
        try {
            val icon = ta.getDrawable(R.styleable.CartButtonView_iconSrc)
            icon?.let { setIcon(it) }

            iconSize = ta.getDimensionPixelSize(
                R.styleable.CartButtonView_iconSize,
                iconSize
            )

            iconPadding = ta.getDimensionPixelSize(
                R.styleable.CartButtonView_iconPadding,
                iconPadding
            )

            if (ta.hasValue(R.styleable.CartButtonView_iconTint)) {
                setIconTint(ta.getColor(R.styleable.CartButtonView_iconTint, Color.WHITE))
            }

            mode = Mode.values()[
                ta.getInt(R.styleable.CartButtonView_mode, Mode.SIMPLE.ordinal)
            ]
        } finally {
            ta.recycle()
        }

        setupPaints()
        setupClickListeners()
        setWillNotDraw(false)
    }

    private fun setupPaints() {
        bgPaint.style = Paint.Style.FILL
        textPaint.color = whiteColor
        textPaint.textSize = 14.spToPx()
        textPaint.textAlign = Paint.Align.CENTER
        checkMarkPaint.color = lightGreenColor
        plusMinusPaint.color = whiteColor
        plusMinusPaint.strokeWidth = 2.dpToPx()
        plusMinusPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when {
            mode == Mode.SIMPLE -> drawSimpleCartButton(canvas)
            currentState == State.ADD_TO_CART -> drawAddToCartState(canvas)
            else -> {
                drawAddToCartButtonLeft(canvas)
                drawQuantityControlState(canvas)
            }
        }
        drawBadge(canvas)
    }

    private fun drawAddToCartButtonLeft(canvas: Canvas) {
        val buttonWidth = width * 0.6f
        val buttonRect = RectF(
            0f,
            0f,
            buttonWidth,
            height.toFloat()
        )

        bgPaint.color = blueColor
        canvas.drawRoundRect(buttonRect, cornerRadius, cornerRadius, bgPaint)

        val text = "В корзину"
        val textY = buttonRect.centerY() - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(text, buttonRect.centerX(), textY, textPaint)

        iconDrawable?.let { icon ->
            val textWidth = textPaint.measureText(text)
            val totalWidth = textWidth + iconSize + iconPadding
            val startX = buttonRect.centerX() - totalWidth / 2

            val iconTop = (height - iconSize) / 2
            iconRect.set(
                startX.toInt(),
                iconTop,
                startX.toInt() + iconSize,
                iconTop + iconSize
            )
            icon.bounds = iconRect
            icon.draw(canvas)

            val textX = startX + iconSize + iconPadding + textWidth / 2
            canvas.drawText(text, textX, textY, textPaint)
        }
    }

    private fun drawSimpleCartButton(canvas: Canvas) {
        bgPaint.color = blueColor
        canvas.drawRoundRect(
            0f, 0f, width.toFloat(), height.toFloat(),
            cornerRadius, cornerRadius, bgPaint
        )

        val text = "В корзину"
        drawIconWithText(canvas, text)

        if (inCartCount > 0) {
            drawCheckMark(canvas)
        }
    }

    private fun drawAddToCartState(canvas: Canvas) {
        bgPaint.color = blueColor
        canvas.drawRoundRect(
            0f, 0f, width.toFloat(), height.toFloat(),
            cornerRadius, cornerRadius, bgPaint
        )

        val text = "В корзину"
        drawIconWithText(canvas, text)

        if (inCartCount > 0) {
            drawCheckMark(canvas)
        }
    }

    private fun drawQuantityControlState(canvas: Canvas) {
        val startX = width * 0.6f

        bgPaint.color = darkBlueColor
        bgPaint.alpha = 200
        canvas.drawRoundRect(
            startX, 0f, width.toFloat(), height.toFloat(),
            cornerRadius, cornerRadius, bgPaint
        )

        val minusRect = RectF(
            startX + padding,
            height / 2f - 8.dpToPx(),
            startX + padding + 16.dpToPx(),
            height / 2f + 8.dpToPx()
        )
        canvas.drawRoundRect(minusRect, 4.dpToPx(), 4.dpToPx(), plusMinusPaint)
        canvas.drawLine(
            minusRect.left + 4.dpToPx(), height / 2f,
            minusRect.right - 4.dpToPx(), height / 2f,
            plusMinusPaint
        )

        val plusRect = RectF(
            width - padding - 16.dpToPx(),
            height / 2f - 8.dpToPx(),
            width - padding,
            height / 2f + 8.dpToPx()
        )
        canvas.drawRoundRect(plusRect, 4.dpToPx(), 4.dpToPx(), plusMinusPaint)
        canvas.drawLine(
            plusRect.left + 4.dpToPx(), height / 2f,
            plusRect.right - 4.dpToPx(), height / 2f,
            plusMinusPaint
        )
        canvas.drawLine(
            width - padding - 8.dpToPx(), height / 2f - 8.dpToPx() + 4.dpToPx(),
            width - padding - 8.dpToPx(), height / 2f + 8.dpToPx() - 4.dpToPx(),
            plusMinusPaint
        )

        val quantityText = quantity.toString()
        val textY = height / 2 - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(quantityText, startX + (width - startX) / 2, textY, textPaint)
    }

    private fun drawIconWithText(canvas: Canvas, text: String) {
        iconDrawable?.let { icon ->
            val textWidth = textPaint.measureText(text)
            val totalWidth = textWidth + iconSize + iconPadding
            val startX = (width - totalWidth) / 2

            val iconTop = (height - iconSize) / 2
            iconRect.set(
                startX.toInt(),
                iconTop,
                startX.toInt() + iconSize,
                iconTop + iconSize
            )
            icon.bounds = iconRect
            icon.draw(canvas)

            val textY = height / 2 - (textPaint.descent() + textPaint.ascent()) / 2
            val textX = startX + iconSize + iconPadding + textWidth / 2
            canvas.drawText(text, textX, textY, textPaint)
        } ?: run {
            val textY = height / 2 - (textPaint.descent() + textPaint.ascent()) / 2
            canvas.drawText(text, width / 2f, textY, textPaint)
        }
    }

    private fun drawBadge(canvas: Canvas) {
        if (badgeCount <= 0) return

        val badgeRadius = 8.dpToPx()
        val badgeX = width - badgeRadius - 2.dpToPx()
        val badgeY = badgeRadius + 2.dpToPx()

        canvas.drawCircle(badgeX, badgeY, badgeRadius, badgePaint)

        val text = if (badgeCount > 9) "9+" else badgeCount.toString()
        val textY = badgeY - (badgeTextPaint.descent() + badgeTextPaint.ascent()) / 2
        canvas.drawText(text, badgeX, textY, badgeTextPaint)
    }

    private fun drawCheckMark(canvas: Canvas) {
        val circleX = width - checkMarkRadius
        val circleY = height - checkMarkRadius

        canvas.drawCircle(circleX, circleY, checkMarkRadius, checkMarkPaint)
        val checkMarkPath = Path().apply {
            moveTo(circleX - checkMarkRadius / 2, circleY)
            lineTo(circleX - checkMarkRadius / 4, circleY + checkMarkRadius / 3)
            lineTo(circleX + checkMarkRadius / 2, circleY - checkMarkRadius / 3)
        }
        plusMinusPaint.style = Paint.Style.STROKE
        canvas.drawPath(checkMarkPath, plusMinusPaint)
    }

    private fun setupClickListeners() {
        setOnClickListener {
            when (mode) {
                Mode.SIMPLE -> {
                    if (inCartCount == 0) {
                        onFirstAddToCartListener?.invoke()
                    }
                }
                Mode.EXPANDABLE -> {
                    if (currentState == State.ADD_TO_CART) {
                        if (inCartCount == 0) {
                            onFirstAddToCartListener?.invoke()
                        }
                        animateStateChange(State.QUANTITY_CONTROL)
                        updateCartCount()
                    }
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mode == Mode.EXPANDABLE && currentState == State.QUANTITY_CONTROL) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val leftButtonWidth = width * 0.6f

                    if (event.x < leftButtonWidth) {
                        isAddToCartPressed = true
                        return true
                    } else {
                        if (isInMinusArea(event)) {
                            isMinusPressed = true
                            decreaseQuantity()
                            startContinuousChange(false)
                            return true
                        } else if (isInPlusArea(event)) {
                            isPlusPressed = true
                            increaseQuantity()
                            startContinuousChange(true)
                            return true
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (isAddToCartPressed) {
                        onAddToCartFromQuantityControlListener?.invoke()
                    }
                    isPlusPressed = false
                    isMinusPressed = false
                    isAddToCartPressed = false
                    longPressJob?.cancel()
                    return true
                }
                MotionEvent.ACTION_CANCEL -> {
                    isPlusPressed = false
                    isMinusPressed = false
                    isAddToCartPressed = false
                    longPressJob?.cancel()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun isInMinusArea(event: MotionEvent): Boolean {
        val leftButtonWidth = width * 0.6f
        val minusRect = RectF(
            leftButtonWidth + padding, 0f,
            leftButtonWidth + padding + 32.dpToPx(), height.toFloat()
        )
        return minusRect.contains(event.x, event.y)
    }

    private fun isInPlusArea(event: MotionEvent): Boolean {
        val plusRect = RectF(
            width - padding - 32.dpToPx(), 0f,
            width - padding.toFloat(), height.toFloat()
        )
        return plusRect.contains(event.x, event.y)
    }

    private fun increaseQuantity() {
        quantity++
        updateCartCount()
        invalidate()
    }

    private fun decreaseQuantity() {
        if (quantity > 1) {
            quantity--
            updateCartCount()
            invalidate()
        } else {
            quantity = 0
            inCartCount = 0
            updateCartCount()
            animateStateChange(State.ADD_TO_CART)
        }
    }

    private fun startContinuousChange(isIncrease: Boolean) {
        longPressJob?.cancel()
        longPressJob = CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            while (isActive && (if (isIncrease) isPlusPressed else isMinusPressed)) {
                if (isIncrease) {
                    increaseQuantity()
                } else {
                    decreaseQuantity()
                }
                delay(100)
        }
        }
    }

    fun animateStateChange(newState: State) {
        if (mode == Mode.SIMPLE) return

        val anim = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 200
            addUpdateListener {
                animationProgress = it.animatedValue as Float
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    currentState = newState
                    animationProgress = 0f
                    invalidate()
                }
            })
        }
        anim.start()
    }

    private fun updateCartCount() {
        inCartCount = if (quantity > 0) quantity else 0
        cartChangeListener?.invoke(quantity)
        invalidate()
    }

    fun setMode(newMode: Mode) {
        mode = newMode
        invalidate()
    }

    fun setInCartCount(count: Int) {
        inCartCount = count
        if (count > 0) {
            quantity = count
            if (mode == Mode.SIMPLE) {
                currentState = State.ADD_TO_CART
            }
        }
        invalidate()
    }

    fun setOnCartChangeListener(listener: (Int) -> Unit) {
        cartChangeListener = listener
    }

    fun setBadgeCount(count: Int) {
        badgeCount = count
        invalidate()
    }

    fun setIcon(drawable: Drawable?) {
        iconDrawable = drawable?.mutate()
        iconTint?.let { tint ->
            DrawableCompat.setTint(iconDrawable!!, tint)
        }
        iconDrawable?.setBounds(0, 0, iconSize, iconSize)
        invalidate()
    }

    fun setIconTint(color: Int) {
        iconTint = color
        iconDrawable?.let {
            DrawableCompat.setTint(it, color)
        }
        invalidate()
    }

    private fun Int.dpToPx(): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics)

    private fun Int.spToPx(): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this.toFloat(), resources.displayMetrics)

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return CartButtonState(
            quantity = quantity,
            inCartCount = inCartCount,
            currentState = currentState,
            mode = mode,
            superState = superState
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is CartButtonState) {
            super.onRestoreInstanceState(state.superState)
            quantity = state.quantity
            inCartCount = state.inCartCount
            currentState = state.currentState
            mode = state.mode
        } else {
            super.onRestoreInstanceState(state)
        }
        invalidate()
    }

    class CartButtonState : BaseSavedState {
        val quantity: Int
        val inCartCount: Int
        val currentState: State
        val mode: Mode

        constructor(
            quantity: Int,
            inCartCount: Int,
            currentState: State,
            mode: Mode,
            superState: Parcelable?
        ) : super(superState) {
            this.quantity = quantity
            this.inCartCount = inCartCount
            this.currentState = currentState
            this.mode = mode
        }

        constructor(parcel: Parcel) : super(parcel) {
            quantity = parcel.readInt()
            inCartCount = parcel.readInt()
            currentState = State.values()[parcel.readInt()]
            mode = Mode.values()[parcel.readInt()]
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
            parcel.writeInt(quantity)
            parcel.writeInt(inCartCount)
            parcel.writeInt(currentState.ordinal)
            parcel.writeInt(mode.ordinal)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<CartButtonState> {
                override fun createFromParcel(parcel: Parcel) = CartButtonState(parcel)
                override fun newArray(size: Int) = arrayOfNulls<CartButtonState>(size)
            }
        }
    }
}