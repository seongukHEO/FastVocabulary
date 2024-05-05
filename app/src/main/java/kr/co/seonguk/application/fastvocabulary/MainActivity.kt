package kr.co.seonguk.application.fastvocabulary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.seonguk.application.fastvocabulary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {

    lateinit var binding:ActivityMainBinding
    lateinit var wordAdapter : WordAdapter
    private val updateAddWordResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val isUpdated = it.data?.getBooleanExtra("isUpdated", false) ?: false
        if (it.resultCode == RESULT_OK && isUpdated){
            updateAddWord()
        }
    }

    private val updateEditWordResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val updatedWord = it.data?.getParcelableExtra<Word>("updatedWord") ?: null
        if (it.resultCode == RESULT_OK && updatedWord != null){
            updateEditWord(updatedWord)
        }
    }

    private var selectedWord: Word? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingAdatper()
        settingEvent()
    }

    private fun settingAdatper(){
        wordAdapter = WordAdapter(mutableListOf(), this)
        binding.apply {
            wordRecyclerview.apply {
                adapter = wordAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
                val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
        Thread{
            val list = AppDataBase.getInstance(this)?.wordDao()?.getAll()
            if (list != null) {
                wordAdapter.list.addAll(list)
            }
            runOnUiThread {
                wordAdapter.notifyDataSetChanged()
            }
        }.start()
    }

    override fun onClick(word: Word) {
        selectedWord = word
        binding.textTextView.text = word.text
        binding.meanTextView.text = word.mean
    }

    private fun settingEvent(){
        binding.apply {
            addButton.setOnClickListener {
                val newIntent = Intent(this@MainActivity, AddActivity::class.java)
                updateAddWordResult.launch(newIntent)
            }

            deleteButton.setOnClickListener {
                delete()
            }
            editButton.setOnClickListener {
                edit()
            }
        }
    }

    private fun edit(){
        if (selectedWord == null) return

        val intent = Intent(this, AddActivity::class.java).putExtra("originData", selectedWord)
        updateAddWordResult.launch(intent)
    }

    private fun delete(){

        if (selectedWord == null) return

        Thread{
            selectedWord?.let {
                AppDataBase.getInstance(this)?.wordDao()?.delete(it)
                runOnUiThread {
                    wordAdapter.list.remove(it)
                    wordAdapter.notifyDataSetChanged()
                    binding.textTextView.text = ""
                    binding.meanTextView.text = ""
                }
            }
        }.start()
    }

    private fun updateAddWord(){
        Thread{
            val word = AppDataBase.getInstance(this)?.wordDao()?.getLatesWord()
            if (word != null){
                wordAdapter.list.add(0, word)
                runOnUiThread {
                    wordAdapter.notifyDataSetChanged()
                }
            }
        }.start()
    }

    private fun updateEditWord(word: Word){
        val index = wordAdapter.list.indexOfFirst { it.id == word.id }
        wordAdapter.list.set(index, word)
        runOnUiThread { wordAdapter.notifyItemChanged(index) }
    }
}