/**
 * @file GameClearScreen.java
 * @brief ゲームクリア画面のクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.ScreenElement;

import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class GameClearScreen
 * @brief ゲームクリア画面のクラス
 */
public class GameClearScreen extends BaseScreen {

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public GameClearScreen(CustomSpriteBatch spriteBatch){

        super(spriteBatch);

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

    }

    /**
     * @brief 更新処理を行う
     */
    public void update(){

        if(Gdx.input.isTouched()){

            ScreenElement.getElement().setNextScreen(ScreenElement.SCREEN_STATUS);

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

        Texture backGround = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_GAMECLEARBACKGROUND);

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
