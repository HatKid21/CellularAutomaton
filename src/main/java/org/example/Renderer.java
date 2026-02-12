package org.example;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;

public class Renderer {

    private final Raylib r;
    private final int CELL_SIZE;
    private boolean showGeneration = false;

    public Renderer(Raylib raylib, int cellSize){
        this.r = raylib;
        this.CELL_SIZE = cellSize;
    }

    public Renderer(Raylib raylib){
        this(raylib,10);
    }

    public void showGeneration(){
        showGeneration = true;
    }

    public void disableGeneration(){
        showGeneration = false;
    }

    public void beginFrame(){
        r.core.BeginDrawing();
        r.core.ClearBackground(Color.WHITE);
    }

    public void drawGrid(Cellular cellular){
        int height = cellular.getHeight();
        int width = cellular.getWidth();
        for (int i = 0; i < height + 1; i++) {
            r.shapes.DrawLine(0,i* CELL_SIZE,width* CELL_SIZE, i* CELL_SIZE, Color.BLACK);
        }
        for (int i = 0; i < width + 1; i++){
            r.shapes.DrawLine(i* CELL_SIZE,0, i* CELL_SIZE,height* CELL_SIZE,Color.BLACK);
        }
        boolean[][] grid = cellular.getGrid();
        for (int w = 0; w < width; w++){
            for (int h = 0; h < height; h++){
                if (grid[w][h]){
                    r.shapes.DrawRectangle(w* CELL_SIZE,h* CELL_SIZE,CELL_SIZE-1,CELL_SIZE-1,Color.GREEN);
                }
            }
        }
        if (showGeneration) {
            drawGeneration(cellular);
        }
    }

    private void drawGeneration(Cellular cellular){
        r.text.DrawText("Generation:" + cellular.getGeneration(),0,r.core.GetScreenHeight()-20,20,Color.PINK);
    }

    public void endFrame(){
        r.core.EndDrawing();
    }

}
