package com.mandeep.dummyproject.Navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun LifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {

    val eventHandler = rememberUpdatedState(onEvent)

    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)


    DisposableEffect(lifecycleOwner.value) {
        Log.d("dfidnfd",lifecycleOwner.value.toString() )
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {  lifecycle.removeObserver(observer)  }
    }
}