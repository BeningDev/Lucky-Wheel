package com.bening.luckywheel.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bening.luckywheel.R
import com.bening.luckywheel.data.DataWheel
import com.bening.luckywheel.data.DatabaseHelper
import com.bening.luckywheel.databinding.ActivityMainBinding
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.startActivity
import com.oratakashi.viewbinding.core.tools.toast

class MainActivity : AppCompatActivity() {

    val binding: ActivityMainBinding by viewBinding()
    private var dbHelper = DatabaseHelper(this)

    val adapter: WheelAdapter by lazy {
        WheelAdapter({ dataWheel ->
            dbHelper.delWheel(dataWheel.id)
            toast("Wheel di Hapus")
        }, { dataWheel ->
            startActivity(SpinWheel::class.java) {
                it.putExtra("data", dataWheel)
            }
        }, { dataWheel ->
            startActivity(EditWheel::class.java) {
                it.putExtra("data", dataWheel)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            myWheels.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }

            btnAdd.onClick {
                startActivity(AddWheel::class.java)
            }

            val listWheels = dbHelper.getWheels()
            while (listWheels.moveToNext()) {
                val newWheel = DataWheel(listWheels.getString(0).toInt(), listWheels.getString(1).toString())
                adapter.addData(newWheel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.clear()
        val listWheels = dbHelper.getWheels()
        while (listWheels.moveToNext()) {
            val newWheel = DataWheel(listWheels.getString(0).toInt(), listWheels.getString(1).toString())
            adapter.addData(newWheel)
        }
    }
}