package org.example;

import com.raylib.java.Raylib;
import com.raylib.java.core.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {

    private final Map<Integer, InputState> map = new HashMap<>();

    public InputHandler(){
        init();
    }

    private void init(){
        map.put(Keyboard.KEY_SPACE,InputState.TOGGLE_PAUSE);
        map.put(Keyboard.KEY_R, InputState.RANDOMIZE);
        map.put(Keyboard.KEY_N, InputState.ONE_STEP);
    }

    public InputState update(Raylib raylib){
        return map.getOrDefault(raylib.core.GetKeyPressed(),InputState.NOTHING);
    }

}
