/**
 * @file ScreenElement.java
 * @brief スクリーン要素を管理するクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame;

/**
 * @class ScreenElement
 * @brief スクリーン要素を管理するクラス
 */
public class ScreenElement {

    //! タイトルスクリーン番号
    public static final int SCREEN_TITLE = 0;

    //! メニュースクリーン番号
    public static final int SCREEN_MENU = 1;

    //! ストーリースクリーン番号
    public static final int SCREEN_STORY = 2;

    //! アナザーストーリースクリーン番号
    public static final int SCREEN_ANOTHERSTORY = 3;

    //! 憎しみの扉スクリーン番号
    public static final int SCREEN_DOORHATRED = 4;

    //! スコアステータススクリーン番号
    public static final int SCREEN_STATUS = 5;

    //! クレジットスクリーン番号
    public static final int SCREEN_CREDIT = 6;

    //! ゲームブレイクスクリーン番号
    public static final int SCREEN_GAMEBREAK = 7;

    //! ゲームスクリーン番号
    public static final int SCREEN_GAME = 8;

    //! ゲームオーバースクリーン番号
    public static final int SCREEN_GAMEOVER = 9;

    //! ゲームクリアスクリーン番号
    public static final int SCREEN_GAMECLEAR = 10;

    //! オプションスクリーン番号
    public static final int SCREEN_OPTION = 11;

    //! スクリーン番号数
    public static final int SCREEN_COUNT = 12;

    //! 自身のインスタンス
    private static final ScreenElement screenElement = new ScreenElement();

    //! スクリーンエフェクトフラグ
    private boolean bEffect = false;

    //次に遷移するスクリーン番号
    private int nextScreen;

    //現在のスクリーン番号
    private int nowScreen;

    /**
     * @brief コンストラクタ
     */
    private ScreenElement(){
        ;
    }

    /**
     * @brief 次のスクリーンと現在のスクリーンが同じかどうかの判定を行う
     * @return boolean 次のスクリーンと現在のスクリーンが同じかどうか
     */
    public boolean isSameScreen(){

        return this.nextScreen == this.nowScreen;

    }

    /**
     * @brief 次のスクリーンと現在のスクリーンの設定を行う
     * @param nextScreen 次のスクリーン番号
     * @param nowScreen 現在のスクリーン番号
     */
    public void setScreen(int nextScreen, int nowScreen){

        this.nextScreen = nextScreen;

        this.nowScreen = nowScreen;

        this.bEffect = false;

    }

    /**
     * @brief スクリーンエフェクトフラグの設定を行う
     * @param bEffect スクリーンエフェクトフラグ
     */
    public void setEffect(boolean bEffect){

        this.bEffect = bEffect;

    }

    /**
     * @brief 次のスクリーン番号の設定を行う
     * @param nextScreen 次のスクリーン番号
     */
    public void setNextScreen(int nextScreen){

        this.nextScreen = nextScreen;

    }

    /**
     * @brief 現在のスクリーン番号の設定を行う
     * @param nowScreen 現在のスクリーン番号
     */
    public void setNowScreen(int nowScreen){

        this.nowScreen = nowScreen;

    }

    /**
     * @brief スクリーンエフェクトフラグの取得を行う
     * @return boolean スクリーンエフェクトフラグ
     */
    public boolean isEffect(){

        return this.bEffect;

    }

    /**
     * @brief 次のスクリーン番号の取得を行う
     * @return int 次のスクリーン番号
     */
    public int getNextScreen(){

        return this.nextScreen;

    }

    /**
     * @brief 現在のスクリーン番号の取得を行う
     * @return int 現在のスクリーン番号
     */
    public int getNowScreen(){

        return this.nowScreen;

    }

    /**
     * @brief ScreenElementのインスタンスの取得を行う
     * @return ScreenElementのインスタンス
     */
    public static ScreenElement getElement(){

        return screenElement;

    }

}
