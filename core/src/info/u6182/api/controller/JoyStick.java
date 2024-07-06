/**
 * @file JoyStick.java
 * @brief ジョイスティッククラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */

package info.u6182.api.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import info.u6182.api.collision.OverlapTester;
import info.u6182.api.primitive.Circle;
import info.u6182.api.vector.Vector2;

public class JoyStick {

    //! ジョイスティックにタッチポインタが触れられていない状態
    public static int JOYSTICK_NOT_POINTER = -1;

    //! ジョイスティック画像
    private Texture joyStick;

    //! スティック画像
    private Texture stick;

    //! スティックの位置
    private Vector2 actuator;

    //! 外側の円座標
    private Vector2 outerCircleCenterPos;

    //! 内側の円座標
    private Vector2 innerCircleCenterPos;

    //! 外側の円(タッチポイントとの衝突判定用)
    private Circle outerCircle;

    //! 操作状態フラグ
    private boolean isPressed;

    //! タッチポインタ
    private int touchPointer;

    //! 外側の円半径
    private float outerCircleRadius;

    //! 内側の円半径
    private float innerCircleRadius;

    /**
     * @brief コンストラクタ
     * @param centerPosX 円のX座標
     * @param centerPosY 円のY座標
     * @param outerCircleRadius 外側の円の半径
     * @param innerCircleRadius 内側の円の半径
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    public JoyStick(float centerPosX, float centerPosY, int outerCircleRadius, int innerCircleRadius){

        //スティックの位置
        this.actuator = new Vector2();

        //外側の円座標の初期化
        this.outerCircleCenterPos = new Vector2(centerPosX, centerPosY);

        //内側の円座標の初期化
        this.innerCircleCenterPos = new Vector2(centerPosX, centerPosY);

        //外側の円半径の初期化
        this.outerCircleRadius = outerCircleRadius;

        //内側の円半径の初期化
        this.innerCircleRadius = innerCircleRadius;

        //外側の円の初期化
        this.outerCircle = new Circle(this.outerCircleCenterPos.x, this.outerCircleCenterPos.y, outerCircleRadius);

        //操作状態フラグの初期化
        this.isPressed = false;

        this.touchPointer = JOYSTICK_NOT_POINTER;

    }

    /**
     * @brief コンストラクタ
     * @param centerPosX 円のX座標
     * @param centerPosY 円のY座標
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    public JoyStick(Texture joyStick, Texture stick, float centerPosX, float centerPosY){

        //ジョイスティック画像の読み込み
        this.joyStick = joyStick;

        //スティック画像の読み込み
        this.stick = stick;

        //スティックの位置
        this.actuator = new Vector2();

        //外側の円座標の初期化
        this.outerCircleCenterPos = new Vector2(centerPosX, centerPosY);

        //内側の円座標の初期化
        this.innerCircleCenterPos = new Vector2(centerPosX, centerPosY);

        //外側の円半径の初期化
        this.outerCircleRadius = 32;

        //内側の円半径の初期化
        this.innerCircleRadius = 16;

        //外側の円の初期化
        this.outerCircle = new Circle(this.outerCircleCenterPos.x, this.outerCircleCenterPos.y, outerCircleRadius);

        //操作状態フラグの初期化
        this.isPressed = false;

        this.touchPointer = JOYSTICK_NOT_POINTER;

    }

    /**
     * @brief render
     * @param shapeRenderer プリミティブ描画
     * @return なし
     * @details ジョイスティックの描画を行う
     */
    public void render(ShapeRenderer shapeRenderer){

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(Color.GRAY);

        shapeRenderer.circle(this.outerCircleCenterPos.x, this.outerCircleCenterPos.y, outerCircleRadius);

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(1,0,0,0.3f);

        shapeRenderer.circle(this.innerCircleCenterPos.x, this.innerCircleCenterPos.y, innerCircleRadius);

        shapeRenderer.end();

    }

    /**
     * @brief render
     * @param spriteBatch バッチャ
     * @return なし
     * @details ジョイスティックの描画を行う
     */
    public void render(SpriteBatch spriteBatch){

        spriteBatch.setColor(1,1,1,0.3f);

        spriteBatch.draw(this.joyStick, this.outerCircleCenterPos.x -32, this.outerCircleCenterPos.y - 32);

        spriteBatch.draw(this.stick, this.innerCircleCenterPos.x -16, this.innerCircleCenterPos.y -16);

        spriteBatch.setColor(1,1,1,1);

    }

    /**
     * @brief update
     * @return なし
     * @details ジョイスティックの更新を行う
     */
    public void update(){

        updateInnerCirclePosition();

    }

    /**
     * @brief updateInnerCirclePosition
     * @return なし
     * @details スティックの位置の更新を行う
     */
    private void updateInnerCirclePosition(){

        this.innerCircleCenterPos.set(this.outerCircleCenterPos.x + this.actuator.x * outerCircleRadius, this.outerCircleCenterPos.y + this.actuator.y * outerCircleRadius);

    }

    /**
     * @brief setActuator
     * @param touchPosX X座標
     * @param touchPosY Y座標
     * @return なし
     * @details タッチポイントに合わせてスティックの移動量の計算を行う
     */
    public void setActuator(float touchPosX, float touchPosY){

        float deltaX = touchPosX - this.outerCircleCenterPos.x;
        float deltaY = touchPosY - this.outerCircleCenterPos.y;

        float deltaDistance = (float) (Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));

        if(deltaDistance < this.outerCircleRadius){

            this.actuator.set(deltaX / this.outerCircleRadius, deltaY / this.outerCircleRadius);

        }else{

            this.actuator.set(deltaX / deltaDistance, deltaY / deltaDistance);

        }

    }

    /**
     * @brief resetActuator
     * @return なし
     * @details ジョイスティックの移動量の初期化を行う
     */
    public void resetActuator(){

        this.actuator.set(0, 0);

    }

    /**
     * @brief release
     * @return なし
     * @details ジョイスティックの画像リソースを解放する
     */
    public void release(){

        this.joyStick.dispose();

        this.stick.dispose();

    }

    /**
     * @brief setIsPressed
     * @param isPressed 操作状態フラグ
     * @return なし
     * @details ジョイスティックの操作状態フラグの設定を行う
     */
    public void setIsPressed(boolean isPressed){

        this.isPressed = isPressed;

    }

    /**
     * @brief setTouchPointer
     * @param touchPointer タッチポインタ
     * @return なし
     * @details ジョイスティックのタッチポインタの設定を行う
     */
    public void setTouchPointer(int touchPointer){

        this.touchPointer = touchPointer;

    }

    /**
     * @brief isPressed
     * @param touchPosX X座標
     * @param touchPosY Y座標
     * @return boolean true タッチポイントがジョイスティックの円と衝突(重なった) false タッチポイントがジョイスティックの円と衝突していない(重なっていない)
     * @details タッチポイントとジョイスティックの衝突結果の取得を行う
     */
    public boolean isPressed(float touchPosX, float touchPosY){

        return OverlapTester.pointInCircle(this.outerCircle, touchPosX, touchPosY);

    }

    /**
     * @brief getIsPressed
     * @return boolean true 操作状態 false 操作状態ではない
     * @details ジョイスティックの操作状態の取得を行う
     */
    public boolean getIsPressed(){

        return this.isPressed;

    }

    /**
     * @brief getTouchPointer
     * @return int ジョイスティックのタッチポインタ
     * @details ジョイスティックのタッチポインタの取得を行う
     */
    public int getTouchPointer(){

        return this.touchPointer;

    }

    /**
     * @brief getActuator
     * @return Vector2 スティックの移動量
     * @detalis スティックの移動量を取得する
     */
    public Vector2 getActuator(){

        return this.actuator;

    }

    /**
     * @brief getActuatorX
     * @return float スティックの移動量X
     * @details スティックの移動量Xの取得を行う
     */
    public float getActuatorX(){

        return this.actuator.x;

    }

    /**
     * @brief getActuatorY
     * @return float スティックの移動量Y
     * @details スティックの移動量Yの取得を行う
     */
    public float getActuatorY(){

        return this.actuator.y;

    }

}
