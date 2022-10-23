package com.example.chatapp.componentsUi

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class BubbleView(context: Context, attrs: AttributeSet?): View(context, attrs) {
    private val paint: Paint = Paint()
    private val path: Path = Path()
    private val rect: Rect = Rect()
    private var size = 500;

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        arrowBubble(canvas)
        //drawingBox(canvas)
    }

    private fun drawingBox(canvas: Canvas?) {

        //canvas?.drawR

        val recta = RectF(size * 0.32f, size * 0.23f, size * 0.50f, size * 0.20f)

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE

        canvas?.drawRect(recta ,paint)
    }


    private fun arrowBubble(canvas: Canvas?) {


        //path.moveTo(size * 0.22f, size * 0.7f)
        path.moveTo(100f, 100f)
        path.quadTo(110f, 110f, 120f, 100f)

        path.moveTo(100f, 100f)
        path.quadTo(110f, 120f, 120f, 110f)

        path.moveTo(120f, 100f)
        path.quadTo(120f, 40f, 160f, 40f)


        path.moveTo(160f, 40f)
        path.quadTo(120f, 40f, 380f, 40f)

        path.moveTo(410f, 110f)
        path.quadTo(420f, 50f, 380f, 40f)
        path.quadTo(400f, 50f, 410f, 120f)

        path.moveTo(120f, 110f)
        path.quadTo(110f, 130f, 380f, 130f)




        //path.quadTo(120f, 60f, 160f, 40f)
        //path.quadTo((size * 0.50f) * 0.50f, size * 0.75f, size * 0.35f, size * 0.70f)
        //path.quadTo((size * 0.50f) * 0.50f, size * 0.25f, size * 0.35f, size * 0.75f)

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE

        canvas?.drawPath(path, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        size = Math.min(measuredWidth, measuredHeight)

        setMeasuredDimension(size, size)

    }

}