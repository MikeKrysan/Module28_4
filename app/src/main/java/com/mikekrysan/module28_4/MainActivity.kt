package com.mikekrysan.module28_4

import android.graphics.Rect
import android.os.Bundle
import android.transition.*
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Пример с ChangeClipBounds
        //Считываем ширину и высоту самого объекта
//        val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 420F, resources.displayMetrics).toInt()
//        val height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280F, resources.displayMetrics).toInt()
//        //Создаем ректы. Первый отвечает за половину ширини и высоты, второй за целый размер
//        val rect1 = Rect(0, 0, width/2, height/2)
//        val rect2 =  Rect(0, 0, width, height)
//        //Вначале устанавливаем размер как половину ширины и высоты
//        img.clipBounds = rect1
//        //И при нажатии изменяем рект
//        img.setOnClickListener {
//            TransitionManager.beginDelayedTransition(root, ChangeClipBounds())
//            img.clipBounds = if(img.clipBounds == rect1) rect2 else rect1
//        }

        //Пример с ChangeImageTransform
        img.scaleX = 0.5F
        img.scaleY = 0.5F
        //Счетчик который отвечает за текущий вариант scaleType
        var i = 0
        img.setOnClickListener {
            TransitionManager.beginDelayedTransition(root, ChangeImageTransform())
            //Меняем scaleType на значение этого перечисления scaleType как i - остаток от деления колечества элементов в этом перечислении
            img.scaleType = ImageView.ScaleType.values()[i % ImageView.ScaleType.values().size]
            //В зависимости от scaleType будет менятся изображение
            i++
            //выводим тост, какой сейчас scaleType на картинке
            Toast.makeText(this, img.scaleType.name, Toast.LENGTH_SHORT).show()
        }

    }
}