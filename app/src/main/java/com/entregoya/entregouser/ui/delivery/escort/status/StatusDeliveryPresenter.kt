package com.entregoya.entregouser.ui.delivery.escort.status

import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.entregoya.entregouser.R
import com.entregoya.entregouser.entity.back.EntregoWaypoint
import com.entregoya.entregouser.entity.back.getCurrentPoint
import com.entregoya.entregouser.entity.back.getDestinationPoint
import com.entregoya.entregouser.entity.route.PointStatus
import com.entregoya.entregouser.mvp.presenter.BaseMvpPresenter


class StatusDeliveryPresenter : BaseMvpPresenter<StatusDeliveryContract.View>(),
        StatusDeliveryContract.Presenter {

    override fun buildSwitchListByState(waypoints: Array<EntregoWaypoint>, switchList: List<ImageView>) {

        mView?.getAppContext()?.let {


            when (waypoints.getCurrentPoint().status) {
                PointStatus.PENDING -> switchList[0].loadDrawable(R.drawable.red_dot)

                PointStatus.GOING -> {
                    val currentIndex = 1
                    switchList.loadDone(currentIndex)
                    switchList[currentIndex].loadDrawable(R.drawable.red_dot)
                }

                PointStatus.WAITING, PointStatus.DONE ->
                    when (waypoints.getDestinationPoint().status) {

                        PointStatus.PENDING -> {
                            val currentIndex = 2
                            switchList.loadDone(currentIndex)
                            switchList[currentIndex].loadDrawable(R.drawable.red_dot)
                        }

                        PointStatus.GOING -> {
                            val currentIndex = 3
                            switchList.loadDone(currentIndex)
                            switchList[currentIndex].loadDrawable(R.drawable.red_dot)
                        }

                        PointStatus.WAITING-> {
                            val currentIndex = 4
                            switchList.loadDone(currentIndex)
                            switchList[currentIndex].loadDrawable(R.drawable.red_dot)
                        }
                        PointStatus.DONE->{
                            val currentIndex = 5
                            switchList.loadDone(currentIndex)
                        }

                        else -> {
                        }
                    }
                else -> {
                }
            }
        }
    }

}

fun List<ImageView>.loadDone(toIndex : Int){
    this.subList(0, toIndex)
            .forEach { it.loadDrawable(R.drawable.blue_dot) }
}

fun ImageView.loadDrawable(resId: Int) {
    setImageDrawable(ContextCompat.getDrawable(context, resId))
}

