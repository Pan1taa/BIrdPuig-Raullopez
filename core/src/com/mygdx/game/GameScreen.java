        package com.mygdx.game;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Screen;
        import com.badlogic.gdx.audio.Sound;
        import com.badlogic.gdx.graphics.OrthographicCamera;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.math.MathUtils;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.utils.Array;
        import com.badlogic.gdx.utils.ScreenUtils;
        import com.badlogic.gdx.utils.TimeUtils;

        import java.util.Iterator;

        public class GameScreen implements Screen {
            final Dog game;
            OrthographicCamera camera;
            Stage stage;
            Player player;
            boolean dead, coinBounds;
            Array<Laser> obstacles;
            long lastObstacleTime;
            float score;
            Array<Coin> coins;
            long lastCoinTime;
            long lastDiamondTime;
            Coin coin;
            long lastFlySoundTime = 0;
            Laser laser;

            public GameScreen(final Dog gam) {
                this.game = gam;

                // create the camera and the SpriteBatch
                camera = new OrthographicCamera();
                camera.setToOrtho(false, 1280, 720);

                player = new Player();
                player.setManager(game.manager);
                stage = new Stage();
                stage.getViewport().setCamera(camera);
                stage.addActor(player);

                // create the obstacles array and spawn the first obstacle
                obstacles = new Array<Laser>();
                coins = new Array<Coin>(); // inicializar el array de monedas
                spawnObstacle();
                spawnCoin(); // generar la primera moneda
                spawnCoinNextLevel();
                score = 0;
            }

            private void spawnCoin() {
                // calculate the height of the coin randomly
                int coiny = MathUtils.random(120, 490);
                int coinx = 0;
                // create a new coin
                Coin coin = new Coin(coinx,coiny,"coin.png",100, game.manager);
                coin.setX(1290);
                coin.setY(coiny);
                coin.setManager(game.manager);
                coins.add(coin);
                stage.addActor(coin); // Agregar la moneda a la etapa
                lastCoinTime = TimeUtils.nanoTime();


            }

            private void spawnCoinNextLevel() {
                // calculate the height of the coin randomly
                int diamondy = MathUtils.random(120, 490);
                // create a new coin
                Coin coin = new Coin(120,diamondy,"diamond.png",250, game.manager);
                coin.setX(1290);
                coin.setY(diamondy);
                coin.setManager(game.manager);
                coins.add(coin);
                stage.addActor(coin); // Agregar la moneda a la etapa
                lastDiamondTime = TimeUtils.nanoTime();
            }

            private void spawnObstacle() {
                // Calcula la alçada de l'obstacle aleatòriament
                float holey = MathUtils.random(120, 490);
                //Crear un rayo laser
                Laser laser = new Laser();
                laser.setX(1290);
                laser.setY(holey);
                laser.setUpsideDown(true);
                laser.setManager(game.manager);
                obstacles.add(laser);
                stage.addActor(laser);
                lastObstacleTime = TimeUtils.nanoTime();
            }

            @Override
            public void render(float delta) {
                dead = false;
                //Render =====================================================
                // clear the screen with a color
                ScreenUtils.clear(0.3f, 0.8f, 0.8f, 1);
                // tell the camera to update its matrices.
                camera.update();
                // tell the SpriteBatch to render in the
                // coordinate system specified by the camera.
                game.batch.setProjectionMatrix(camera.combined);
                // begin a new batch
                game.batch.begin();
                game.batch.draw(game.manager.get("background.jpg", Texture.class), 0,0);
                game.batch.end();

                // Stage batch: Actors
                stage.getBatch().setProjectionMatrix(camera.combined);
                stage.draw();

                game.batch.begin();
                game.smallFont.draw(game.batch, "Score: " + (int)score, 10,690);
                game.batch.end();

                //Logica ======================================================


                if (player.getBounds().y<120){
                    player.setY(120);
                    player.speedy=0;
                    player.inAir = false;
                }
                if (player.getBounds().y>120) player.inAir=true;
                if (player.getBounds().y>690){
                    player.setY(690);
                }

                // process user input
                if (Gdx.input.getPressure()>0) {
                    player.impulso();
                    long currentTime = TimeUtils.nanoTime();
                    // reproducir el sonido solo si ha pasado más de 1 segundo desde la última vez
                    if (currentTime - lastFlySoundTime > 500000000L) {
                        game.manager.get("fly.wav", Sound.class).play();
                        lastFlySoundTime = currentTime;
                    }
                    player.fly=true;
                }
                else {
                    player.fly=false;
                    game.manager.get("fly.wav", Sound.class).stop();

                }

                stage.act();



                // Comprova si cal generar un obstacle nou
                if (TimeUtils.nanoTime() - lastObstacleTime > (MathUtils.random(2000000000L,4000000000L)))
                    spawnObstacle();
                if (TimeUtils.nanoTime() - lastCoinTime > (MathUtils.random(500000000L,10000000000L)))// generar monedas cada segundo
                    spawnCoin();
                if (TimeUtils.nanoTime() - lastDiamondTime > (MathUtils.random(5000000000L,100000000000L)))// generar monedas cada segundo
                    spawnCoinNextLevel();
                // Comprova si els lasers colisionen amb el jugador
                Iterator<Laser> iter = obstacles.iterator();
                while (iter.hasNext()) {
                    Laser laser = iter.next();
                    if (laser.getBounds().overlaps(player.getBounds())) {
                        dead = true;
                    }
                }

                // check for coin collision
                Iterator<Coin> coinIter = coins.iterator();
                while (coinIter.hasNext()) {
                    Coin coin = coinIter.next();
                    if (coin.getBounds().overlaps(player.getBounds())) {
                        score += coin.getValue();
                        coinIter.remove();
                        coin.remove();
                        //game.manager.get("coin.wav", Sound.class).play();
                    }
                }

                // Treure de l'array els lasers que estan fora de pantalla
                iter = obstacles.iterator();
                while (iter.hasNext()) {
                    Laser laser = iter.next();
                    if (laser.getX() < -64) {
                        obstacles.removeValue(laser, true);
                    }
                }

                // Treure de l'array els coins que estan fora de pantalla
                coinIter = coins.iterator();
                while (coinIter.hasNext()) {
                    Coin coin = coinIter.next();
                    if (coin.getX() < -64) {
                        coins.removeValue(coin, true);
                    }
                }

                if(dead) {
                    game.lastScore = (int)score;
                    if(game.lastScore > game.topScore)
                        game.topScore = game.lastScore;
                    game.setScreen(new GameOverScreen(game));
                    dispose();
                }

            }
            @Override
            public void resize(int width, int height) {
            }
            @Override
            public void show() {
            }
            @Override
            public void hide() {
            }
            @Override
            public void pause() {
            }
            @Override
            public void resume() {
            }
            @Override
            public void dispose() {
                for (Coin coin : coins) {
                    coin.remove();
                }
                coins.clear();
            }
        }

