package com.math.firstMaker

import agency.tango.android.avatarview.AvatarPlaceholder
import agency.tango.android.avatarview.ImageLoaderBase
import agency.tango.android.avatarview.views.AvatarView
import android.graphics.Bitmap
import androidx.annotation.NonNull
import com.bumptech.glide.Glide

class GlideLoader : ImageLoaderBase {

    constructor() : super() {}

    constructor(defaultPlaceholderString: String) : super(defaultPlaceholderString) {}

    override fun loadImage(@NonNull avatarView: AvatarView, avatarUrl: String?, name: String) {
        Glide.with(avatarView.context)
            .load(avatarUrl)
            // .crossFade()
            .placeholder(AvatarPlaceholder(name, "-"))
            .fitCenter()
            .into(avatarView)
    }

    override fun loadImage(
        avatarView: AvatarView,
        avatarPlaceholder: AvatarPlaceholder,
        avatarUrl: String?
    ) {
        Glide.with(avatarView.context)
            .load(avatarUrl)
            // .crossFade()
            .placeholder(avatarPlaceholder)
            .fitCenter()
            .into(avatarView)
    }

    fun loadImageBase64( avatarView: AvatarView,avatarPlaceholder: AvatarPlaceholder, decodeBytes : Bitmap) {
        Glide.with (avatarView.context)
        .load(decodeBytes)
        .placeholder(avatarPlaceholder)
        .fitCenter()
        .into(avatarView)
    }
}
