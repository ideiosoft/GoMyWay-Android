package ssc.snow.app.gomyways.views.home.controler.profile

interface IEmailCallback {
   fun delUserEmail(mEmail: String)
   fun resendVerificationEmail(mEmail: String)
   fun makePrimaryEmail(mEmail: String,mEmailType:String)
}