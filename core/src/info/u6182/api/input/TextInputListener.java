/**
 * @file TextInputListener.java
 * @brief テキスト入力リスナーのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.input;

import com.badlogic.gdx.Input;

/**
 * @class TextInputListener
 * @brief テキスト入力を管理するクラス
 */
public class TextInputListener implements Input.TextInputListener {

    //! 入力されたテキスト
    private String text;

    //! 入力状態フラグ
    private boolean bInput = false;

    //! キャンセル状態フラグ
    private boolean bCancele = false;

    /**
     * @brief input
     * @param text 入力されたテキスト
     * @details テキストが入力された際に入力されたテキストの保存と入力状態の設定を行う
     */
    @Override
    public void input(String text){

        this.text = text;

        this.bInput = true;

    }

    /**
     * @brief canceled
     * @details テキスト入力がキャンセルされた際にキャンセル状態の設定を行う
     */
    @Override
    public void canceled(){

        this.bCancele = true;

    }

    /**
     * @brief setInput
     * @param bInput 入力状態フラグ
     * @details 入力状態フラグの設定を行う
     */
    public void setInput(boolean bInput){

        this.bInput = bInput;

    }

    /**
     * @brief setCancele
     * @param bCancele キャンセル状態フラグ
     * @details キャンセル状態フラグの設定を行う
     */
    public void setCancele(boolean bCancele){

        this.bCancele = bCancele;

    }

    /**
     * @brief getText
     * @return String 入力されたテキスト
     * @details 入力されたテキストの取得を行う
     */
    public String getText(){

        return this.text;

    }

    /**
     * @brief isInput
     * @return boolean 入力状態
     * @details 入力状態の取得を行う
     */
    public boolean isInput(){

        return this.bInput;

    }

    /**
     * @brief isCancele
     * @return boolean キャンセル状態
     * @details キャンセル状態の取得を行う
     */
    public boolean isCancele(){

        return this.bCancele;

    }

}
