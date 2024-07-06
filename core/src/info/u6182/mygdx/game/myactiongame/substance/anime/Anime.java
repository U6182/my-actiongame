/**
 * @file Anime.java
 * @brief ゲーム内のキャラクターを表す抽象クラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime;

import com.badlogic.gdx.math.Vector2;
import info.u6182.mygdx.game.myactiongame.substance.Substance;

import info.u6182.api.animation.AnimeMotion;

/**
 * @class Anime
 * @brief ゲーム内のキャラクターを表す抽象クラスを定義するクラス
 */
public abstract class Anime extends Substance {

    //! アニメーション
    protected AnimeMotion anime;

    //! 出現座標
    protected Vector2 startPos;

    //! ジャンプフラグ
    protected boolean bJump = false;

    //! 反転フラグ
    protected boolean bReverse = false;

    //! 死亡フラグ
    protected boolean bDead = false;

    //! ダメージフラグ
    protected boolean bDamage = false;

    /**
     * @brief ジャンプフラグの設定を行う
     * @param bJump ジャンプフラグ
     */
    public void setJump(boolean bJump){

        this.bJump = bJump;

    }

    /**
     * @brief 死亡フラグの設定を行う
     * @param bDead 死亡フラグ
     */
    public void setDead(boolean bDead){

        this.bDead = bDead;

    }

    /**
     * @brief ダメージフラグの設定を行う
     * @param bDamage ダメージフラグ
     */
    public void setDamage(boolean bDamage){

        this.bDamage = bDamage;

    }

    /**
     * @brief アニメーションの取得を行う
     * @return アニメーションを返す
     */
    public AnimeMotion getAnime(){

        return this.anime;

    }

    /**
     * @brief 出現座標の取得を行う
     * @return 出現座標を返す
     */
    public Vector2 getStartPos(){

        return this.startPos;

    }

    /**
     * @brief 反転フラグの取得を行う
     * @return 反転フラグを返す
     */
    public boolean isReverse(){

        return this.bReverse;

    }

    /**
     * @brief 死亡フラグの取得を行う
     * @return 死亡フラグを返す
     */
    public boolean isDead(){

        return this.bDead;

    }

    /**
     * @brief ダメージフラグの取得を行う
     * @return ダメージフラグを返す
     */
    public boolean isDamage(){

        return this.bDamage;

    }

}
