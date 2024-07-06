/**
 * @file InputManager.java
 * @brief インプットマネージャのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import info.u6182.api.primitive.CustomRectangle;

/**
 * @class InputManager
 * @brief インプットマネージャ
 */
public class InputManager {

    //! 自身のインスタンス
    private static final InputManager INPUT_MANAGER = new InputManager();

    //! 登録インプット
    private Input input;

    //! タッチダウンリスト
    private ArrayList<Integer> touchDownList = new ArrayList<>();

    //! タッチアップリスト
    private ArrayList<Integer> touchUpList = new ArrayList<>();

    //! タッチホールドリスト
    private ArrayList<Integer> touchHoldList = new ArrayList<>();

    //! フリックの移動量
    private Vector2 fling;

    //! インプット設定番号
    private int setProcessor;

    //! 最大タッチポインタ
    private int maxPointer;

    //! タッチ状態
    private int[] touchStatus;

    //! インプットノット番号
    public static final int PROCESSOR_NOT = 0;

    //! インプット番号
    public static final int PROCESSOR_INPUT = 1;

    //! インプットジェスチャー番号
    public static final int PROCESSOR_INPUTGESTURE = 2;

    //! タッチアップ番号
    public static final int TOUCH_UP = -1;

    //! タッチノット番号
    public static final int TOUCH_NOT = 0;

    //! タッチダウン番号
    public static final int TOUCH_DOWN = 1;

    //! タッチホールド番号
    public static final int TOUCH_HOLD = 2;

    //! フリックの移動量判定
    public static final float FLING_LEFT_OR_UP = -2000f;

    //! フリックの移動量判定
    public static final float FLING_RIGHT_OR_DOWN = 2000f;

    /**
     * @brief コンストラクタ
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    private InputManager(){

        //インプット設定番号の初期化
        this.setProcessor = PROCESSOR_NOT;

    }

    /**
     * @brief setInputProcessor
     * @param maxPointer 最大タッチポインタ数
     * @details インプットプロセッサの設定を行う
     */
    public void setInputProcessor(int maxPointer){

        //設定完了している場合
        if(this.setProcessor != PROCESSOR_NOT){

            return;

        }

        //タッチ状態の初期化
        this.touchStatus = new int[maxPointer];

        //登録インプットの生成
        this.input = new Input(this.touchStatus);

        //最大タッチポインタの設定
        this.input.setMaxPointer(maxPointer);

        //インプットの登録
        Gdx.input.setInputProcessor(this.input);

        //最大タッチポインタの初期化
        this.maxPointer = maxPointer;

        //登録完了フラグの設定
        this.setProcessor = PROCESSOR_INPUT;

    }

    /**
     * @brief setInputGestureProcessor
     * @param maxPointer 最大タッチポインタ数
     * @details インプットジェスチャープロセッサの設定を行う
     */
    public void setInputGestureProcessor(int maxPointer){

        //設定完了している場合
        if(this.setProcessor != PROCESSOR_NOT){

            return;

        }

        //フリックの移動量の初期化
        this.fling = new Vector2();

        //タッチ状態の初期化
        this.touchStatus = new int[maxPointer];

        //登録インプットの生成
        this.input = new Input(this.fling, this.touchStatus);

        //最大タッチポインタの設定
        this.input.setMaxPointer(maxPointer);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();


        //登録ジェスチャーの生成
        GestureDetector gestureDetector = new GestureDetector(this.input);

        //インプットの登録
        inputMultiplexer.addProcessor(this.input);

        //ジェスチャーの登録
        inputMultiplexer.addProcessor(gestureDetector);

        Gdx.input.setInputProcessor(inputMultiplexer);

        //最大タッチポインタの初期化
        this.maxPointer = maxPointer;

        //登録完了フラグの設定
        this.setProcessor = PROCESSOR_INPUTGESTURE;

    }

    /**
     * @brief reset
     * @details インプットマネージャのリセットを行う
     */
    public void reset(){

        switch (this.setProcessor){

            case PROCESSOR_INPUT:

                touchDownReset();

                touchUpReset();

                break;

            case PROCESSOR_INPUTGESTURE:

                touchDownReset();

                touchUpReset();

                this.input.getGestureInfo().reset();

                break;

        }

    }

    /**
     * @brief touchDownReset
     * @details タッチダウンのリセットを行う
     */
    public void touchDownReset(){

        for(int i = 0;i < this.maxPointer;i++){

            //タッチ状態がダウンの場合
            if(this.touchStatus[i] == TOUCH_DOWN){

                this.touchStatus[i] = TOUCH_HOLD;

            }

        }

    }

    /**
     * @brief touchUpReset
     * @details タッチアップのリセットを行う
     */
    public void touchUpReset(){

        for(int i = 0;i < this.maxPointer;i++){

            if(this.touchStatus[i] == TOUCH_UP){

                this.touchStatus[i] = TOUCH_NOT;

            }

        }


    }

    /**
     * @brief getTouchDownList
     * @return ArrayList<Integer> タッチダウンリスト
     * @details タッチダウンリストの取得を行う
     */
    public ArrayList<Integer> getTouchDownList(){

        for(int i = 0;i < this.maxPointer;i++){

            //タッチ状態がダウンの場合
            if(this.touchStatus[i] == TOUCH_DOWN){

                boolean bContains = false;

                for(int j = 0;j < this.touchDownList.size();j++){

                    if(this.touchDownList.get(j) == i){

                        bContains = true;

                    }

                }

                if(!bContains){

                    //タッチダウンリストに追加
                    this.touchDownList.add(i);

                }

            }else{

                for(int j = 0;j < this.touchDownList.size();j++){

                    //前回タッチダウンの要素が追加されていて今フレームで変わっている場合その要素を削除
                    if(this.touchDownList.get(j) == i){

                        this.touchDownList.remove(j);

                    }

                }

            }

        }

        return this.touchDownList;

    }

    /**
     * @brief getTouchUpList
     * @return ArrayList<Integer> タッチアップリスト
     * @details タッチアップリストの取得を行う
     */
    public ArrayList<Integer> getTouchUpList(){

        for(int i = 0;i < this.maxPointer;i++){

            //タッチ状態がアップの場合
            if(this.touchStatus[i] == TOUCH_UP){

                boolean bContains = false;

                for(int j = 0;j < this.touchUpList.size();j++){

                    if(this.touchUpList.get(j) == i){

                        bContains = true;

                    }

                }

                if(!bContains){

                    //タッチアップリストに追加
                    this.touchUpList.add(i);

                }

            }else{

                for(int j = 0;j < this.touchUpList.size();j++){

                    //前回タッチアップの要素が追加されていて今フレームで変わっている場合その要素を削除
                    if(this.touchUpList.get(j) == i){

                        this.touchUpList.remove(j);

                    }

                }

            }

        }

        return this.touchUpList;

    }

    /**
     * @brief getTouchHoldList
     * @return ArrayList<Integer> タッチホールドリスト
     * @details タッチホールドリストの取得を行う
     */
    public ArrayList<Integer> getTouchHoldList(){

        this.touchHoldList.clear();

        for(int i = 0;i < this.maxPointer;i++){

            if(this.touchStatus[i] == TOUCH_HOLD){

                this.touchHoldList.add(i);

            }

        }

        return this.touchHoldList;

    }

    /**
     * @brief rectTouchDown
     * @param rect 矩形
     * @param camera カメラ
     * @param touchPos タッチ位置
     * @return boolean タッチダウンしているかどうか
     * @details 矩形内でのタッチダウンを判定する
     */
    public boolean rectTouchDown(CustomRectangle rect, OrthographicCamera camera, Vector3 touchPos){

        boolean bTouchDown = false;

        ArrayList<Integer> list = getTouchDownList();

        int maxPointer = list.size();

        for(int i = 0;i < maxPointer;i++) {

            int pointer = list.get(i);

            touchPos.x = Gdx.input.getX(pointer);
            touchPos.y = Gdx.input.getY(pointer);

            touchPos = camera.unproject(touchPos);

            if (rect.contains(touchPos.x, touchPos.y)) {

                bTouchDown = true;

                break;

            }

        }

        return bTouchDown;

    }

    /**
     * @brief rectTouchUp
     * @param rect 矩形
     * @param camera カメラ
     * @param touchPos タッチ位置
     * @return boolean タッチアップしているかどうか
     * @details 矩形内でのタッチアップを判定する
     */
    public boolean rectTouchUp(CustomRectangle rect, OrthographicCamera camera, Vector3 touchPos){

        boolean bTouchUp = false;

        ArrayList<Integer> list = getTouchUpList();

        int maxPointer = list.size();

        for(int i = 0;i < maxPointer;i++) {

            int pointer = list.get(i);

            touchPos.x = Gdx.input.getX(pointer);
            touchPos.y = Gdx.input.getY(pointer);

            touchPos = camera.unproject(touchPos);

            if (rect.contains(touchPos.x, touchPos.y)) {

                bTouchUp = true;

                break;

            }

        }

        return bTouchUp;

    }

    /**
     * @brief rectTouchHold
     * @param rect 矩形
     * @param camera カメラ
     * @param touchPos タッチ位置
     * @return boolean タッチホールドしているかどうか
     * @details 矩形内でのタッチホールドを判定する
     */
    public boolean rectTouchHold(CustomRectangle rect, OrthographicCamera camera, Vector3 touchPos){

        boolean bTouchHold = false;

        ArrayList<Integer> list = getTouchHoldList();

        int maxPointer = list.size();

        for(int i = 0;i < maxPointer;i++) {

            int pointer = list.get(i);

            touchPos.x = Gdx.input.getX(pointer);
            touchPos.y = Gdx.input.getY(pointer);

            touchPos = camera.unproject(touchPos);

            if (rect.contains(touchPos.x, touchPos.y)) {

                bTouchHold = true;

                break;

            }

        }

        return bTouchHold;

    }

    /**
     * @brief getMaxPointer
     * @return int 最大タッチポインタ数
     * @details 最大タッチポインタ数の取得を行う
     */
    public int getMaxPointer(){

        return this.maxPointer;

    }

    /**
     * @brief getFling
     * @return Vector2 フリックの移動量
     * @details フリックの移動量の取得を行う
     */
    public Vector2 getFling(){

        return this.fling;

    }

    /**
     * @brief isFling
     * @return boolean フリックしているかどうか
     * @details フリックしているかどうかの判定を行う
     */
    public boolean isFling(){

        return this.input.getGestureInfo().bFling;

    }

    /**
     * @brief isLeftFling
     * @param leftFling フリックの移動量判定
     * @return boolean 左方向にフリックしているかどうか
     * @details 左方向にフリックしているかどうかの判定を行う
     */
    public boolean isLeftFling(float leftFling){

        boolean bLeftFling = false;

        if(isFling()){

            if(this.fling.x < leftFling){

                bLeftFling = true;

            }

        }

        return bLeftFling;

    }

    /**
     * @brief isRightFling
     * @param rightFling フリックの移動量判定
     * @return boolean 右方向にフリックしているかどうか
     * @details 右方向にフリックしているかどうかの判定を行う
     */
    public boolean isRightFling(float rightFling){

        boolean bRightFling = false;

        if(isFling()){

            if(this.fling.x > rightFling){

                bRightFling = true;

            }

        }

        return bRightFling;

    }

    /**
     * @brief isUpFling
     * @param upFling フリックの移動量判定
     * @return boolean 上方向にフリックしているかどうか
     * @details 上方向にフリックしているかどうかの判定を行う
     */
    public boolean isUpFling(float upFling){

        boolean bUpFling = false;

        if(isFling()){

            if(this.fling.y < upFling){

                bUpFling = true;

            }

        }

        return bUpFling;

    }

    /**
     * @brief isDownFling
     * @param downFling フリックの移動量判定
     * @return boolean 下方向にフリックしているかどうか
     * @details 下方向にフリックしているかどうかの判定を行う
     */
    public boolean isDownFling(float downFling){

        boolean bDownFling = false;

        if(isFling()){

            if(this.fling.y > downFling){

                bDownFling = true;

            }

        }

        return bDownFling;

    }

    /**
     * @brief isLeftFling
     * @return boolean 左方向にフリックしているかどうか
     * @details 左方向にフリックしているかどうかの判定を行う
     */
    public boolean isLeftFling(){

        boolean bLeftFling = false;

        if(isFling()){

            if(this.fling.x < FLING_LEFT_OR_UP){

                bLeftFling = true;

            }

        }

        return bLeftFling;

    }

    /**
     * @brief isRightFling
     * @return boolean 右方向にフリックしているかどうか
     * @details 右方向にフリックしているかどうかの判定を行う
     */
    public boolean isRightFling(){

        boolean bRightFling = false;

        if(isFling()){

            if(this.fling.x > FLING_RIGHT_OR_DOWN){

                bRightFling = true;

            }

        }

        return bRightFling;

    }

    /**
     * @brief isUpFling
     * @return boolean 上方向にフリックしているかどうか
     * @details 上方向にフリックしているかどうかの判定を行う
     */
    public boolean isUpFling(){

        boolean bUpFling = false;

        if(isFling()){

            if(this.fling.y < FLING_LEFT_OR_UP){

                bUpFling = true;

            }

        }

        return bUpFling;

    }

    /**
     * @brief isDownFling
     * @return boolean 下方向にフリックしているかどうか
     * @details 下方向にフリックしているかどうかの判定を行う
     */
    public boolean isDownFling(){

        boolean bDownFling = false;

        if(isFling()){

            if(this.fling.y > FLING_RIGHT_OR_DOWN){

                bDownFling = true;

            }

        }

        return bDownFling;

    }

    /**
     * @brief isLongPress
     * @return boolean 長押ししているかどうか
     * @details 長押ししているかどうかの判定を行う
     */
    public boolean isLongPress(){

        return this.input.getGestureInfo().bLongPress;

    }

    /**
     * @brief getTouchDown
     * @return ArrayList<Integer> タッチダウンリスト
     * @details タッチダウンリストの取得を行う
     */
    public ArrayList<Integer> getTouchDown(){

        return this.touchDownList;

    }

    /**
     * @brief getTouchUp
     * @return ArrayList<Integer> タッチアップリスト
     * @details タッチアップリストの取得を行う
     */
    public ArrayList<Integer> getTouchUp(){

        return this.touchUpList;

    }

    /**
     * @brief getTouchHold
     * @return ArrayList<Integer> タッチホールドリスト
     * @details タッチホールドリストの取得を行う
     */
    public ArrayList<Integer> getTouchHold(){

        return this.touchHoldList;

    }

    /**
     * @brief getTouch
     * @param pointer タッチポインタ
     * @return int タッチ状態
     * @details タッチ状態の取得を行う
     */
    public int getTouch(int pointer){

        if(pointer < 0){

            return pointer;

        }

        return this.touchStatus[pointer];

    }

    /**
     * @brief getInputManager
     * @return InputManager インプットマネージャのインスタンス
     * @details インプットマネージャのインスタンスの取得を行う
     */
    public static InputManager getInputManager(){

        return INPUT_MANAGER;

    }

}
