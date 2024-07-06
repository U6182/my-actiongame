/**
 * @file GestureInfo.java
 * @brief ジェスチャー情報を管理するクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.input;

/**
 * @class GestureInfo
 * @brief ジェスチャー情報を管理するクラス
 */
public class GestureInfo {

    //! フリック操作フラグ
    public boolean bFling;

    //! 長押し操作フラグ
    public boolean bLongPress;

    /**
     * @brief コンストラクタ
     * @details 各フラグの初期化を行う
     */
    public GestureInfo(){

        this.bFling = false;

        this.bLongPress = false;

    }

    /**
     * @brief フラグのリセットを行う
     */
    public void reset(){

        this.bFling = false;

        this.bLongPress = false;

    }

}
