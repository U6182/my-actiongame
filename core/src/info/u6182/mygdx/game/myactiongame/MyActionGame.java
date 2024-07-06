/**
 * @file MyActionGame.java
 * @brief ゲーム全体の管理を行うクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;

import info.u6182.api.input.InputManager;
import info.u6182.mygdx.game.myactiongame.screen.CreditScreen;
import info.u6182.mygdx.game.myactiongame.screen.DoorHatredScreen;
import info.u6182.mygdx.game.myactiongame.screen.FameScoreScreen;
import info.u6182.mygdx.game.myactiongame.screen.GameBreakScreen;
import info.u6182.mygdx.game.myactiongame.screen.GameOverScreen;
import info.u6182.mygdx.game.myactiongame.screen.GameScreen;
import info.u6182.mygdx.game.myactiongame.screen.MenuScreen;
import info.u6182.mygdx.game.myactiongame.screen.OptionScreen;
import info.u6182.mygdx.game.myactiongame.screen.StageSelectScreen;
import info.u6182.mygdx.game.myactiongame.screen.TitleScreen;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.fontmanager.FontManager;
import info.u6182.api.screeneffectmanager.ScreenEffectManager;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class MyActionGame
 * @brief ゲーム全体の管理を行うクラス
 */
public class MyActionGame extends Game {

	//! フレームレート検査
	private FPSLogger fpsLogger;

	//! バッチャ
	private CustomSpriteBatch spriteBatch;

	//! スクリーン変更フラグ
	private boolean bScreenChange;

	/**
	 * @brief ゲームの初期化を行う
	 */
	@Override
	public void create () {

		//test();

		//設定や暗号鍵の読み込み
		GameElement.getGameElement().Load();

		SoundManager.getSoundManager().setSoundOFF(GameElement.getGameElement().getSettings().bSoundOFF);

		this.fpsLogger = new FPSLogger();

		this.spriteBatch = new CustomSpriteBatch();

		this.bScreenChange = false;

		FontManager.getFontmanager().setSpriteBatch(this.spriteBatch);

		//バックボタンのキャッチを有効にする
		Gdx.input.setCatchKey(Input.Keys.BACK, true);

		//インプットの生成
		InputManager.getInputManager().setInputGestureProcessor(5);

		//最初のスクリーンの決定
		ScreenElement.getElement().setScreen(ScreenElement.SCREEN_TITLE, ScreenElement.SCREEN_TITLE);

		//最初のスクリーンをタイトルスクリーンに設定
		setScreen(new TitleScreen(this.spriteBatch));

	}

	/**
	 * @brief テスト用のメソッド（テスト用のファイルを削除する）
	 */
	void test(){

		FileHandle[] files = Gdx.files.local("Save/").list();

		for(FileHandle file : files){

			String test = file.path();

			file.delete();

		}

	}

	/**
	 * @brief 初期化を行う
	 */
	public void initialize(){

		//スクリーンエフェクトの初期化
		ScreenEffectManager.getScreenEffectManager().initialize();
		ScreenElement.getElement().setEffect(false);


	}

	/**
	 * @brief ゲームのレンダリングを行う
	 */
	@Override
	public void render () {

		//スクリーンが変更された場合初期化
		if(this.bScreenChange){

			initialize();

			this.bScreenChange = false;

		}

		Gdx.gl.glClearColor(0, 0, 0, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//スクリーンの更新.描画
		super.render();

		//インプットのリセット
		InputManager.getInputManager().reset();

		//this.fpsLogger.log();

		//スクリーン設定が同じ場合
		if(ScreenElement.getElement().isSameScreen()){

			return;

		}

		int screenNo = -1;

		//スクリーンが変更された場合各スクリーンへ遷移
		switch (ScreenElement.getElement().getNextScreen()){

			case ScreenElement.SCREEN_TITLE:

				ScreenElement.getElement().setNowScreen(ScreenElement.getElement().getNextScreen());

				setScreen(new TitleScreen(this.spriteBatch));

				break;

			case ScreenElement.SCREEN_MENU:

				ScreenElement.getElement().setNowScreen(ScreenElement.getElement().getNextScreen());

				setScreen(new MenuScreen(this.spriteBatch));

				break;

			case ScreenElement.SCREEN_STORY:
			case ScreenElement.SCREEN_ANOTHERSTORY:

				ScreenElement.getElement().setNowScreen(ScreenElement.getElement().getNextScreen());

				setScreen(new StageSelectScreen(this.spriteBatch));

				break;

			case ScreenElement.SCREEN_DOORHATRED:

				ScreenElement.getElement().setNowScreen(ScreenElement.getElement().getNextScreen());

				setScreen(new DoorHatredScreen(this.spriteBatch));

				break;

			case ScreenElement.SCREEN_GAMEBREAK:

				ScreenElement.getElement().setNowScreen(ScreenElement.getElement().getNextScreen());

				setScreen(new GameBreakScreen(this.spriteBatch));

				break;

			case ScreenElement.SCREEN_GAME:

				GameBreakScreen gameBreakScreen = null;

				if(this.screen instanceof GameBreakScreen){

					gameBreakScreen = (GameBreakScreen)this.screen;

				}

				GameScreen gameScreen = gameBreakScreen.getGameScreen();

				ScreenElement.getElement().setNowScreen(ScreenElement.getElement().getNextScreen());

				this.screen = gameScreen;

				this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

				//setScreen(new GameScreen(this.spriteBatch));

				break;

			case ScreenElement.SCREEN_GAMEOVER:

				ScreenElement.getElement().setNowScreen(ScreenElement.getElement().getNextScreen());

				setScreen(new GameOverScreen(this.spriteBatch));

				break;

			case ScreenElement.SCREEN_OPTION:

				ScreenElement.getElement().setNowScreen(ScreenElement.getElement().getNextScreen());

				setScreen(new OptionScreen(this.spriteBatch));

				break;

			case ScreenElement.SCREEN_STATUS:

				screenNo = ScreenElement.getElement().getNowScreen();

				ScreenElement.getElement().setNowScreen(ScreenElement.getElement().getNextScreen());

				setScreen(new FameScoreScreen(this.spriteBatch, screenNo));

				break;

			case ScreenElement.SCREEN_CREDIT:

				screenNo = ScreenElement.getElement().getNowScreen();

				ScreenElement.getElement().setNowScreen(ScreenElement.getElement().getNextScreen());

				setScreen(new CreditScreen(this.spriteBatch, screenNo));

				break;

		}

		//スクリーンの変更
		this.bScreenChange = true;

	}

	/**
	 * @brief リソースの解放を行う
	 */
	public void release(){

		//テクスチャの解放
		TextureManager.getTextureManager().release();

		//エフェクトの解放
		EffectManager.getEffectManager().release();

		//サウンドの解放
		SoundManager.getSoundManager().release();

		//フォントの解放
		FontManager.getFontmanager().release();

		//スクリーンエフェクトの解放
		ScreenEffectManager.getScreenEffectManager().release();

	}

	/**
	 * @brief ゲームの一時停止時に処理を行う
	 */
	@Override
	public void pause(){

		super.pause();

		SecretKey key = GameElement.getGameElement().getKey();

		IvParameterSpec iv = GameElement.getGameElement().getIv();

		//暗号鍵の保存
		Save.getSave().keySave(key, iv);

		GameElement.getGameElement().getSettings().bSoundOFF = SoundManager.getSoundManager().isSoundOFF();

		Settings settings = GameElement.getGameElement().getSettings();

		//設定の保存
		Save.getSave().settingsSave(key, iv, settings);

	}

	/**
	 * @brief ゲームの終了時にリソースの解放を行う
	 */
	@Override
	public void dispose(){

		//解放
		release();

	}

}
