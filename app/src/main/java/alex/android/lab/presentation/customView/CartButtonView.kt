package alex.android.lab.presentation.customView

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class CartButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Состояния кнопки
    enum class State {
        ADD_TO_CART, // Состояние 1 - "В корзину"
        QUANTITY_CONTROL // Состояние 2 - с +/-
    }

    private var currentState = State.ADD_TO_CART
    private var quantity = 1
    private var inCartCount = 0 // Будем получать из БД

    // Paint объекты для рисования
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val checkMarkPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val plusMinusPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Цвета
    private val blueColor = Color.parseColor("#1976D2")
    private val darkBlueColor = Color.parseColor("#1565C0")
    private val lightGreenColor = Color.parseColor("#4CAF50")
    private val whiteColor = Color.WHITE

    // Размеры и отступы
    private val cornerRadius = 8.dpToPx()
    private val checkMarkRadius = 12.dpToPx()
    private val padding = 8.dpToPx()

    // Анимации
    private var animationProgress = 0f
    private val animator = ValueAnimator()

    init {
        setupPaints()
        setupClickListeners()
    }

    private fun setupPaints() {
        // Настройка Paint объектов
        bgPaint.style = Paint.Style.FILL
        textPaint.color = whiteColor
        textPaint.textSize = 14.spToPx()
        textPaint.textAlign = Paint.Align.CENTER
        checkMarkPaint.color = lightGreenColor
        plusMinusPaint.color = whiteColor
        plusMinusPaint.strokeWidth = 2.dpToPx()
        plusMinusPaint.style = Paint.Style.STROKE
    }

    // Конвертеры dp/sp в пиксели
    private fun Int.dpToPx(): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics)

    private fun Int.spToPx(): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this.toFloat(), resources.displayMetrics)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when (currentState) {
            State.ADD_TO_CART -> drawAddToCartState(canvas)
            State.QUANTITY_CONTROL -> drawQuantityControlState(canvas)
        }
    }

    private fun drawAddToCartState(canvas: Canvas) {
        // Рисуем синий фон
        bgPaint.color = blueColor
        canvas.drawRoundRect(
            0f, 0f, width.toFloat(), height.toFloat(),
            cornerRadius, cornerRadius, bgPaint
        )

        // Рисуем текст "В корзину"
        val textY = height / 2 - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText("В корзину", width / 2f, textY, textPaint)

        // Если товар уже в корзине, рисуем зеленый кружок с галочкой
        if (inCartCount > 0) {
            val circleX = width - checkMarkRadius
            val circleY = height - checkMarkRadius

            // Рисуем зеленый круг
            canvas.drawCircle(circleX, circleY, checkMarkRadius, checkMarkPaint)

            // Рисуем галочку
            val checkMarkPath = android.graphics.Path().apply {
                moveTo(circleX - checkMarkRadius / 2, circleY)
                lineTo(circleX - checkMarkRadius / 4, circleY + checkMarkRadius / 3)
                lineTo(circleX + checkMarkRadius / 2, circleY - checkMarkRadius / 3)
            }
            plusMinusPaint.style = Paint.Style.STROKE
            canvas.drawPath(checkMarkPath, plusMinusPaint)
        }
    }

    private fun drawQuantityControlState(canvas: Canvas) {
        // Рисуем темно-синий полупрозрачный фон
        bgPaint.color = darkBlueColor
        bgPaint.alpha = 200
        canvas.drawRoundRect(
            0f, 0f, width.toFloat(), height.toFloat(),
            cornerRadius, cornerRadius, bgPaint
        )

        // Рисуем минус слева
        val minusRect = RectF(
            padding, height / 2f - 8.dpToPx(),
            padding + 16.dpToPx(), height / 2f + 8.dpToPx()
        )
        canvas.drawRoundRect(minusRect, 4.dpToPx(), 4.dpToPx(), plusMinusPaint)
        canvas.drawLine(
            minusRect.left + 4.dpToPx(), height / 2f,
            minusRect.right - 4.dpToPx(), height / 2f,
            plusMinusPaint
        )

        // Рисуем плюс справа
        val plusRect = RectF(
            width - padding - 16.dpToPx(), height / 2f - 8.dpToPx(),
            width - padding, height / 2f + 8.dpToPx()
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

        // Рисуем количество
        val quantityText = quantity.toString()
        val textY = height / 2 - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(quantityText, width / 2f, textY, textPaint)
    }
    private fun setupClickListeners() {
        setOnClickListener {
            if (currentState == State.ADD_TO_CART) {
                // Переход в состояние с количеством
                animateStateChange(State.QUANTITY_CONTROL)
                // Переход на экран корзины (если нужно)
                // context.startActivity(Intent(context, CartActivity::class.java))
            }
        }

        // Обработка долгого нажатия для быстрого изменения количества
//        setOnLongClickListener { v ->
//            when {
//                isInMinusArea(v, MotionEvent.ACTION_DOWN) -> {
//                    startContinuousDecrease()
//                    true
//                }
//                isInPlusArea(v, MotionEvent.ACTION_DOWN) -> {
//                    startContinuousIncrease()
//                    true
//                }
//                else -> false
//            }
//        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                animator.cancel() // Отменяем долгое нажатие

                if (currentState == State.QUANTITY_CONTROL) {
                    when {
                        isInMinusArea(this, event) -> decreaseQuantity()
                        isInPlusArea(this, event) -> increaseQuantity()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun isInMinusArea(view: View, event: MotionEvent): Boolean {
        val minusRect = RectF(
            padding, 0f,
            padding + 32.dpToPx(), height.toFloat()
        )
        return minusRect.contains(event.x, event.y)
    }

    private fun isInPlusArea(view: View, event: MotionEvent): Boolean {
        val plusRect = RectF(
            width - padding - 32.dpToPx(), 0f,
            width - padding.toFloat(), height.toFloat()
        )
        return plusRect.contains(event.x, event.y)
    }

    private fun increaseQuantity() {
        quantity++
        updateCartCountInDB(quantity)
        invalidate()
    }

    private fun decreaseQuantity() {
        if (quantity > 1) {
            quantity--
            updateCartCountInDB(quantity)
            invalidate()
        } else {
            // Возвращаемся в состояние "В корзину"
            animateStateChange(State.ADD_TO_CART)
        }
    }

    private fun startContinuousIncrease() {
        animator.cancel()
        animator.setIntValues(quantity, quantity + 10)
        animator.duration = 1000
        animator.addUpdateListener {
            quantity = it.animatedValue as Int
            updateCartCountInDB(quantity)
            invalidate()
        }
        animator.start()
    }

    private fun startContinuousDecrease() {
        animator.cancel()
        val target = if (quantity > 5) quantity - 5 else 1
        animator.setIntValues(quantity, target)
        animator.duration = 1000
        animator.addUpdateListener {
            quantity = it.animatedValue as Int
            updateCartCountInDB(quantity)
            invalidate()

            if (quantity == 1) {
                animator.cancel()
                animateStateChange(State.ADD_TO_CART)
            }
        }
        animator.start()
    }

    private fun animateStateChange(newState: State) {
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

    fun setInCartCount(count: Int) {
        inCartCount = count
        if (count > 0 && currentState == State.ADD_TO_CART) {
            quantity = count
        }
        invalidate()
    }

    fun setOnCartChangeListener(listener: (Int) -> Unit) {
        this.cartChangeListener = listener
    }

    private var cartChangeListener: ((Int) -> Unit)? = null

    private fun updateCartCountInDB(count: Int) {
        inCartCount = count
        cartChangeListener?.invoke(count)
        // Здесь также можно вызвать обновление в ViewModel
    }

}
