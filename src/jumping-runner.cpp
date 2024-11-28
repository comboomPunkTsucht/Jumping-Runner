#include "raylib.h"
#include "nord-color.hpp"
#include <vector>

#define SCREEN_WIDTH 1920
#define SCREEN_HEIGHT 1080
#define DINO_WIDTH_PERCENT 0.05f
#define OBSTACLE_WIDTH_PERCENT 0.03f
#define GROUND_HEIGHT_PERCENT 0.09f

struct Dino {
    Rectangle rect;
    float velocity;
    bool isJumping;
};

struct Obstacle {
    Rectangle rect;
    bool active;
};

struct GameState {
    float scrollingSpeed;
    float obstacleSpawnTimer;
    int score;
    bool gameOver;
    std::vector<Obstacle> obstacles;
    Dino dino;
};

int main(void) {
    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Jumping Runner");
    SetWindowState(FLAG_VSYNC_HINT | FLAG_WINDOW_RESIZABLE | FLAG_WINDOW_HIGHDPI |
                  FLAG_MSAA_4X_HINT | FLAG_INTERLACED_HINT);

    Font gameFont = LoadFont("../assets/fonts/CaskaydiaCoveNerdFontPropo-Regular.ttf");
    SetTargetFPS(60);

    GameState game;

    auto resetDinoPosition = [&]() {
        float groundHeight = GetScreenHeight() * GROUND_HEIGHT_PERCENT;
        game.dino.rect.y = GetScreenHeight() - groundHeight - game.dino.rect.height;
    };

    auto resetGame = [&]() {
        game.scrollingSpeed = GetScreenWidth() * 0.4f;
        game.obstacleSpawnTimer = 0;
        game.score = 0;
        game.gameOver = false;
        game.obstacles.clear();

        float dinoWidth = GetScreenWidth() * DINO_WIDTH_PERCENT;
        float dinoHeight = dinoWidth;

        game.dino = {
            {dinoWidth, 0, dinoWidth, dinoHeight},
            0,
            false
        };
        resetDinoPosition();
    };

    resetGame();

    while (!WindowShouldClose()) {
        if (IsWindowResized() && !game.dino.isJumping) {
            float dinoWidth = GetScreenWidth() * DINO_WIDTH_PERCENT;
            game.dino.rect.width = dinoWidth;
            game.dino.rect.height = dinoWidth;
            resetDinoPosition();
            game.scrollingSpeed = GetScreenWidth() * 0.4f;
        }

        if (game.gameOver && IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
            resetGame();
        }

        if (!game.gameOver) {
            float jumpVelocity = -GetScreenHeight() * 1.1f;
            float gravity = GetScreenHeight() * 2.2f;

            if ((IsKeyPressed(KEY_SPACE) || IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) &&
                !game.dino.isJumping) {
                game.dino.velocity = jumpVelocity;
                game.dino.isJumping = true;
            }

            if (game.dino.isJumping) {
                game.dino.velocity += gravity * GetFrameTime();
                game.dino.rect.y += game.dino.velocity * GetFrameTime();

                float groundHeight = GetScreenHeight() * GROUND_HEIGHT_PERCENT;
                float groundLevel = GetScreenHeight() - groundHeight - game.dino.rect.height;

                if (game.dino.rect.y > groundLevel) {
                    game.dino.rect.y = groundLevel;
                    game.dino.velocity = 0;
                    game.dino.isJumping = false;
                }
            }

            game.obstacleSpawnTimer += GetFrameTime();
            if (game.obstacleSpawnTimer > 2.0f) {
                float obstacleWidth = GetScreenWidth() * OBSTACLE_WIDTH_PERCENT;
                float obstacleHeight = game.dino.rect.height;
                float groundHeight = GetScreenHeight() * GROUND_HEIGHT_PERCENT;

                game.obstacles.push_back({
                    {
                        static_cast<float>(GetScreenWidth()),
                        GetScreenHeight() - groundHeight - obstacleHeight,
                        obstacleWidth,
                        obstacleHeight
                    },
                    true
                });
                game.obstacleSpawnTimer = 0;
            }

            for (auto& obs : game.obstacles) {
                if (obs.active) {
                    obs.rect.x -= game.scrollingSpeed * GetFrameTime();
                    if (CheckCollisionRecs(game.dino.rect, obs.rect)) {
                        game.gameOver = true;
                    }
                }
            }

            game.obstacles.erase(
                std::remove_if(game.obstacles.begin(), game.obstacles.end(),
                             [](const Obstacle& obs) { return obs.rect.x < -obs.rect.width; }),
                game.obstacles.end()
            );

            game.score++;
        }

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
