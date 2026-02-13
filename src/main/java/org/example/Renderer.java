package org.example;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;

public class Renderer {

    private final Raylib r;
    private final int CELL_SIZE;
    private final int X_START = 20;
    private final int Y_START = 20;

    public Renderer(Raylib raylib, int cellSize){
        this.r = raylib;
        this.CELL_SIZE = cellSize;
    }

    public Renderer(Raylib raylib){
        this(raylib,10);
    }

    public void beginFrame(){
        r.core.BeginDrawing();
        r.core.ClearBackground(Color.WHITE);
    }

    public void drawGrid(Cellular cellular){
        int height = cellular.getHeight();
        int width = cellular.getWidth();
        for (int i = 0; i < height + 1; i++) {
            int x = adjustGlobalPositionX(width*CELL_SIZE);
            int y = adjustGlobalPositionY(i* CELL_SIZE);
            r.shapes.DrawLine(X_START,y,x, y, Color.BLACK);
        }
        for (int i = 0; i < width + 1; i++){
            int x = adjustGlobalPositionX(i*CELL_SIZE);
            int y = adjustGlobalPositionY(height * CELL_SIZE);
            r.shapes.DrawLine(x,Y_START, x,y,Color.BLACK);
        }
        boolean[][] grid = cellular.getGrid();
        for (int w = 0; w < width; w++){
            for (int h = 0; h < height; h++){
                if (grid[w][h]){
                    int x = adjustGlobalPositionX(w * CELL_SIZE);
                    int y = adjustGlobalPositionY(h * CELL_SIZE);
                    r.shapes.DrawRectangle(x,y,CELL_SIZE-1,CELL_SIZE-1,Color.GREEN);
                }
            }
        }
    }

    private int adjustGlobalPositionX(int x){
       return X_START + x;
    }

    private int adjustGlobalPositionY(int y){
        return Y_START + y;
    }

    public void drawPaused(int width){
        int x =  width - r.text.MeasureText("Paused",20);
        r.text.DrawText("Paused",x,0,20,Color.PINK);
    }

    public void drawGeneration(Cellular cellular){
        r.text.DrawText("Generation:" + cellular.getGeneration(),0,r.core.GetScreenHeight()-20,20,Color.PINK);
    }

    public void endFrame(){
        r.core.EndDrawing();
    }

    public void drawSpeed(double speed){
        r.text.DrawText("Speed: " + speed, 0,0,20,Color.PINK);
    }

}
