/**
 * @file TitleScreen.java
 * @brief タイトル画面のクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.mygdx.game.myactiongame.ScreenElement;
import info.u6182.mygdx.game.myactiongame.animation.title.TitleAnimation;

import info.u6182.api.Utiility;
import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.screeneffectmanager.ScreenEffectManager;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class TitleScreen
 * @brief タイトル画面のクラス
 */
public class TitleScreen extends BaseScreen {

    //! アニメーション
    private TitleAnimation animation;

    //! タイトルのUIの表示インターバル
    private float touchLogoShowInterval;

    //! タイトルのUIの表示インターバルタイム
    private final float SHOW_INTERVALTIME = 30f;

    //! タイトルのUIの表示インターバルタイム
    private final float SHOW_INTERVAL = 0.5f;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public TitleScreen(CustomSpriteBatch spriteBatch){

        //バッチャの設定
        super(spriteBatch);

        //タイトルのUIの表示インターバル
        this.touchLogoShowInterval = SHOW_INTERVALTIME;

    }

    /**
     * @brief スクリーンが表示されたときの処理を行う
     */
    @Override
    public void show(){

        //アセットの読み込み
        assetLoad();

        //ワールドカメラの設定
        setCamera(960f, 540f);

        //アニメーションの生成
        this.animation = new TitleAnimation(this.spriteBatch);

        //BGMの設定と再生
        SoundManager.getSoundManager().bgmPlay(SoundInfo.BGM_STAGE);

    }

    /**
     * @brief 更新処理を行う
     * @param deltaTime 経過時間
     */
    public void update(float deltaTime){

        //遷移エフェクトの更新
        ScreenEffectManager.getScreenEffectManager().rectOrderLined(ScreenElement.SCREEN_MENU);

        //操作の更新
        operation();

        //アニメーションの更新
        this.animation.animation(deltaTime);

        this.touchLogoShowInterval -= SHOW_INTERVAL;

        if(this.touchLogoShowInterval < -SHOW_INTERVALTIME){

            this.touchLogoShowInterval = SHOW_INTERVALTIME;

        }

        //ワールドカメラの更新
        this.camera.update();

    }

    /**
     * @brief 操作の処理を行う
     */
    private void operation(){

        //画面がタッチされた場合
        if(Gdx.input.isTouched() && !ScreenElement.getElement().isEffect()){

            SoundManager.getSoundManager().play(SoundInfo.SOUND_CLICK);

            //遷移エフェクトの開始
            ScreenElement.getElement().setEffect(true);

        }

    }

    /**
     * @brief 描画処理を行う
     * @param deltaTime 経過時間
     */
    @Override
    public void render(float deltaTime){

        //更新
        update(deltaTime);

        this.spriteBatch.begin();

        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        //アニメーションの描画
        this.animation.render(this.spriteBatch, deltaTime);

        Texture title = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_TITLEL);

        float titlePosX = Utiility.getScreenCenterX(title.getWidth());
        float titlePosY = this.camera.viewportHeight - title.getHeight() - 100.0f;

        //タイトルの描画
        this.spriteBatch.draw(title, titlePosX, titlePosY);

        Texture titleTouch = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_TITLETOUCH);

        float titleTouchPosX = Utiility.getScreenCenterX(titleTouch.getWidth());
        float titleTouchPosY = 200f;

        //タイトルのUIの描画
        if(this.touchLogoShowInterval > 0){

            this.spriteBatch.draw(titleTouch, titleTouchPosX, titleTouchPosY);

        }

        this.spriteBatch.end();

        //遷移エフェクトの描画
        ScreenEffectManager.getScreenEffectManager().rectOrderLinedRender(this.camera.combined);

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
