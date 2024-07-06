/**
 * @file CameraInfo.java
 * @brief カメラ情報を管理するクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame;


import com.badlogic.gdx.math.Vector2;

import info.u6182.api.camera.Camera;

/**
 * @class CameraInfo
 * @brief カメラ情報を管理するクラス
 */
public class CameraInfo {

    //! リセットフラグ
    private boolean bReset;

    //! スクロール
    private Vector2 scroll;

    //! カメラ座標
    private Vector2 pos;

    /**
     * @brief コンストラクタ
     */
    public CameraInfo(){

        this.bReset = false;

        this.scroll = new Vector2();

        this.pos = new Vector2();

    }

    /**
     * @brief カメラ座標とスクロール情報の変更を行う
     * @param cameraPos カメラ座標
     * @param scroll スクロール情報
     */
    public void change(Vector2 cameraPos, Vector2 scroll){

        this.pos.set(cameraPos);

        this.scroll.set(scroll);

    }

    /**
     * @brief カメラ座標とスクロール情報のリセットを行う
     * @param camera カメラオブジェクト
     * @param scroll スクロール情報
     */
    public void reset(Camera camera, Vector2 scroll){

        if(this.bReset){

            scroll.set(this.scroll);

            camera.position.x = this.pos.x;

            camera.position.y = this.pos.y;

            this.bReset = false;

        }

    }

    /**
     * @brief リセットフラグの設定を行う
     * @param bReset リセットフラグ
     */
    public void setReset(boolean bReset){

        this.bReset = bReset;

    }

}
