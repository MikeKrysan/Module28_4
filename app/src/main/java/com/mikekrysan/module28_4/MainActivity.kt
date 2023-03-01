package com.mikekrysan.module28_4

import android.graphics.Rect
import android.os.Bundle
import android.transition.Explode
import android.transition.Transition
import android.transition.TransitionManager
import android.transition.Visibility.MODE_IN
import android.transition.Visibility.MODE_OUT
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.TableRow
import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatActivity
import androidx.core.transition.addListener
import androidx.core.view.children
import androidx.core.view.forEach
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: MyAdapter

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
//        img.scaleX = 0.5F
//        img.scaleY = 0.5F
//        //Счетчик который отвечает за текущий вариант scaleType
//        var i = 0
//        img.setOnClickListener {
//            TransitionManager.beginDelayedTransition(root, ChangeImageTransform())
//            //Меняем scaleType на значение этого перечисления scaleType как i - остаток от деления колечества элементов в этом перечислении
//            img.scaleType = ImageView.ScaleType.values()[i % ImageView.ScaleType.values().size]
//            //В зависимости от scaleType будет менятся изображение
//            i++
//            //выводим тост, какой сейчас scaleType на картинке
//            Toast.makeText(this, img.scaleType.name, Toast.LENGTH_SHORT).show()
//        }


        //Пример с TransitionSet из ChangeBounds и ChangeImageTransform
        //переменная отвечающая за текущее состояние: расширенное или нет
//        var expanded = false
//        val transitionSet = TransitionSet()
//            .addTransition(ChangeBounds())  //отвечает за изменение расположения
//            .addTransition(ChangeImageTransform())  //отвечает за изменение высоты и ширины
//
//        img.setOnClickListener {
//            expanded = !expanded    //при нажатии изменяем состояние на противоположное значение
//            TransitionManager.beginDelayedTransition(root, transitionSet)
//            val params: ViewGroup.LayoutParams = img.layoutParams
//            params.height = if (expanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
//            img.layoutParams = params
//
//            img.scaleType = if (expanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
//        }

        //Пример 2. Explode Transition - Множество объектов на экране разлетаются в разные стороны при нажатии на одном из элементов

        //Ставится пустой emptyAdapter который не содержит элемнтов. Когда объекты разлетаются, на сцене не должно быть объектов
        val emptyAdapter = MyAdapter(0)
        //наш обычный адаптер содержит 9 элементов. Передаем обработчик нажатия:
        adapter = MyAdapter(9) {
            //при нажатии мы получаем Rect как viewRect
            val viewRect = Rect()
            // как элемент, по которому нажали
            it.getGlobalVisibleRect(viewRect)
            //Создаем explodeOut, создаем видимость анимации, MODE, и реализуем эпицентр Callback(), который возвращает viewRect
            val explodeOut = Explode().apply {
                mode = MODE_OUT; duration = 1200;
                epicenterCallback = object : Transition.EpicenterCallback() {
                    override fun onGetEpicenter(transition: Transition?): Rect {
                        return viewRect
                    }
                }
            }
            //то-же самое с explodeIn
            val explodeIn = Explode().apply {
                mode = MODE_IN; duration = 1200;
                epicenterCallback = object : Transition.EpicenterCallback() {
                    override fun onGetEpicenter(transition: Transition?): Rect {
                        return viewRect
                    }
                }
            }

            //на explodeOut вешаем обработчик событий. Когда transition заканчивается adapter меняется на адаптер без элементов

            explodeOut.addListener({ _ ->
                TransitionManager.beginDelayedTransition(recycler_view, explodeIn)
                recycler_view.adapter = adapter
                it.setBackgroundResource(R.color.teal_200)
            })
            //запускаем transition explodeOut меняя адаптер на emptyAdapter
            TransitionManager.beginDelayedTransition(recycler_view, explodeOut)
            recycler_view.adapter = emptyAdapter
            it.setBackgroundResource(com.google.android.material.R.color.design_default_color_primary_variant)
        }
E
        //Изначально адаптер равен наполненному кубиками адаптеру
        recycler_view.adapter = adapter
    }
}