/**
 * @file Substance.java
 * @brief ゲーム内の物質を表す抽象クラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance;

import com.badlogic.gdx.math.Vector2;

import info.u6182.api.primitive.CustomRectangle;

/**
 * @class Substance
 * @brief ゲーム内の物質を表す抽象クラスを定義するクラス
 */
public abstract class Substance {

    //! 座標
    protected Vector2 pos;

    //! 移動量
    protected Vector2 move;

    //! サイズ
    protected Vector2 size;

    //! 矩形
    protected CustomRectangle rect;

    //! 表示フラグ
    protected boolean bShow = false;

    //! 個体識別番号
    protected int idNo;

    //! 種類
    protected int type;

    //! 名声
    protected int fameScore;

    /**
     * @brief 表示フラグの設定を行う
     * @param bShow 表示フラグ
     */
    public void setShow(boolean bShow){

        this.bShow = bShow;

    }

    /**
     * @brief 矩形の取得を行う
     * @return 矩形を返す
     */
    public CustomRectangle getRect(){

        return this.rect.set(this.pos, this.size);

    }

    /**
     * @brief 座標の取得を行う
     * @return 座標を返す
     */
    public Vector2 getPos(){

        return this.pos;

    }

    /**
     * @brief 移動量の取得を行う
     * @return 移動量を返す
     */
    public Vector2 getMove(){

        return this.move;

    }

    /**
     * @brief サイズの取得を行う
     * @return サイズを返す
     */
    public Vector2 getSize(){

        return this.size;

    }

    /**
     * @brief 表示フラグの取得を行う
     * @return 表示フラグを返す
     */
    public boolean isShow(){

        return this.bShow;

    }

    /**
     * @brief 個体識別番号の取得を行う
     * @return 個体識別番号を返す
     */
    public int getIdNo(){

        return this.idNo;

    }

    /**
     * @brief 種類の取得を行う
     * @return 種類を返す
     */
    public int getType(){

        return this.type;

    }

    /**
     * @brief 名声スコアの取得を行う
     * @return 名声スコアを返す
     */
    public int getFameScore(){

        return this.fameScore;

    }

}
