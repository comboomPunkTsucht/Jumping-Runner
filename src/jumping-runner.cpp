#include <raylib.h>
#include <raymath.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#include <string.h>
#include "nord-color.hpp"
#include <vector>

#define SREEN_WIDTH     1920
#define SREEN_HEIGHT    1080
#define TARGET_FPS      60

int main(void)
{
    InitWindow(SREEN_WIDTH, SREEN_HEIGHT, "Jumping Runner");
    SetTargetFPS(TARGET_FPS);

    while (!WindowShouldClose())
    {
        BeginDrawing();
        ClearBackground(GetColor(NORD_BACKGROUND));

        float groundHeight = GetScreenHeight() * GROUND_HEIGHT_PERCENT;
        DrawRectangle(0, GetScreenHeight() - groundHeight,
                     GetScreenWidth(), groundHeight, GetColor(NORD_FOREGROUND));

        DrawRectangleRec(game.dino.rect, GetColor(NORD_WHITE));

        for (const auto& obs : game.obstacles) {
            if (obs.active) {
                DrawRectangleRec(obs.rect, GetColor(NORD_RED));
            }
        }

        DrawTextEx(gameFont, TextFormat("Score: %d", game.score/60),
                  {10, 10}, 20, 2, GetColor(NORD_WHITE));

        if (game.gameOver) {
            const char* gameOverText = "Game Over!\nClick to restart";
            Vector2 textSize = MeasureTextEx(gameFont, gameOverText, 40, 2);
            DrawTextEx(gameFont, gameOverText,
                      {GetScreenWidth()/2 - textSize.x/2,
                       GetScreenHeight()/2 - textSize.y/2},
                      40, 2, GetColor(NORD_RED));
        }

        EndDrawing();
    }

    UnloadFont(gameFont);
    CloseWindow();
    return 0;
}
