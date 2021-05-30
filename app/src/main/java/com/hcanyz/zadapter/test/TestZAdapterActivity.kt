package com.hcanyz.zadapter.test

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.hcanyz.zadapter.ZAdapter
import com.hcanyz.zadapter.helper.bindZAdapter
import com.hcanyz.zadapter.hodler.ViewHolderHelper
import com.hcanyz.zadapter.hodler.ZViewHolder
import com.hcanyz.zadapter.hodler.getViewModel
import com.hcanyz.zadapter.registry.IHolderCreater
import kotlinx.android.synthetic.main.activity_test_zadapter.*

class TestZAdapterActivity : AppCompatActivity() {

    companion object {
        const val TAG = "testZAdapterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_zadapter)

        val listOf = arrayListOf<IHolderCreater>()
        //simple
        repeat(2) { listOf.add(SimpleData(R.mipmap.ic_launcher, "SimpleData_$it")) }
        //MultiData + R.layout.holder_multi_1
        repeat(7) { listOf.add(MultiData(R.mipmap.ic_launcher, "multiData_$it", false)) }
        //MultiData + R.layout.holder_multi_2
        repeat(7) { listOf.add(MultiData(R.mipmap.ic_launcher_round, "multiData_$it", true)) }
        //MultiData2 + R.layout.holder_multi_1
        repeat(7) { listOf.add(MultiData2(R.mipmap.ic_launcher, "MultiData2_$it")) }
        repeat(14) { listOf.add(SimpleLifeBean(R.mipmap.ic_launcher_round, "SimpleLifeBean_$it")) }

        val zAdapter = ZAdapter(listOf, ViewHolderHelper(fragmentActivity = this))
//        //registry SimpleData + R.layout.holder_simple > SimpleHolder
//        zAdapter.registry.registered(SimpleData::class.java.name) { parent ->
//            val testHolder = SimpleHolder(parent)
//            testHolder.lifecycle.addObserver(LifecycleObserverTest())
//            return@registered testHolder
//        }
//        //registry MultiData + R.layout.holder_multi_1 > MultiHolder
//        zAdapter.registry.registered(MultiData::class.java.name) { parent ->
//            val multiHolder = MultiHolder(parent, R.layout.holder_multi_1)
//            multiHolder.lifecycle.addObserver(LifecycleObserverTest())
//            return@registered multiHolder
//        }
//        //registry MultiData + R.layout.holder_multi_2 > MultiHolder
//        zAdapter.registry.registered("${MultiData::class.java.name}_${R.layout.holder_multi_2}") { parent ->
//            val multiHolder = MultiHolder(parent, R.layout.holder_multi_2)
//            multiHolder.lifecycle.addObserver(LifecycleObserverTest())
//            return@registered multiHolder
//        }
        recylerview.bindZAdapter(zAdapter)

        //listen clickEvent
        getViewModel(EventViewModel::class.java).clickEvent.observe(this, Observer {
            when (it) {
                is SimpleData ->{
                    Toast.makeText(this, "click -> $it", Toast.LENGTH_SHORT).show()
                }
                is MultiData -> {
                    Toast.makeText(this, "click -> $it", Toast.LENGTH_SHORT).show()
                }
                is MultiData2 -> {
                    Toast.makeText(this, "click -> $it", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}