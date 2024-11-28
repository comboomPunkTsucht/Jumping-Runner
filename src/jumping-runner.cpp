#include <raylib.h>
#include <raymath.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#include <string.h>
#include "nord-color.hpp"

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
        EndDrawing();
    }
    CloseWindow();

    return 0;
}
