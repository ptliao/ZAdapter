package com.hcanyz.zadapter.test

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.hcanyz.zadapter.ZAdapterThrottle
import com.hcanyz.zadapter.helper.bindZAdapter
import com.hcanyz.zadapter.hodler.ViewHolderHelper
import com.hcanyz.zadapter.registry.IHolderCreatorName
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_test_zadapter.*
import java.util.concurrent.TimeUnit

class TestZAdapterThrottleActivity : AppCompatActivity() {

    companion object {
        const val TAG = "testZAdapterActivity"
    }

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_zadapter)

        val listOf = arrayListOf<IHolderCreatorName>()
        //simple
        repeat(2) { listOf.add(SimpleData(R.mipmap.ic_launcher, "SimpleData_$it")) }
        //MultiData + R.layout.holder_multi_1
        repeat(7) { listOf.add(MultiData(R.mipmap.ic_launcher, "multiData_$it", false)) }
        //MultiData + R.layout.holder_multi_2
        repeat(7) { listOf.add(MultiData(R.mipmap.ic_launcher_round, "", true)) }
        //MultiData2 + R.layout.holder_multi_1
        repeat(7) { listOf.add(MultiData2(R.mipmap.ic_launcher, "MultiData2_$it")) }

        val zAdapter = ZAdapterThrottle<IHolderCreatorName>(mViewHolderHelper = ViewHolderHelper(fragmentActivity = this))
        //registry SimpleData + R.layout.holder_simple > SimpleHolder
        zAdapter.holderCreatorRegistry.registeredCreator(SimpleData::class.java.name) { parent ->
            return@registeredCreator SimpleHolder(parent)
        }
        //registry MultiData + R.layout.holder_multi_1 > MultiHolder
        zAdapter.holderCreatorRegistry.registeredCreator(MultiData::class.java.name) { parent ->
            return@registeredCreator MultiHolder(parent, R.layout.holder_multi_1)
        }
        //registry MultiData + R.layout.holder_multi_2 > MultiHolder
        zAdapter.holderCreatorRegistry.registeredCreator("${MultiData::class.java.name}_${R.layout.holder_multi_2}") { parent ->
            return@registeredCreator MultiHolder(parent, R.layout.holder_multi_2)
        }
        zAdapter.openThrottle(1000)
        zAdapter.mDatas = listOf
        recylerview.bindZAdapter(zAdapter)

        //listen clickEvent
        ViewModelProviders.of(this).get(EventViewModel::class.java).clickEvent.observe(this, Observer {
            when (it) {
                is MultiData -> {
                    Toast.makeText(this, "click -> $it", Toast.LENGTH_SHORT).show()
                }
                is MultiData2 -> {
                    Toast.makeText(this, "click -> $it", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val subscribe = Observable.interval(10, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    zAdapter.notifyDataChangedWithThrottle()
                }

        compositeDisposable.add(subscribe)

        zAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                Log.e(TAG, "onChanged -> ${System.currentTimeMillis()}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        //需要释放。 否则有内存泄漏问题
        (recylerview.adapter as ZAdapterThrottle<*>).release()
    }
}