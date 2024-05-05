package kr.co.seonguk.application.fastvocabulary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.seonguk.application.fastvocabulary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {

    lateinit var binding:ActivityMainBinding
    lateinit var wordAdapter : WordAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        wordAdapter = WordAdapter(mutableListOf(), this)
        settingAdatper()
        settingEvent()
    }

    private fun settingAdatper(){
        binding.apply {
            wordRecyclerview.apply {
                adapter = wordAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
                val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    override fun onClick(word: Word) {
        Toast.makeText(this, "${word.text}", Toast.LENGTH_SHORT).show()
    }

    private fun settingEvent(){
        binding.apply {
            addButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddActivity::class.java))
            }
        }
    }
}