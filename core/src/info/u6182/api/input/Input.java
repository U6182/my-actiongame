/**
 * @file JoyStick.java
 * @brief インプットクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */

package info.u6182.api.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

/**
 * @class Input
 * @brief インプットを管理するクラス
 */
public class Input implements InputProcessor, GestureListener {

    //! ジェスチャー情報
    private GestureInfo gestureInfo;

    //! フリック
    private Vector2 fling;

    //! タッチ状態
    private int[] touchStatus;

    //! 最大タッチ可能数
    private int maxPointer;

    /**
     * @brief コンストラクタ
     * @param touchStatus タッチ状態
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    public Input(int[] touchStatus){

        //タッチ状態の設定
        this.touchStatus = touchStatus;

    }

    /**
     * @brief コンストラクタ
     * @param fling フリック
     * @param touchStatus タッチ状態
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    public Input(Vector2 fling, int[] touchStatus){

        //ジェスチャー情報の初期化
        this.gestureInfo = new GestureInfo();

        //フリックの設定
        this.fling = fling;

        //タッチ状態の設定
        this.touchStatus = touchStatus;

    }

    /**
     * @brief keyDown
     * @param keycode キーコード
     * @return boolean false
     * @details キーが解放された時の処理を行う
     */
    @Override
    public boolean keyDown(int keycode){

        return false;

    }

    /**
     * @brief keyUp
     * @param keycode キーコード
     * @return boolean false
     * @details キーが押された時の処理を行う
     */
    @Override
    public boolean keyUp(int keycode){

        return false;

    }

    /**
     * @brief keyDown
     * @param character 文字
     * @return boolean false
     * @details キーが入力された時の処理を行う
     */
    @Override
    public boolean keyTyped(char character){

        return false;

    }

    /**
     * @brief touchDown
     * @param screenX タッチスクリーン座標X
     * @param screenY タッチスクリーン座標Y
     * @param pointer タッチポインタ
     * @param button ボタン
     * @details スクリーンをタッチされた瞬間の処理を行う
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){

        if(pointer < this.maxPointer){

            if(this.touchStatus != null){

                if(this.touchStatus[pointer] <= InputManager.TOUCH_NOT){

                    this.touchStatus[pointer] = InputManager.TOUCH_DOWN;

                }

            }

        }

        return false;

    }

    /**
     * @brief touchUp
     * @param screenX タッチスクリーン座標X
     * @param screenY タッチスクリーン座標Y
     * @param pointer タッチポインタ
     * @param button ボタン
     * @details スクリーンを離された瞬間の処理を行う
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){

        if(pointer < this.maxPointer){

            if(this.touchStatus != null){

                if(this.touchStatus[pointer] >= InputManager.TOUCH_DOWN){

                    this.touchStatus[pointer] = InputManager.TOUCH_UP;

                }

            }

        }

        return false;

    }

    /**
     * @brief touchDragged
     * @param screenX タッチスクリーン座標X
     * @param screenY タッチスクリーン座標Y
     * @param pointer タッチポインタ
     * @details スクリーンをドラッグされた時の処理を行う
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer){

        return false;

    }

    /**
     * @brief mouseMoved
     * @param screenX タッチスクリーン座標X
     * @param screenY タッチスクリーン座標Y
     * @details マウスを移動した時の処理を行う
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY){

        return false;

    }

    /**
     * @brief scrolled
     * @param amount ホイールスクロール量
     * @details ホイールをスクロールした時の処理を行う
     */
    @Override
    public boolean scrolled(int amount){

        return false;

    }

    /**
     * @brief touchDown
     * @param x タッチ座標X
     * @param y タッチ座標Y
     * @param pointer タッチポインタ
     * @param button ボタン
     * @return boolean false
     * @details タッチされた時の処理を行う
     */
    @Override
    public boolean touchDown(float x, float y, int pointer, int button){

        if(pointer < this.maxPointer){

            if(this.touchStatus != null){

                if(this.touchStatus[pointer] <= InputManager.TOUCH_NOT){

                    this.touchStatus[pointer] = InputManager.TOUCH_DOWN;

                }

            }

        }

        return false;

    }

    /**
     * @brief tap
     * @param x タッチ座標X
     * @param y タッチ座標Y
     * @param pointer タッチポインタ
     * @param button ボタン
     * @return boolean false
     * @details タップされた時の処理を行う
     */
    @Override
    public boolean tap(float x, float y, int pointer, int button){

        return false;

    }

    /**
     * @brief longPress
     * @param x タッチ座標X
     * @param y タッチ座標Y
     * @return boolean true
     * @details 長押しされた時の処理を行う
     */
    @Override
    public boolean longPress(float x, float y){

        this.gestureInfo.bLongPress = true;

        return true;

    }

    /**
     * @brief fling
     * @param velocityX フリック速度X
     * @param velocityY フリック速度Y
     * @param button ボタン
     * @return boolean true
     * @details フリックされた時の処理を行う
     */
    @Override
    public boolean fling(float velocityX, float velocityY, int button){

        this.fling.set(velocityX, velocityY);

        this.gestureInfo.bFling = true;

        return true;

    }

    /**
     * @brief pan
     * @param x パン開始座標X
     * @param y パン開始座標Y
     * @param deltaX パン移動量X
     * @param deltaY パン移動量Y
     * @return boolean true
     * @details パンされた時の処理を行う
     */
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY){

        return true;

    }

    /**
     * @brief panStop
     * @param x パン終了座標X
     * @param y パン終了座標Y
     * @param pointer タッチポインタ
     * @param button ボタン
     * @return boolean true
     * @details パンが終了した時の処理を行う
     */
    @Override
    public boolean panStop(float x, float y, int pointer, int button){

        return true;

    }

    /**
     * @brief zoom
     * @param initiaDistance 初期距離
     * @param distance 距離
     * @return boolean true
     * @details ズームされた時の処理を行う
     */
    @Override
    public boolean zoom(float initiaDistance, float distance){

        return true;

    }

    /**
     * @brief pinch
     * @param initialPointer1 初期ポインタ1
     * @param initialPointer2 初期ポインタ2
     * @param pointer1 ポインタ1
     * @param pointer2 ポインタ2
     * @return boolean true
     * @details ピンチされた時の処理を行う
     */
    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2){

        return true;

    }

    /**
     * @brief pinchStop
     * @details ピンチが終了した時の処理を行う
     */
    @Override
    public void pinchStop(){
        ;
    }

    /**
     * @brief scrolled
     * @param maxPointer 最大タッチ可能数
     * @details 最大タッチ可能数の設定を行う
     */
    public void setMaxPointer(int maxPointer){

        this.maxPointer = maxPointer;

    }

    /**
     * @brief getGestureInfo
     * @return GestureInfo ジェスチャー情報
     * @details ジェスチャー情報の取得を行う
     */
    public GestureInfo getGestureInfo(){

        return this.gestureInfo;

    }

}