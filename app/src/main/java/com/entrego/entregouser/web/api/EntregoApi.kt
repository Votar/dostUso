package entrego.com.android.web.api

import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by bertalt on 29.11.16.
 */
object EntregoApi {


    const val BASE_URL = "http://62.149.12.54/mobile-gateway-1.0.0-SNAPSHOT/"
    const val CONTENT_JSON = "content-type: application/json"
    const val TOKEN = "x-auth-token"

    object REQUESTS {
        const val AUTH = "login"
        const val REGISTRATION = "messenger/user/register"
        const val PROFILE = "messenger/user"
        const val VEHICLE = "messenger/user/vehicle"
        const val CHANGE_PROFILE = "messenger/user/change"
        const val CHANGE_PROFILE_PASSWORD = "messenger/user/change/password"
        const val GET_DELIVERY = "messenger/delivery"
        const val POST_LOCATION = "messenger/user/track"
        const val POST_CANCEL_DELIVERY = "messenger/delivery/{id}/cancel"
        const val POST_ACCEPT_DELIVERY = "messenger/delivery/{id}/accept"
        const val POST_DECLINE_DELIVERY = "messenger/delivery/{id}/decline"
        const val POST_CHANGE_STATUS = "messenger/delivery/{id}/status"
        const val POST_DRIVER_LICENCE = "messenger/user/change/driverLicense"
        const val POST_PERSON_LICENCE = "messenger/user/change/driverLicense"
        const val POST_USER_PHOTO =  "messenger/user/change/photo"
        const val POST_FINISH_DELIVERY ="messenger/delivery/{id}/finish"
        const val POST_POST_RESTORE_PASSWORD = ""

    }

//    interface Authorization {
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.AUTH)
//        fun auth(@Body body: AuthBody): Call<BaseEntregoResponse>
//    }
//
//    interface Registration {
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.REGISTRATION)
//        fun registration(@Body body: RegistrationBody): Call<EntregoResultRegistration>
//    }
//
//    interface GetProfile {
//        @Headers(CONTENT_JSON)
//        @GET(REQUESTS.PROFILE)
//        fun getProfile(@Header(TOKEN) token: String): Call<EntregoResultGetProfile>
//    }
//
//    interface GetVehicle {
//        @Headers(CONTENT_JSON)
//        @GET(REQUESTS.VEHICLE)
//        fun getVehicle(@Header(TOKEN) token: String): Call<EntregoResultGetVehicle>
//    }
//
//
//    interface UpdateVehicle {
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.VEHICLE)
//        fun updateVehicle(@Header(TOKEN) token: String, @Body body: UserVehicleModel): Call<EntregoResultEditVehicle>
//    }
//
//
//    interface UpdateProfile {
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.CHANGE_PROFILE)
//        fun updateProfile(@Header(TOKEN) token: String, @Body body: UserProfileModel): Call<EntregoResultEditProfile>
//    }
//
//    interface UpdateProfilePassword {
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.CHANGE_PROFILE_PASSWORD)
//        fun updateProfile(@Header(TOKEN) token: String, @Body body: ChangePasswordRequest): Call<EntregoResultEditPassword>
//    }
//
//
//    interface GetDelivery {
//        @Headers(CONTENT_JSON)
//        @GET(REQUESTS.GET_DELIVERY)
//        fun getDelivery(@Header(TOKEN) token: String): Call<EntregoResultGetDelivery>
//    }
//
//    interface PostLocation {
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.POST_LOCATION)
//        fun postLocation(@Header(TOKEN) token: String, @Body location: LatLng): Call<BaseEntregoResponse>
//    }
//
//    interface CancelDelivery {
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.POST_CANCEL_DELIVERY)
//        fun cancelDelivery(@Header(TOKEN) token: String, @Path("id") deliveryId: Int): Call<JsonElement>
//    }
//
//    interface AcceptDelivery {
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.POST_ACCEPT_DELIVERY)
//        fun acceptDelivery(@Header(TOKEN) token: String, @Path("id") deliveryId: Int): Call<BaseEntregoResponse>
//    }
//
//    interface DeclineDelivery {
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.POST_DECLINE_DELIVERY)
//        fun declineDelivery(@Header(TOKEN) token: String, @Path("id") deliveryId: Int): Call<BaseEntregoResponse>
//    }
//
//    interface ChangeStatus {
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.POST_CHANGE_STATUS)
//        fun changeStatus(@Header(TOKEN) token: String, @Path("id") deliveryId: Int, @Body body: ChangeStatusBody): Call<EntregoResultStatusChanged>
//    }
//
//    interface UploadDriverLicence{
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.POST_DRIVER_LICENCE)
//        fun postDriverLicence(@Header(TOKEN) token: String, @Body body: UploadPhotoBody): Call<BaseEntregoResponse>
//    }
//    interface UploadPersonLicence{
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.POST_PERSON_LICENCE)
//        fun postPersonLicence(@Header(TOKEN) token: String, @Body body: UploadPhotoBody): Call<BaseEntregoResponse>
//    }
//    interface UploadUserPhoto{
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.POST_USER_PHOTO)
//        fun postUserPhoto(@Header(TOKEN) token: String, @Body body: UploadPhotoBody): Call<BaseEntregoResponse>
//    }
//    interface FinishDelivery{
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.POST_FINISH_DELIVERY)
//        fun finishDelivery(@Header(TOKEN) token: String,@Path("id")deliveryId:Int, @Body body: FinishDeliveryBody): Call<BaseEntregoResponse>
//    }
//    interface RestorePassword{
//        @Headers(CONTENT_JSON)
//        @POST(REQUESTS.POST_POST_RESTORE_PASSWORD)
//        fun restorePassword(@Body body: RestorePasswordBody): Call<BaseEntregoResponse>
//    }
}