package com.cc.randomuserapimvp.presenter

import com.cc.randomuserapimvp.network.Repository

class Presenter (private val view:Contract.View) : Contract.Presenter {

    private val repo = Repository()


    override suspend fun getData(total:Int) {
        val list = repo.getInfo(total)
        view.updateRecyclerView(list)
    }


}