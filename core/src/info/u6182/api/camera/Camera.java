/**
 * @file Camera.java
 * @brief カメラ操作を管理するクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import info.u6182.mygdx.game.myactiongame.GameElement;

/**
 * @class Camera
 * @brief カメラ操作を管理するクラス
 */
public class Camera extends OrthographicCamera {

    //! 反転フラグ
    private boolean bRevese;

    //! カメラの右サイド座標
    private float posRight;

    //! カメラの左サイド座標
    private float posLeft;

    //! カメラの上サイド座標
    private float posTop;

    //! カメラの下サイド座標
    private float posButtom;

    //! 移動量X
    private float moveX;

    //! 反転ブレーキ
    private float reveseBrake;

    //! 反転ブレーキの定数
    private final float REVESE_BRAKE = 0.225f;

    //! 反転移動量の定数
    private final float REVESE_MOVE = 7f;

    /**
     * @brief コンストラクタ
     */
    public Camera(){

        super();

    }

    /**
     * @brief コンストラクタ
     * @param viewportWidth ビューポートの幅
     * @param viewportHeight ビューポートの高さ
     */
    public Camera(float viewportWidth, float viewportHeight){

        super(viewportWidth, viewportHeight);

    }

    /**
     * @brief カメラの反転位置を設定する
     * @param bRevese 反転フラグ
     * @param stageWidth ステージの幅
     */
    public void posReverse(boolean bRevese, float stageWidth){

        Vector2 scroll = GameElement.getGameElement().getScroll();

        if(bRevese){

            if(!this.bRevese){

                this.bRevese = true;

                this.moveX = -REVESE_MOVE;

                this.reveseBrake = REVESE_BRAKE;

            }

        }else{

            if(!this.bRevese){

                this.bRevese = true;

                this.moveX = REVESE_MOVE;

                this.reveseBrake = -REVESE_BRAKE;

            }

        }

        this.position.x += this.moveX;

        scroll.x += this.moveX;

        this.moveX += this.reveseBrake;

        if(getPosLeft() < 0){

            this.position.x = getViewCenterX();

        }else if(getPosRight() > stageWidth){

            this.position.x = stageWidth - getViewCenterX();

        }

        if(bRevese){

            this.moveX = this.moveX > -2 ? -2 : this.moveX;

        }else{

            this.moveX = this.moveX < 2 ? 2 : this.moveX;

        }

    }

    /**
     * @brief 反転のリセットを行う
     */
    public void reveseReset(){

        this.bRevese = false;

        this.moveX = 0f;

        this.reveseBrake = 0f;

    }

    /**
     * @brief カメラのサイド位置の設定を行う
     */
    public void setPosSide(){

        this.posRight = this.viewportWidth - this.position.x;

        this.posLeft = this.position.x;

        this.posTop = this.viewportHeight - this.position.y;

        this.posButtom = this.position.y;

    }

    /**
     * @brief カメラの右サイド位置の取得を行う
     * @return float カメラの右サイド位置
     */
    public float getPosRight(){

        return this.position.x + this.posRight;

    }

    /**
     * @brief カメラの左サイド位置の取得を行う
     * @return float カメラの左サイド位置
     */
    public float getPosLeft(){

        return this.position.x - this.posLeft;

    }

    /**
     * @brief カメラの上サイド位置の取得を行う
     * @return float カメラの上サイド位置
     */
    public float getPosTop(){

        return this.position.y + this.posTop;

    }

    /**
     * @brief カメラの下サイド位置の取得を行う
     * @return float カメラの下サイド位置
     */
    public float getPosButtom(){

        return this.position.y - this.posButtom;

    }

    /**
     * @brief カメラの中心X座標の取得を行う
     * @return float カメラの中心X座標
     */
    public float getViewCenterX(){

        return this.viewportWidth * 0.5f;

    }

    /**
     * @brief カメラの中心Y座標の取得を行う
     * @return float カメラの中心Y座標
     */
    public float getViewCenterY(){

        return this.viewportHeight * 0.5f;

    }

}
