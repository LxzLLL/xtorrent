package com.github.xtorrent.xtorrent.utils

/**
 * Created by zhihao.zeng on 16/11/29.
 */
fun <T> checkNotNull(t: T?): T {
    return t ?: throw NullPointerException()
}

fun <T> checkNotNull(t: T?, value: Any): T {
    return t ?: throw NullPointerException(value.toString())
}