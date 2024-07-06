/**
 * @file BaseScreen.java
 * @brief ゲームスクリーンの基本クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.camera.Camera;
import info.u6182.api.effectmanager.EffectInfo;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.ScreenElement;

import info.u6182.api.PrimitiveRenderer;
import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class BaseScreen
 * @brief ゲームスクリーンの基本クラス
 */
public abstract class BaseScreen implements Screen {

    //! プリミティブレンダラー
    protected PrimitiveRenderer renderer = new PrimitiveRenderer();

    //! バッチャ
    protected CustomSpriteBatch spriteBatch;

    //! ワールドカメラ
    protected Camera camera;

    //! ビューポート
    protected Viewport viewport;

    //! タッチ座標
    protected Vector3 touchPos;

    //! アナザーストーリーフラグ
    protected boolean bAnotherStory;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public BaseScreen(CustomSpriteBatch spriteBatch){

        this.spriteBatch = spriteBatch;

        this.touchPos = new Vector3();

        this.bAnotherStory = false;

    }

    /**
     * @brief 画面リサイズ処理を行う
     */
    public void screenResize(){
        ;
    }

    /**
     * @brief 画面サイズ変更時の処理を行う
     * @param width 画面の幅
     * @param height 画面の高さ
     */
    @Override
    public void resize(int width, int height){

        //画面サイズが変わった場合再設定
        this.viewport.update(width, height);

        //カメラをビューポートの中心に設定
        this.camera.position.set(this.camera.viewportWidth / 2,this.camera.viewportHeight / 2,0);

        screenResize();

        //ビューポートサイズを伝える
        GameElement.getGameElement().setViewPortSize(this.camera.viewportWidth, this.camera.viewportHeight);

    }

    /**
     * @brief アセットの読み込み処理を行う
     */
    public void assetLoad(){

        int nowScreen = ScreenElement.getElement().getNowScreen();

        TextureInfo textureInfo = new TextureInfo();

        EffectInfo effectInfo = new EffectInfo();

        SoundInfo soundInfo = new SoundInfo();

        switch (nowScreen){

            case ScreenElement.SCREEN_TITLE:

                textureInfo.titleLoad();

                soundInfo.titleLoad();

                break;

            case ScreenElement.SCREEN_MENU:

                textureInfo.menuLoad();

                soundInfo.menuLoad();

                break;

            case ScreenElement.SCREEN_STORY:
            case ScreenElement.SCREEN_ANOTHERSTORY:

                textureInfo.stageSelectLoad();

                soundInfo.stageSelectLoad();

                break;

            case ScreenElement.SCREEN_DOORHATRED:

                textureInfo.doorHatredLoad();

                effectInfo.doorHatredLoad();

                soundInfo.doorHatredLoad();

                break;

            case ScreenElement.SCREEN_GAMEBREAK:

                textureInfo.gameBreakLoad();

                textureInfo.gameLoad();

                effectInfo.gameLoad();

                soundInfo.gameLoad();

                break;

            case ScreenElement.SCREEN_GAME:

                break;

            case ScreenElement.SCREEN_GAMEOVER:

                textureInfo.gameOverLoad();

                soundInfo.gameOverLoad();

                break;

            case ScreenElement.SCREEN_OPTION:

                textureInfo.optionLoad();

                soundInfo.optionLoad();

                break;

            case ScreenElement.SCREEN_STATUS:

                textureInfo.fameScoreLoad();

                soundInfo.fameScoreLoad();

                break;

            case ScreenElement.SCREEN_CREDIT:

                textureInfo.creditLoad();

                break;

        }

        //スクリーンで使用するテクスチャの読み込み
        TextureManager.getTextureManager().load(textureInfo);

        //スクリーンで使用するエフェクトの読み込み
        EffectManager.getEffectManager().load(this.spriteBatch, effectInfo, 20);

        //スクリーンで使用するサウンドの読み込み
        SoundManager.getSoundManager().load(soundInfo);

    }

    /**
     * @brief カメラの設定を行う
     * @param viewWidth ビューの幅
     * @param viewHeight ビューの高さ
     */
    public void setCamera(float viewWidth, float viewHeight){

        //ワールドカメラの初期化
        this.camera = new Camera();

        //論理空間を設定
        //実際のスクリーンサイズといなるため画面が潰れる論理空間と合わせる
        this.viewport = new StretchViewport(viewWidth, viewHeight, this.camera);

        this.viewport.apply();

        //カメラをビューポートの中心に設定
        this.camera.position.set(this.camera.viewportWidth / 2,this.camera.viewportHeight / 2,0);

        //カメラのサイド座標を設定
        this.camera.setPosSide();

        //ビューポートサイズを伝える
        GameElement.getGameElement().setViewPortSize(this.camera.viewportWidth, this.camera.viewportHeight);

    }

    /**
     * @brief テスト用のカメラ設定を行う
     * @param viewWidth ビューの幅
     * @param viewHeight ビューの高さ
     */
    void TestsetCamera(float viewWidth, float viewHeight){

        //論理空間を設定
        this.camera = new Camera();
        //実際のスクリーンサイズといなるため画面が潰れる論理空間と合わせる
        this.viewport = new StretchViewport(viewWidth, viewHeight, this.camera);

        this.viewport.apply();

        //カメラをビューポートの中心に設定
        this.camera.position.set(this.camera.viewportWidth / 2,this.camera.viewportHeight / 2,0);

        //カメラのサイド座標を設定
        this.camera.setPosSide();

        //ビューポートサイズを伝える
        GameElement.getGameElement().setViewPortSize(this.camera.viewportWidth, this.camera.viewportHeight);

    }

    /**
     * @brief 解放処理を行う
     */
    public abstract void release();

    /**
     * @brief 画面破棄時の処理を行う
     */
    @Override
    public void dispose(){

        //アプリケーション終了時に解放
        release();

    }

}
