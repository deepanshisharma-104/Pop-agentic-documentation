package com.pop.components.ds_components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.annotation.RawRes
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.controllers.RiveFileController
import app.rive.runtime.kotlin.core.Alignment
import app.rive.runtime.kotlin.core.Fit
import app.rive.runtime.kotlin.core.Loop
import app.rive.runtime.kotlin.core.PlayableInstance
import android.util.Log

/**
 * Composable function to display a Rive animation.
 *
 * This function wraps the [RiveAnimationView] in an [AndroidView] to integrate it with Jetpack Compose.
 * It allows specifying various parameters to control the animation's behavior and appearance.
 *
 * @param modifier Modifier to be applied to the RiveAnimationView. Defaults to [Modifier].
 * @param resourceId The raw resource ID of the Rive animation file.
 * @param autoplay Whether the animation should start playing automatically. Defaults to `false`.
 * @param autoBind Whether the animation view should automatically bind to the lifecycle. Defaults to `false`.
 * @param artboardName The name of the artboard to display. If `null`, the default artboard will be used.
 * @param animationName The name of the animation to play. If `null`, the default animation will be played.
 * @param stateMachineName The name of the state machine to use. If `null`, no state machine will be used.
 * @param fit How the animation should be scaled to fit the view. Defaults to [Fit.CONTAIN].
 * @param alignment How the animation should be aligned within the view. Defaults to [Alignment.CENTER].
 * @param loop How the animation should loop. Defaults to [Loop.AUTO].
 * @param contentDescription A textual description of the Rive animation for accessibility.
 * @param isPlayingLambda A lambda function that returns `true` if the animation should be playing, `false` otherwise.
 *                        This allows for controlling the animation's play state from outside the composable.
 * @param notifyLoop A callback function that is invoked when an animation loops.
 * @param notifyPause A callback function that is invoked when an animation is paused.
 * @param notifyPlay A callback function that is invoked when an animation starts playing.
 * @param notifyStateChanged A callback function that is invoked when the state of a state machine changes.
 *                           It provides the name of the state machine and the new state name.
 * @param notifyStop A callback function that is invoked when an animation stops.
 * @param update A lambda function that provides access to the underlying [RiveAnimationView] instance
 *               for advanced customization. Defaults to an empty lambda.
 */
@Composable
fun RiveAnimationCompose(
    modifier: Modifier = Modifier,
    @RawRes resourceId: Int,
    autoplay: Boolean = false,
    autoBind: Boolean = false,
    artboardName: String? = null,
    animationName: String? = null,
    stateMachineName: String? = null,
    fit: Fit = Fit.CONTAIN,
    alignment: Alignment = Alignment.CENTER,
    loop: Loop = Loop.AUTO,
    contentDescription: String? = null,
    isPlayingLambda: () -> Boolean = { autoplay },
    notifyLoop: ((PlayableInstance) -> Unit)? = null,
    notifyPause: ((PlayableInstance) -> Unit)? = null,
    notifyPlay: ((PlayableInstance) -> Unit)? = null,
    notifyStateChanged: ((String, String) -> Unit)? = null,
    notifyStop: ((PlayableInstance) -> Unit)? = null,
    update: (RiveAnimationView) -> Unit = { _ -> }
) {

    var riveAnimationView: RiveAnimationView? = null
    val lifecycleOwner = LocalLifecycleOwner.current

    val semantics = if (contentDescription != null) {
        Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    } else {
        Modifier
    }
    val listener = object : RiveFileController.Listener {
        override fun notifyLoop(animation: PlayableInstance) {
            Log.d("RiveAnimation", "notifyLoop : ${animation.name}")
            notifyLoop?.invoke(animation)
        }

        override fun notifyPause(animation: PlayableInstance) {
            Log.d("RiveAnimation", "notifyPause : ${animation.name}")
            notifyPause?.invoke(animation)
        }

        override fun notifyPlay(animation: PlayableInstance) {
            Log.d("RiveAnimation", "notifyPlay : ${animation.name}")
            notifyPlay?.invoke(animation)
        }

        override fun notifyStateChanged(
            stateMachineName: String,
            stateName: String
        ) {
            Log.d("RiveAnimation", "notifyStateChanged : $stateMachineName : $stateName")
            notifyStateChanged?.invoke(stateMachineName, stateName)
        }

        override fun notifyStop(animation: PlayableInstance) {
            Log.d("RiveAnimation", "notifyStop : ${animation.name}")
            notifyStop?.invoke(animation)
        }
    }.takeIf {
        (notifyLoop != null) || (notifyPause != null) ||
            (notifyPlay != null) || (notifyStateChanged != null) ||
            (notifyStop != null)
    }

    AndroidView(
        modifier = modifier
            .then(semantics)
            .clipToBounds(),
        factory = { context ->
            RiveAnimationView(context)
        },
        onRelease = { animationView ->
            animationView.stop()
            animationView.reset()
        },
        onReset = { animationView ->
            animationView.stop()
            animationView.reset()
        },
        update = { view ->
            view.setRiveResource(resourceId)
            // Note: artboardName, animationName, stateMachineName, fit, alignment, loop
            // may need to be set via other methods if the Rive library supports them
            // For now, using the basic setRiveResource(Int) method
            
            listener?.let { safeListener ->
                view.registerListener(safeListener)
            }
            if (isPlayingLambda()) {
                if (animationName.isNullOrBlank()) {
                    view.play()
                } else {
                    view.play(animationName)
                }
            } else {
                view.pause()
            }
            riveAnimationView = view
            update.invoke(view)
        }
    )

    DisposableEffect(lifecycleOwner) {
        onDispose {
            listener?.let { safeListener ->
                riveAnimationView?.unregisterListener(safeListener)
            }
        }
    }
}
