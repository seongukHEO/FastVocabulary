package kr.co.seonguk.application.fastvocabulary

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "word")
data class Word(
    val text:String,
    val mean:String,
    val type:String,

    //autoGenerate = true는 자동으로 숫자를 만들어 주는 것!
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
) : Parcelable
