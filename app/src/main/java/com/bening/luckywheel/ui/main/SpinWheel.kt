package com.bening.luckywheel.ui.main

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bening.luckywheel.R
import com.bening.luckywheel.data.DataWheel
import com.bening.luckywheel.data.DatabaseHelper
import com.bening.luckywheel.databinding.ActivitySpinWheelBinding
import com.bluehomestudio.luckywheel.WheelItem
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.binding.intent.intent
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.toast
import kotlin.random.Random

class SpinWheel : AppCompatActivity() {

    val binding: ActivitySpinWheelBinding by viewBinding()
    val wheelItemsList: ArrayList<WheelItem> = ArrayList()
    private var dbHelper = DatabaseHelper(this)

    val dataWheel: DataWheel by intent("data")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spin_wheel)

        with(binding) {
            tvTitle.text = dataWheel.nama

            val Items = dbHelper.getWheelItems(dataWheel.id.toString())
            var i = 1
            while (Items.moveToNext()) {
                val text = Items.getString(1).toString()
                val item: WheelItem
                if ( i == 2) {
                    item = WheelItem(ContextCompat.getColor(this@SpinWheel, R.color.black_200), BitmapFactory.decodeResource(resources, R.drawable.ic_action_name),text)
                    i--
                } else {
                    item = WheelItem(ContextCompat.getColor(this@SpinWheel, R.color.black_300), BitmapFactory.decodeResource(resources, R.drawable.ic_action_name),text)
                    i++
                }

                wheelItemsList.add(item)
            }

            lwv.addWheelItems(wheelItemsList)

            var point: Int
            point = 1

            btnSpin.onClick {
                point = (1..Items.count).random()
                lwv.rotateWheelTo(point)
                tvResult.setText("")
            }

            lwv.setLuckyWheelReachTheTarget {
                val item = wheelItemsList.get(point - 1)
                val result = item.text
                tvResult.setText(result.toString())
                toast(result.toString())
            }

            btnBack.onClick {
                finish()
            }
        }
    }
}