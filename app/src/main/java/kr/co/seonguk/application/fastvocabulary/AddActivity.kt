package kr.co.seonguk.application.fastvocabulary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.chip.Chip
import kr.co.seonguk.application.fastvocabulary.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    lateinit var binding:ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        settingEvent()
    }

    private fun initView(){
        val types = listOf(
            "명사", "대명사","동사", "형용사", "부사", "전치사", "접속사", "감탄사"
        )
        binding.typeChipGroup.apply {
            types.forEach {
                addView(createChip(it))
            }
        }
    }

    private fun createChip(text:String):Chip{
        //chip을 만든다
        return Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
    }

    private fun settingEvent(){
        binding.apply {
            addButton.setOnClickListener {
                add()
            }
        }
    }

    private fun add(){
        binding.apply {
            val text = textInputEditText.text.toString()
            val mean = meanTextInputEditText.text.toString()
            //체킹된 Chip의 id값을 가져온다(text)
            val type = findViewById<Chip>(typeChipGroup.checkedChipId).text
            val word = Word(text, mean, type.toString())

            Thread{
                AppDataBase.getInstance(this@AddActivity)?.wordDao()?.insert(word)
                runOnUiThread {
                    Toast.makeText(this@AddActivity, "저장완료", Toast.LENGTH_SHORT).show()
                }
                finish()
            }.start()
        }
    }
}