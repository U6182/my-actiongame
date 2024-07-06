/**
 * @file GameOverScreen.java
 * @brief ゲームオーバー画面のクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.ScreenElement;

import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class GameOverScreen
 * @brief ゲームオーバー画面のクラス
 */
public class GameOverScreen extends BaseScreen {

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public GameOverScreen(CustomSpriteBatch spriteBatch){

        super(spriteBatch);

        this.bAnotherStory = GameElement.getGameElement().isAnotherStory();

    }

    /**
     * @brief スクリーンが表示されたときの処理を行う
     */
    @Override
    public void show(){

        //アセットの読み込み
        assetLoad();

        //ワールドカメラの設定
        setCamera(1024f, 768f);

        SoundManager.getSoundManager().bgmPlay(SoundInfo.BGM_GAMEOVER, false);

    }

    /**
     * @brief 更新処理を行う
     */
    public void update(){

        if(SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_GAMEOVER)){

            return;

        }

        if(Gdx.input.isTouched()){

            int nextScreen = this.bAnotherStory ? ScreenElement.SCREEN_MENU : ScreenElement.SCREEN_STATUS;

            ScreenElement.getElement().setNextScreen(nextScreen);

        }

    }

    /**
     * @brief 描画処理を行う
     * @param deltaTime 経過時間
     */
    @Override
    public void render(float deltaTime){

        //更新
        update();

        this.spriteBatch.begin();

        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        Texture backGround = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_GAMEOVERBACKGROUND);

        //背景の描画
        this.spriteBatch.draw(backGround, 0, 0);

        this.spriteBatch.end();

    }

    /**
     * @brief 解放処理を行う
     */
    @Override
    public void release(){

        //テクスチャの解放
        TextureManager.getTextureManager().release();

        //サウンドの解放
        SoundManager.getSoundManager().release();

    }

    /**
     * @brief スクリーンが隠れたときの処理を行う
     */
    @Override
    public void hide(){

        //インスタンスの解放
        release();

    }

    /**
     * @brief ポーズ処理を行う
     */
    @Override
    public void pause(){

        //全てのサウンドの一時停止
        SoundManager.getSoundManager().allPause();

    }

    /**
     * @brief 再開処理を行う
     */
    @Override
    public void resume(){

        //全てのサウンドの再開
        SoundManager.getSoundManager().allResume();

    }

}
