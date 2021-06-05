package com.math.firstMaker.common


/**
 * Created by jedaichoi on 2018. 2. 10..
 */

class NetworkException(val msg: String = "네트워크에러") : Exception(msg)

open class ReponseException(val code: Int, val msg: String) : Exception(msg)

class TokenExpiredException(code: Int, msg: String) : ReponseException(code, msg)

class BeatFloServiceException(code:Int, msg:String) : ReponseException(code, msg)

class BeatFloMediaException(msg:String) : Exception(msg)

class NotAuthenticationException : Exception("인증에러")