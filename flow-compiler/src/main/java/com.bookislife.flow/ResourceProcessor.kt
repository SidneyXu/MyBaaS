package com.bookislife.flow

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

/**
 * Created by SidneyXu on 2016/05/24.
 */
class ResourceProcessor:AbstractProcessor(){

    override fun process(annotations: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        throw UnsupportedOperationException()
    }

}