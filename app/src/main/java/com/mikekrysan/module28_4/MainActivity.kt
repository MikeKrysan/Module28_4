package com.mikekrysan.module28_4

import android.graphics.Rect
import android.os.Bundle
import android.transition.*
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Считываем ширину и высоту самого объекта
        val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 420F, resources.displayMetrics).toInt()
        val height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280F, resources.displayMetrics).toInt()
        //Создаем ректы. Первый отвечает за половину ширини и высоты, второй за целый размер
        val rect1 = Rect(0, 0, width/2, height/2)
        val rect2 =  Rect(0, 0, width, height)
        //Вначале устанавливаем размер как половину ширины и высоты
        img.clipBounds = rect1
        //И при нажатии изменяем рект
        img.setOnClickListener {
            TransitionManager.beginDelayedTransition(root, ChangeClipBounds())
            img.clipBounds = if(img.clipBounds == rect1) rect2 else rect1
        }
    }
}