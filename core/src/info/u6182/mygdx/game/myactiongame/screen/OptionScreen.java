/**
 * @file OptionScreen.java
 * @brief オプション画面のクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.Pause;
import info.u6182.mygdx.game.myactiongame.ScreenElement;

import info.u6182.api.Utiility;
import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.input.InputManager;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class OptionScreen
 * @brief オプション画面のクラス
 */
public class OptionScreen extends BaseScreen {

    //! ポーズ
    private Pause pause;

    //! 左キーの矩形
    private CustomRectangle leftKeyRect;

    //! 右キーの矩形
    private CustomRectangle rightKeyRect;

    //! サウンドメニュー
    private String[] soundMenu = {TextureInfo.TEXTURE_SOUNDON, TextureInfo.TEXTURE_SOUNDOFF};

    //! 選択
    private int select;

    //! スクロール
    private int scroll;

    //! サウンドオンの選択
    private final int SELECT_SOUNDON = 0;

    //! サウンドオフの選択
    private final int SELECT_SOUNDOFF = 1;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public OptionScreen(CustomSpriteBatch spriteBatch){

        super(spriteBatch);

        this.touchPos = new Vector3();

        this.select = !SoundManager.getSoundManager().isSoundOFF() ? SELECT_SOUNDON : SELECT_SOUNDOFF;

        this.scroll = 0;

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

        this.pause = new Pause(this.spriteBatch);

        Texture key = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_KEY);

        Texture soundMenu = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_SOUNDON);

        float px = Utiility.getScreenCenterX(soundMenu.getWidth()) - key.getWidth();
        float py = Utiility.getScreenCenterY(key.getHeight());

        this.leftKeyRect = new CustomRectangle(px, py, key.getWidth(), key.getHeight());

        px = Utiility.getScreenCenterX(soundMenu.getWidth()) + soundMenu.getWidth();

        this.rightKeyRect = new CustomRectangle(px, py, key.getWidth(), key.getHeight());

        SoundManager.getSoundManager().bgmPlay(SoundInfo.BGM_OPTION);

    }

    /**
     * @brief 更新処理を行う
     */
    public void update(){

        //バックボタンでメニューセレクトスクリーンへ遷移
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){

            ScreenElement.getElement().setNextScreen(ScreenElement.SCREEN_MENU);

        }

        if(this.pause.update()){

            return;

        }

        this.scroll++;

        if(InputManager.getInputManager().rectTouchDown(this.leftKeyRect, this.camera, this.touchPos) && this.select != SELECT_SOUNDON){

            SoundManager.getSoundManager().setSoundOFF(false);

            if(!SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_OPTION)){

                SoundManager.getSoundManager().bgmPlay(SoundInfo.BGM_OPTION);

            }

            this.select = SELECT_SOUNDON;

        }

        if(InputManager.getInputManager().rectTouchDown(this.rightKeyRect, this.camera, this.touchPos) && this.select != SELECT_SOUNDOFF){

            SoundManager.getSoundManager().setSoundOFF(true);

            if(SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_OPTION)){

                SoundManager.getSoundManager().allStop();

            }

            this.select = SELECT_SOUNDOFF;

        }

    }

    /**
     * @brief 描画処理を行う
     * @param deltaTime 経過時間
     */
    @Override
    public void render(float deltaTime){

        update();

        this.spriteBatch.begin();

        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        Texture backGround = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_ANOTHERSTAGE1BACKGROUND);

        int sw = (int)this.camera.viewportWidth;
        int sh = (int)this.camera.viewportHeight;

        int wn = backGround.getWidth();

        int hn = backGround.getHeight();

        for(float y = ((int)-0 % hn) - hn;y < sh;y += hn){

            for(float x = (-this.scroll % wn) - wn;x < sw;x += wn){

                //背景の描画
                this.spriteBatch.draw(backGround, x, y);

            }

        }

        Texture soundMenu = TextureManager.getTextureManager().getTexture(this.soundMenu[this.select]);

        float px = Utiility.getScreenCenterX(soundMenu.getWidth());
        float py = Utiility.getScreenCenterY(soundMenu.getHeight());

        this.spriteBatch.draw(soundMenu, px, py);

        Texture key = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_KEY);

        if(this.select == SELECT_SOUNDON){

            this.spriteBatch.setColor(1,1,1,0.5f);

        }

        this.spriteBatch.draw(key, this.leftKeyRect.x, this.leftKeyRect.y, true, false);

        this.spriteBatch.setColor(1,1,1,1);

        if(this.select == SELECT_SOUNDOFF){

            this.spriteBatch.setColor(1,1,1,0.5f);

        }

        this.spriteBatch.draw(key, this.rightKeyRect.x, this.rightKeyRect.y);

        this.spriteBatch.setColor(1,1,1,1);

        this.spriteBatch.end();

        //ポーズの描画
        this.pause.render();

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
