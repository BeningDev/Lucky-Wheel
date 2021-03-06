package com.bening.luckywheel.ui.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.bening.luckywheel.R
import com.bening.luckywheel.data.*
import com.bening.luckywheel.databinding.ActivityEditWheelBinding
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.binding.intent.intent
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.toast

class EditWheel : AppCompatActivity() {

    val binding: ActivityEditWheelBinding by viewBinding()
    val ListEditItems: ArrayList<DataItem> = ArrayList()

    val adapter: ItemEditAdapter by lazy {
        ItemEditAdapter()
    }

    private val dbHelper = DatabaseHelper(this)
    val dataWheel: DataWheel by intent("data")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_wheel)

        with(binding) {
            etWheelName.setText(dataWheel.nama)
            myWheelItems.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(
                    this@EditWheel,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }

            val ListItems = dbHelper.getWheelItems(dataWheel.id.toString())
            while(ListItems.moveToNext()) {
                adapter.addData(DataItem(ListItems.getString(0).toInt() ,ListItems.getString(1).toString()))
                ListEditItems.add(DataItem(0, ListItems.getString(1).toString()))
            }

            btnAddItem.onClick {
                if (etItem.text.toString().isEmpty()){
                    toast("Silahkan isi item")
                } else if (ListEditItems.contains(DataItem(0, etItem.text.toString()))){
                    toast("Item Sudah Ada")
                } else {
                    adapter.addData(DataItem(0, etItem.text.toString()))
                    ListEditItems.add(DataItem(0, etItem.text.toString()))
                    etItem.setText("")
                }
            }

            btnSave.onClick {
                if ( etWheelName.text.toString().isEmpty() ) {
                    toast("Wheel Name tidak boleh kosong")
                } else if ( dbHelper.wheelIsExist(etWheelName.text.toString()) && etWheelName.text.toString() != dataWheel.nama ) {
                    toast("Nama wheel sudah terdaftar")
                } else if ( adapter.getList().isEmpty() ) {
                    toast("Items tidak boleh kosong")
                } else {
                    dbHelper.delAllItem(dataWheel.id)
                    dbHelper.updateWheel(dataWheel.id, etWheelName.text.toString())
                    adapter.getList().forEach {
                        dbHelper.addItem(dataWheel.id, it.nama)
                    }
                    toast("Wheel Updated")
                    finish()
                }
            }

            btnBack.onClick {
                finish()
            }
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnYes = dialog.findViewById<Button>(R.id.btnYes)
        val btnNo = dialog.findViewById<Button>(R.id.btnNo)

        btnNo.onClick {
            dialog.dismiss()
        }

        btnYes.onClick {
            finish()
        }
    }
}