/**
 * @file Settings.java
 * @brief ゲームの設定情報を管理するクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame;

import java.io.Serializable;

/**
 * @class Settings
 * @brief ゲームの設定情報を管理するクラス
 */
public class Settings implements Serializable {

    //! シリアルID
    private static final long serialVersionUID = 23245678968678L;

    //! サウンド再生フラグ
    public boolean bSoundOFF;

    //! 未プレイフラグ
    public boolean bNotPlay;

    /**
     * @brief コンストラクタ
     */
    public Settings(){

        this.bSoundOFF = false;

        this.bNotPlay = true;

    }

}
