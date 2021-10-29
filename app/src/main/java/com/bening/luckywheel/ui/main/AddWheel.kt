package com.bening.luckywheel.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bening.luckywheel.data.DataItem
import com.bening.luckywheel.data.DatabaseHelper
import com.bening.luckywheel.databinding.ActivityAddWheelBinding
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.toast

class AddWheel : AppCompatActivity() {

    val binding: ActivityAddWheelBinding by viewBinding()

    internal var dbHelper = DatabaseHelper(this)

    val adapter: ItemAdapter by lazy {
        ItemAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            myWheelItems.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(
                    this@AddWheel,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }


            btnBack.onClick {
                finish()
            }

            btnAddItem.onClick {
                if (etItem.text.toString().isEmpty() || adapter.getList().contains(DataItem(1, etItem.text.toString()))){
                    toast("Silahkan isi item")
                }else{
                    adapter.addData(DataItem(1, etItem.text.toString()))
                    etItem.setText("")
                }
            }

            btnSave.onClick {
                if (etWheelName.text.toString().isEmpty()) {
                    toast("Silahkan isi semua kolom")
                } else if ( adapter.getList().isEmpty() ) {
                    toast("Item tidak boleh kosong")
                } else {
                    dbHelper.addWheel(etWheelName.text.toString(), adapter.getList())
                    toast("Wheel Sudah ditambahkan")
                    finish()
                }
            }

        }
    }
}