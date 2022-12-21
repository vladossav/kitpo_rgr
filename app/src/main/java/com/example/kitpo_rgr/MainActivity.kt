package com.example.kitpo_rgr

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kitpo_rgr.types.UserType


class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainText: TextView = findViewById(R.id.main_text)
        mainText.text = viewModel.logText
        val scroll: ScrollView = findViewById(R.id.scroller)

        val spinner: Spinner = findViewById(R.id.spinner)
        val spinnerList = UserFactory.getTypeNameList()
        val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, spinnerList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val insertIndex: EditText = findViewById(R.id.insert_index_input)
        val insertValue: EditText = findViewById(R.id.insert_value_input)
        val insertBtn: Button = findViewById(R.id.insert_btn)
        insertBtn.setOnClickListener {
            if (insertIndex.text.isNullOrEmpty() || insertValue.text.isNullOrEmpty()) return@setOnClickListener
            val index = Integer.parseInt(insertIndex.text.toString())
            val value = UserFactory
                .getBuilderByName(spinner.selectedItem.toString())
                .parseValue(insertValue.text.toString()) as UserType
            insertIndex.setText("")
            insertValue.setText("")

            viewModel.tree.insertByIndex(index,value)
            viewModel.logText = viewModel.logText + "\nInsert " + value.toString() + " at " + index
            mainText.text = viewModel.logText
        }

        spinner.setSelection(0,false)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                viewModel.tree.init()
                insertValue.hint = spinner.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val removeIndex: EditText = findViewById(R.id.remove_index_input)
        val removeBtn: Button = findViewById(R.id.remove_btn)
        removeBtn.setOnClickListener {
            if (removeIndex.text.isNullOrEmpty()) return@setOnClickListener
            val index = Integer.parseInt(removeIndex.text.toString())
            viewModel.tree.deleteByIndex(index)
            viewModel.logText = viewModel.logText + "\nRemove " + index
            mainText.text =  viewModel.logText
            scroll.fullScroll(View.FOCUS_DOWN)
        }

        val balanceBtn: Button = findViewById(R.id.balance_btn)
        balanceBtn.setOnClickListener {
            viewModel.tree.balance()
            viewModel.logText = viewModel.logText + "\nTree was balanced!\n" + viewModel.tree.toString()
            mainText.text =  viewModel.logText
            scroll.fullScroll(View.FOCUS_DOWN)
        }

        val showBtn: Button = findViewById(R.id.show_btn)
        showBtn.setOnClickListener {
            viewModel.logText = viewModel.logText + "\n" + viewModel.tree.toString()
            mainText.text =  viewModel.logText
            scroll.fullScroll(View.FOCUS_DOWN)
        }

        val saveBtn: Button = findViewById(R.id.save_btn)
        saveBtn.setOnClickListener {
            Serialization.saveToFile(viewModel.tree,"tree.txt", spinner.selectedItem.toString())
            viewModel.logText = viewModel.logText + "\nTree was saved!\n"
            mainText.text =  viewModel.logText
            scroll.fullScroll(View.FOCUS_DOWN)
        }

        val loadBtn: Button = findViewById(R.id.load_btn)
        loadBtn.setOnClickListener {
            viewModel.tree = Serialization.loadFile("tree.txt")
            viewModel.logText = viewModel.logText + "\nTree was loaded!\n"
            mainText.text =  viewModel.logText
            scroll.fullScroll(View.FOCUS_DOWN)
        }
    }
}