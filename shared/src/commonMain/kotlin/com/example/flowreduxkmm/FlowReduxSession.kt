package com.example.flowreduxkmm

import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import com.freeletics.flowredux.dsl.OverrideState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal sealed class FlowReduxSessionAction {
    object Resume : FlowReduxSessionAction()
    object Suspend : FlowReduxSessionAction()
}

sealed class FlowReduxSessionState {
    object Suspended : FlowReduxSessionState()
    object Running : FlowReduxSessionState()
}

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
internal class SessionStateMachine(
) : FlowReduxStateMachine<FlowReduxSessionState, FlowReduxSessionAction>(initialState = FlowReduxSessionState.Suspended) {

    init {
        spec {
            inState<FlowReduxSessionState.Running> {
                onEnterEffect {
                    println("onEnterEffect: Running")
                }
                on<FlowReduxSessionAction.Suspend> { _, _ ->
                    OverrideState(FlowReduxSessionState.Suspended)
                }
            }

            inState<FlowReduxSessionState.Suspended> {
                onEnterEffect {
                    println("onEnterEffect: Suspended")
                }
                on<FlowReduxSessionAction.Resume> { _, _ ->
                    OverrideState(FlowReduxSessionState.Running)
                }
            }
        }
    }
}

class FlowReduxSession {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val sessionStateMutableStateFlow = MutableStateFlow<FlowReduxSessionState>(FlowReduxSessionState.Suspended)
    val sessionStateFlow = sessionStateMutableStateFlow.asStateFlow()
    private val stateMachine = SessionStateMachine()

    init {
        coroutineScope.launch {
            stateMachine.state.collect {
                sessionStateMutableStateFlow.value = it
            }
        }
    }

    fun resume() {
        println("resume")
        coroutineScope.launch {
            stateMachine.dispatch(FlowReduxSessionAction.Resume)
        }
    }

    fun suspend() {
        println("suspend")
        coroutineScope.launch {
            stateMachine.dispatch(FlowReduxSessionAction.Suspend)
        }
    }
}