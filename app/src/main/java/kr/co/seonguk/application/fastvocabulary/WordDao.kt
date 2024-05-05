package kr.co.seonguk.application.fastvocabulary

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    //word라는 테이블의 모든 정보를 가져온다
    //ORDER BY id DESC는 최신 순으로 데이터를 가져온다 id의 숫자를 하나씩 증가하게 뒀기 때문
    @Query("SELECT * from word ORDER BY id DESC")
    fun getAll(): List<Word>

    //최신 거 하나만 가져온다
    @Query("SELECT * from word ORDER BY id DESC LIMIT 1")
    fun getLatesWord():Word

    //값을 더해준다
    @Insert
    fun insert(word: Word)

    @Delete
    fun delete(word: Word)

    @Update
    fun update(word: Word)




}