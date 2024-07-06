/**
 * @file Pause.java
 * @brief ポーズ画面の管理を行うクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import info.u6182.api.Utiility;
import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.input.InputManager;
import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class Pause
 * @brief ポーズ画面の管理を行うクラス
 */
public class Pause {

    //! バッチャ
    private CustomSpriteBatch spriteBatch;

    //! カメラ
    private OrthographicCamera camera;

    //! UIカメラ
    private OrthographicCamera uiCamera;

    //! タッチポジション
    private Vector3 touchPos = new Vector3();

    //! タイトルテクスチャ
    private Texture title;

    //! Yesボタンの座標
    private Vector2 yesButtonPos;

    //! Noボタンの座標
    private Vector2 noButtonPos;

    //! 戻るボタンの座標
    private Vector2 backButtonPos;

    //! Yesボタンの矩形
    private CustomRectangle yesButtonRect;

    //! Noボタンの矩形
    private CustomRectangle noButtonRect;

    //! 戻るボタンの矩形
    private CustomRectangle backButtonRect;

    //! ポーズ番号
    private int pauseNo;

    //! Yesボタンの押下状態
    private final int PUSH_YES = 1;

    //! Noボタンの押下状態
    private final int PUSH_NO = 2;

    //! 押下状態
    private int push;

    //! タッチポインタ
    private int touchPointer;

    //! タッチカウント
    private int touchCount = 1;

    //! スクリーン番号
    private int screenNo;

    //! 次のスクリーン
    private int nextScreen;

    //! タイトルオフセットY
    private final float TITLE_OFFSETY = 300f;

    //! ボタンオフセットX
    private final float BUTTON_OFFSETX = 200f;

    //! ボタンオフセットY
    private final float BUTTON_OFFSETY = 200f;
    
    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public Pause(CustomSpriteBatch spriteBatch){

        this.spriteBatch = spriteBatch;

        //ワールドカメラの初期化
        this.camera = new OrthographicCamera();

        this.camera.setToOrtho(false, 960f, 540f);

        this.uiCamera = new OrthographicCamera();

        //カメラを右上に移動　1:Y軸の反転 2.3:カメラの幅と高さ
        this.uiCamera.setToOrtho(false, 1024f, 768f);

        //ポーズ番号の初期化
        this.pauseNo = GameElement.PAUSE_NOT;

        this.push = 0;

        this.touchPointer = -1;

        //スクリーン番号の初期化
        this.screenNo = ScreenElement.getElement().getNowScreen();

        switch (this.screenNo){

            case ScreenElement.SCREEN_STORY:
            case ScreenElement.SCREEN_ANOTHERSTORY:
            case ScreenElement.SCREEN_DOORHATRED:
            case ScreenElement.SCREEN_OPTION:
            case ScreenElement.SCREEN_STATUS:
            case ScreenElement.SCREEN_CREDIT:

                this.title = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_MENUSELECTBACK);

                this.nextScreen = ScreenElement.SCREEN_MENU;

                break;

            case ScreenElement.SCREEN_GAMEBREAK:

                this.title = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_STAGESELECTBACK);

                this.nextScreen = GameElement.getGameElement().isAnotherStory() ? ScreenElement.SCREEN_ANOTHERSTORY : ScreenElement.SCREEN_STORY;

                break;

        }

        Texture yesButton = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_SELECTBUTTON_YES);

        Texture noButton = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_SELECTBUTTON_NO);

        this.yesButtonPos = new Vector2(Utiility.getScreenCenterX(yesButton.getWidth(), this.camera.viewportWidth) - BUTTON_OFFSETX, TITLE_OFFSETY - BUTTON_OFFSETY);

        this.noButtonPos = new Vector2(Utiility.getScreenCenterX(noButton.getWidth(), this.camera.viewportWidth) + BUTTON_OFFSETX, TITLE_OFFSETY - BUTTON_OFFSETY);

        this.yesButtonRect = new CustomRectangle(this.yesButtonPos.x, this.yesButtonPos.y, yesButton.getWidth(), yesButton.getHeight());

        Texture backButton = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_BACKBUTTON);

        this.backButtonPos = new Vector2(this.uiCamera.viewportWidth - backButton.getWidth(), this.uiCamera.viewportHeight - backButton.getHeight());

        this.backButtonRect = new CustomRectangle(this.backButtonPos.x, this.backButtonPos.y, backButton.getWidth(), backButton.getHeight());

        this.noButtonRect = new CustomRectangle(this.noButtonPos.x, this.noButtonPos.y, noButton.getWidth(), noButton.getHeight());

    }

    /**
     * @brief ポーズ画面の更新を行う
     * @return boolean ポーズ画面が表示されているかどうか
     */
    public boolean update(){

        if(this.pauseNo >= GameElement.PAUSE_RUNNING){

            if(pauseNo == GameElement.PAUSE_RUNNING){

                this.pauseNo++;

                SoundManager.getSoundManager().allPause();

            }

            this.pauseNo = pauseMenu(this.pauseNo);

            if(this.pauseNo == GameElement.PAUSE_NOT){

                SoundManager.getSoundManager().allResume();

            }

            return true;

        }

        if(InputManager.getInputManager().rectTouchDown(this.backButtonRect, this.uiCamera, this.touchPos)){

            this.pauseNo = GameElement.PAUSE_RUNNING;

        }

        return false;

    }

    /**
     * @brief ポーズメニューの操作処理を行う
     * @param pauseNo 現在のポーズ番号
     * @return int 更新されたポーズ番号
     */
    public int pauseMenu(int pauseNo){

        ArrayList<Integer> list = InputManager.getInputManager().getTouchDownList();

        int maxPointer = list.size();

        for(int i = 0;i < maxPointer;i++) {

            int pointer = list.get(i);

            this.touchPos.x = Gdx.input.getX(pointer);
            this.touchPos.y = Gdx.input.getY(pointer);

            this.touchPos = this.camera.unproject(this.touchPos);

            this.touchCount++;

            if (this.yesButtonRect.contains(this.touchPos.x, this.touchPos.y)) {

                this.push = PUSH_YES;

                this.touchPointer = i;

                break;


            }else if (this.noButtonRect.contains(this.touchPos.x, this.touchPos.y)) {

                this.push = PUSH_NO;

                this.touchPointer = i;

                break;

            }

        }

        list = InputManager.getInputManager().getTouchUpList();

        maxPointer = list.size();

        for(int i = 0;i < maxPointer;i++) {

            int pointer = list.get(i);

            this.touchPos.x = Gdx.input.getX(pointer);
            this.touchPos.y = Gdx.input.getY(pointer);

            this.touchPos = this.camera.unproject(this.touchPos);

            this.touchCount--;

            if (this.yesButtonRect.contains(this.touchPos.x, this.touchPos.y) && this.push == PUSH_YES) {

                ScreenElement.getElement().setNextScreen(nextScreen);

                this.push = 0;

                this.touchPointer = -1;

                this.touchCount = 1;

                break;


            }else if (this.noButtonRect.contains(this.touchPos.x, this.touchPos.y) && this.push == PUSH_NO) {

                pauseNo = GameElement.PAUSE_NOT;

                this.push = 0;

                this.touchPointer = -1;

                this.touchCount = 1;

                break;

            }else{

                if(this.touchCount == this.touchPointer){

                    this.push = 0;


                }

            }

        }

        return pauseNo;

    }

    /**
     * @brief ポーズ画面の描画を行う
     */
    public void render(){

        Texture backButton = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_BACKBUTTON);

        this.spriteBatch.begin();

        this.spriteBatch.setProjectionMatrix(this.uiCamera.combined);

        this.spriteBatch.draw(backButton, this.backButtonPos.x, this.backButtonPos.y);

        this.spriteBatch.end();

        if(this.pauseNo == GameElement.PAUSE_NOT){

            return;

        }

        this.spriteBatch.begin();

        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        Texture backGround = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_GAMEBREAKBACKGROUND);

        this.spriteBatch.setColor(1,1,1, 0.3f);

        this.spriteBatch.draw(backGround, 0, 0);

        this.spriteBatch.setColor(1,1,1,1);

        this.spriteBatch.draw(this.title, Utiility.getScreenCenterX(this.title.getWidth(), this.camera.viewportWidth), TITLE_OFFSETY);

        Texture yesButton = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_SELECTBUTTON_YES);

        Texture noButton = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_SELECTBUTTON_NO);

        if(this.push == PUSH_YES){

            this.spriteBatch.setColor(0.5f,0.5f,0.5f,1);

        }

        this.spriteBatch.draw(yesButton, this.yesButtonPos.x, this.yesButtonPos.y);

        this.spriteBatch.setColor(1,1,1,1);

        if(this.push == PUSH_NO){

            this.spriteBatch.setColor(0.5f,0.5f,0.5f,1);

        }

        this.spriteBatch.draw(noButton, this.noButtonPos.x, this.noButtonPos.y);

        this.spriteBatch.setColor(1,1,1,1);

        this.spriteBatch.end();

    }

}
