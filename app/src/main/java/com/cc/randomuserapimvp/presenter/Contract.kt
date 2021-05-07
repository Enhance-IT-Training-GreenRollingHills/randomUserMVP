package com.cc.randomuserapimvp.presenter

import com.cc.randomuserapimvp.model.APIData

interface Contract {

    interface Presenter {
        suspend fun getData (total:Int)
    }

    interface View {

        fun updateRecyclerView (data:APIData)
    }
}