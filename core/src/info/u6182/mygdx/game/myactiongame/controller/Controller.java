/**
 * @file Controller.java
 * @brief ゲームのコントローラーを管理するクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.camera.Camera;
import info.u6182.api.input.InputManager;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class Controller
 * @brief ゲーム内の操作を管理するコントローラークラス
 */
public class Controller {

    //! ジョイスティックコントローラ
    private JoyStickController joyStickController;

    //! 召喚ボタンのテクスチャ
    private Texture callButton;

    //! 自殺ボタンのテクスチャ
    private Texture deadButton;

    //! ジャンプボタンのテクスチャ
    private Texture jumpButton;

    //! 召喚のボタン円
    private Circle callButtonCircle;

    //! ジャンプボタンの円
    private Circle jampButtonCircle;

    //! アナザーストーリーフラグ
    private boolean bAnotherStory;

    //! 召喚ボタンフラグ
    public static boolean bCallButton;

    public static boolean bDeadButton;

    //! ジャンプボタンフラグ
    public static boolean bJumpButton;

    //! 左フリックフラグ
    public static boolean bLeftFling;

    //! 右フリックフラグ
    public static boolean bRightFling;

    /**
     * @brief コンストラクタ
     * @param bAnotherStory アナザーストーリーフラグ
     * @param uiViewWidth UIの幅
     * @param uiViewHeight UIの高さ
     */
    public Controller(boolean bAnotherStory, float uiViewWidth, float uiViewHeight){

        //ジョイスティックコントローラの生成
        this.joyStickController = new JoyStickController();

        //召喚ボタンのテクスチャの設定
        if(bAnotherStory){

            this.callButton = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_CALLBUTTON);

        }else{

            this.callButton = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_FIREBALLBUTTON);

        }

        //自殺ボタンのテクスチャの設定
        this.deadButton = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_DEADBUTTON);

        //ジャンプボタンのテクスチャの設定
        this.jumpButton = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_JUMPBUTTON);

        //召喚のボタン円の初期化
        this.callButtonCircle = new Circle(uiViewWidth - 128f, 96f, 32f);

        //ジャンプボタンの円の初期化
        this.jampButtonCircle = new Circle(uiViewWidth - 64f, 32f, 32f);

        //アナザーストーリーフラグの初期化
        this.bAnotherStory = bAnotherStory;

        resset();

    }

    /**
     * @brief コントローラの更新処理を行う
     * @param uiCamera UIカメラ
     * @param touchPos タッチ位置
     */
    public void update(Camera uiCamera, Vector3 touchPos){

        //ジョイスティックコントローラの更新
        joyStickController.update(uiCamera, touchPos);

        operation(uiCamera, touchPos);

    }

    /**
     * @brief ユーザー操作の処理を行う
     * @param uiCamera UIカメラ
     * @param touchPos タッチ位置
     */
    public void operation(Camera uiCamera, Vector3 touchPos){

        ArrayList<Integer> list = InputManager.getInputManager().getTouchDownList();

        int maxPointer = list.size();

        for(int i = 0;i < maxPointer;i++){

            int pointer = list.get(i);

            touchPos.x = Gdx.input.getX(pointer);
            touchPos.y = Gdx.input.getY(pointer);

            touchPos = uiCamera.unproject(touchPos);

            if(this.callButtonCircle.contains(touchPos.x, touchPos.y)){

                bCallButton = true;

                break;

            }

            if(this.jampButtonCircle.contains(touchPos.x, touchPos.y)){

                bDeadButton = true;

                bJumpButton = true;

                break;

            }

        }

        list = InputManager.getInputManager().getTouchUpList();

        maxPointer = list.size();

        for(int i = 0;i < maxPointer;i++){

            int pointer = list.get(i);

            touchPos.x = Gdx.input.getX(pointer);
            touchPos.y = Gdx.input.getY(pointer);

            touchPos = uiCamera.unproject(touchPos);

            if(touchPos.x > this.callButtonCircle.x - this.callButtonCircle.radius){

                if(InputManager.getInputManager().isLeftFling(-500f)){

                    bLeftFling = true;

                }

                if(InputManager.getInputManager().isRightFling(500f)){

                    bRightFling = true;

                }

            }

        }

    }

    /**
     * @brief コントローラのリセットを行う
     */
    public void resset(){

        bCallButton = false;

        bDeadButton = false;

        bJumpButton = false;

        bLeftFling = false;

        bRightFling = false;

    }

    /**
     * @brief コントローラの描画処理を行う
     * @param spriteBatch スプライトバッチ
     * @param bPlayerChange プレイヤー変更フラグ
     */
    public void render(CustomSpriteBatch spriteBatch, boolean bPlayerChange){

        //ジョイスティックの描画
        this.joyStickController.render(spriteBatch);

        spriteBatch.setColor(1,1,1,0.3f);

        //召喚ボタンの描画
        spriteBatch.draw(this.callButton, this.callButtonCircle.x - 32f, this.callButtonCircle.y - 32f);

        if(bPlayerChange){

            //自殺ボタンの描画
            spriteBatch.draw(this.deadButton, this.jampButtonCircle.x - 32f, this.jampButtonCircle.y - 32f);

        }else{

            //ジャンプボタンの描画
            spriteBatch.draw(this.jumpButton, this.jampButtonCircle.x - 32f, this.jampButtonCircle.y - 32f);

        }

        spriteBatch.setColor(1,1,1,1);

    }

}
