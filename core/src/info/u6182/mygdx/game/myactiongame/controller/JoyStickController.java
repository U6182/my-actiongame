/**
 * @file JoyStickController.java
 * @brief ジョイスティックの操作を管理するクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.controller.JoyStick;
import info.u6182.api.input.InputManager;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class JoyStickController
 * @brief ジョイスティックの操作を管理するクラス
 */
public class JoyStickController {

    //! actuatorの座標
    public static Vector2 actuator = new Vector2();

    //! ジョイスティック
    private JoyStick joyStick;

    /**
     * @brief コンストラクタ
     */
    public JoyStickController(){

        Texture joyStick = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_JOYSTICK);

        Texture stick = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_STICK);

        //ジョイスティックの生成
        this.joyStick = new JoyStick(joyStick, stick, 32 * 2, 32 * 2);

    }

    /**
     * @brief ジョイスティックの更新処理を行う
     * @param uiCamera UIカメラ
     * @param touchPos タッチ位置
     */
    public void update(OrthographicCamera uiCamera, Vector3 touchPos){

        joyStickOperation(uiCamera, touchPos);

        joyStick.update();

        actuator.set(this.joyStick.getActuatorX(), this.joyStick.getActuatorY());

    }

    /**
     * @brief ジョイスティックの操作処理を行う
     * @param uiCamera UIカメラ
     * @param touchPos タッチ位置
     */
    public void joyStickOperation(OrthographicCamera uiCamera, Vector3 touchPos){

        ArrayList<Integer> list = InputManager.getInputManager().getTouchHoldList();

        int maxPointer = list.size();

        if(this.joyStick.getTouchPointer() != JoyStick.JOYSTICK_NOT_POINTER){

            if(InputManager.getInputManager().getTouch(this.joyStick.getTouchPointer()) != InputManager.TOUCH_HOLD){

                this.joyStick.setIsPressed(false);

                this.joyStick.setTouchPointer(JoyStick.JOYSTICK_NOT_POINTER);

                this.joyStick.resetActuator();

            }

        }

        if(this.joyStick.getIsPressed() && maxPointer == 0){

            this.joyStick.setIsPressed(false);

            this.joyStick.setTouchPointer(JoyStick.JOYSTICK_NOT_POINTER);

            this.joyStick.resetActuator();

        }


        for(int i = 0;i < maxPointer;i++){

            int pointer = list.get(i);

            touchPos.x = Gdx.input.getX(pointer);
            touchPos.y = Gdx.input.getY(pointer);

            touchPos = uiCamera.unproject(touchPos);

            if(this.joyStick.isPressed(touchPos.x, touchPos.y) && !this.joyStick.getIsPressed()){

                this.joyStick.setIsPressed(true);

                this.joyStick.setTouchPointer(pointer);

                break;

            }

        }

        if(this.joyStick.getIsPressed()){

            touchPos.x = Gdx.input.getX(this.joyStick.getTouchPointer());
            touchPos.y = Gdx.input.getY(this.joyStick.getTouchPointer());

            touchPos = uiCamera.unproject(touchPos);

            this.joyStick.setActuator(touchPos.x, touchPos.y);

        }

    }

    /**
     * @brief ジョイスティックの描画処理を行う
     * @param spriteBatch スプライトバッチ
     */
    public void render(CustomSpriteBatch spriteBatch){

        this.joyStick.render(spriteBatch);

    }

}
