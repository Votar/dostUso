package com.entrego.entregouser.ui.delivery.escort.status

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.entrego.entregouser.R
import com.entrego.entregouser.entity.route.EntregoRouteModel
import com.entrego.entregouser.entity.route.PointStatus
import com.entrego.entregouser.mvp.presenter.BaseMvpPresenter


class StatusDeliveryPresenter : BaseMvpPresenter<StatusDeliveryContract.View>(),
        StatusDeliveryContract.Presenter {

    override fun buildSwitchListByState(route: EntregoRouteModel, switchList: List<ImageView>) {

        mView?.getAppContext()?.let {

            for (next in switchList)
                Glide.with(it)
                        .load(R.drawable.default_dot)
                        .into(next)

            when (route.getCurrentPoint().status) {
                PointStatus.PENDING -> {
                    Glide.with(it)
                            .load(R.drawable.finish_dot)
                            .into(switchList[0])
                }
                PointStatus.GOING -> {
                    Glide.with(it)
                            .load(R.drawable.finish_dot)
                            .into(switchList[1])
                }
                PointStatus.WAITING, PointStatus.DONE -> when (route.getDestinationPoint().status) {
                        PointStatus.PENDING -> {
                            Glide.with(it)
                                    .load(R.drawable.finish_dot)
                                    .into(switchList[2])
                        }
                        PointStatus.GOING -> {
                            Glide.with(it)
                                    .load(R.drawable.finish_dot)
                                    .into(switchList[3])
                        }
                        PointStatus.WAITING -> {
                            Glide.with(it)
                                    .load(R.drawable.finish_dot)
                                    .into(switchList[4])
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