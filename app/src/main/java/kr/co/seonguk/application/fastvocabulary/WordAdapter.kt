package kr.co.seonguk.application.fastvocabulary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.seonguk.application.fastvocabulary.databinding.ItemWordBinding

class WordAdapter(val list:MutableList<Word>, val itemClickListener:ItemClickListener? = null) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {



    inner class WordViewHolder(itemWordBinding: ItemWordBinding) : RecyclerView.ViewHolder(itemWordBinding.root){
        var itemWordBinding: ItemWordBinding


        init {
            this.itemWordBinding = itemWordBinding
            this.itemWordBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.itemWordBinding.textTextView2.text = list[position].text
        holder.itemWordBinding.meanTextView2.text = list[position].mean
        holder.itemWordBinding.typeChip.text = list[position].type
        holder.itemWordBinding.root.setOnClickListener {
            itemClickListener?.onClick(list[position])
        }
    }

    interface ItemClickListener{
        fun onClick(word:Word)
    }
}