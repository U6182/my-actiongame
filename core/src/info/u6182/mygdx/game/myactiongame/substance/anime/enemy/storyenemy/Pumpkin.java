/**
 * @file Pumpkin.java
 * @brief ゲーム内のパンプキンクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;

/**
 * @class Pumpkin
 * @brief ゲーム内のパンプキンクラス
 */
public class Pumpkin extends Enemy {

    //! 落下フラグ
    private boolean bFall;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     */
    public Pumpkin(Texture texture, Vector2 pos, int type){

        super(pos, type);

        //サイズの初期化
        this.size = new Vector2(32f, 32f);

        //移動量の初期化
        this.move = new Vector2(-ENEMY_SPEED, 0f);

        //名声スコアの初期化
        this.fameScore = GameElement.FAMESCORE_PUMPKIN;

        AnimeData[] animeData = {new AnimeData(texture, "移動", this.size, true, new int[][]{{0,2},{1,2},{2,2}},0,0,0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData);

    }

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param size サイズ
     * @param bReverse 反転フラグ
     * @param type 種類
     */
    public Pumpkin(CustomSpriteBatch spriteBatch, Texture texture, Vector2 pos, Vector2 size, boolean bReverse, int type){

        super(pos, size, bReverse, type);

        //召喚コストの初期化
        this.callCost = GameElement.CALLCOST_PUMPKIN;

        AnimeData[] animeData = {new AnimeData(texture, "移動", this.size, true, new int[][]{{0,2},{1,2},{2,2}},0,0,0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief 敵の行動処理を行う
     */
    @Override
    public void behavior(){

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

        //前方に地面がない場合反対に移動
        if(this.bFall){

            this.move.x = -this.move.x;

        }

    }

    /**
     * @brief 召喚の行動処理を行う
     */
    @Override
    public void callBehavior(){

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

    /**
     * @brief 落下フラグの設定を行う
     * @param bFall 落下フラグ
     */
    @Override
    public void setFall(boolean bFall){

        this.bFall = bFall;

    }

}
