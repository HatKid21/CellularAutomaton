package org.example;

import com.raylib.java.Raylib;
import com.raylib.java.core.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {

    private final Map<Integer, InputState> map = new HashMap<>();

    private boolean speedUpHeld;
    private boolean speedDownHeld;

    public InputHandler(){
        init();
    }

    private void init(){
        map.put(Keyboard.KEY_SPACE,InputState.TOGGLE_PAUSE);
        map.put(Keyboard.KEY_R, InputState.RANDOMIZE);
        map.put(Keyboard.KEY_N, InputState.ONE_STEP);
        map.put(Keyboard.KEY_C, InputState.CLEAR);
        map.put(Keyboard.KEY_Q, InputState.QUIT);
        map.put(Keyboard.KEY_W, InputState.SPEED_UP);
        map.put(Keyboard.KEY_S, InputState.SPEED_DOWN);
    }

    public InputState update(Raylib raylib){
        speedDownHeld = raylib.core.IsKeyDown(Keyboard.KEY_S);
        speedUpHeld = raylib.core.IsKeyDown(Keyboard.KEY_W);
        return map.getOrDefault(raylib.core.GetKeyPressed(), null);
    }

    public boolean isSpeedDownHeld() {
        return speedDownHeld;
    }

    public boolean isSpeedUpHeld() {
        return speedUpHeld;
    }
}
