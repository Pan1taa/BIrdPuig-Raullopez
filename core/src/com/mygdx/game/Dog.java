package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;


import com.badlogic.gdx.Game;

public class Dog extends Game {

	SpriteBatch batch;
	BitmapFont smallFont, bigFont;
	AssetManager manager;
	int topScore;
	int lastScore;

	public void create() {
		batch = new SpriteBatch();
		// Create bitmap fonts from TrueType font
		FreeTypeFontGenerator generator = new
				FreeTypeFontGenerator(Gdx.files.internal("8bitOperatorPlus-Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new
				FreeTypeFontGenerator.FreeTypeFontParameter();
		params.size = 22;
		params.borderWidth = 2;
		params.borderColor = Color.BLACK;
		params.color = Color.WHITE;
		smallFont = generator.generateFont(params); // font size 22 pixels
		params.size = 50;
		params.borderWidth = 5;
		bigFont = generator.generateFont(params); // font size 50 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!

		manager = new AssetManager();
		manager.load("rayo_laser.png", Texture.class);
		manager.load("fly.wav", Sound.class);
		manager.load("background.jpg", Texture.class);
		manager.load("perronovolando.png", Texture.class);
		manager.load("perrovolando.png", Texture.class);
		manager.load("perrosuelo1.png", Texture.class);
		manager.load("perrosuelo2.png", Texture.class);
		manager.load("perrosuelo3.png", Texture.class);
		manager.load("diamond.png", Texture.class);
		manager.load("coin.png", Texture.class);
		manager.finishLoading();

		this.setScreen(new MainMenuScreen(this));
		topScore = 0;
		lastScore = 0;

	}
	public void render() {
		super.render(); // important!
	}
	public void dispose() {
	}

}
