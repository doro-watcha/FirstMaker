package com.math.firstMaker.mock

import kotlin.random.Random

object fakeAuthInfo {

    val email = "goddoro@naver.com"
    val name = "goddoro"
    val password = "gusgh0705!"
    val acceptedLegalNoticeVersion = "2020-05-21"

    val unverifiedEmail = "${Random.nextInt()}@Test.com"
    val snsEmail = "goddoro@naver.com"
    val firebaseUid = "Qd6kNYbZnPXmlBnw5oTc0zIzcih2"

    val coverUrl = "https://cdn.staging.beatflo.co/users/cover_default.png"
    val avatar = "https://cdn.staging.beatflo.co/users/avatar_default.png"

}