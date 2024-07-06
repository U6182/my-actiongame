/**
 * @file GhostPumpkin.java
 * @brief ゲーム内のゴーストパンプキンクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.batch.CustomSpriteBatch;

/**
 * @class GhostPumpkin
 * @brief ゲーム内のゴーストパンプキンクラス
 */
public class GhostPumpkin extends Enemy {

    //! 移動範囲
    private float rangeY;

    //! 召喚時のゴーストパンプキンの範囲Y
    private final float GHOSTPUMPKIN_CALLRANGEY = 80f;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     */
    public GhostPumpkin(Texture texture, Vector2 pos, int type){

        super(pos, type);

        //サイズの初期化
        this.size = new Vector2(32f, 32f);

        //移動量の初期化
        this.move = new Vector2(-ENEMY_SPEED, ENEMY_SPEED);

        //名声スコアの初期化
        this.fameScore = GameElement.FAMESCORE_GHOSTPUMPKIN;

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
    public GhostPumpkin(CustomSpriteBatch spriteBatch, Texture texture, Vector2 pos, Vector2 size, boolean bReverse, int type){

        super(pos, size, bReverse, type);

        //移動量Yの初期化
        this.move.y = CALLENEMY_SPEED;

        //出現座標の設定
        this.startPos = new Vector2(pos);

        //移動範囲の初期化
        this.rangeY = this.startPos.y + this.size.y + GHOSTPUMPKIN_CALLRANGEY;

        //召喚コストの初期化
        this.callCost = GameElement.CALLCOST_GHOSTPUMPKIN;

        AnimeData[] animeData = {new AnimeData(texture, "移動", this.size, true, new int[][]{{0,2},{1,2},{2,2}},0,0,0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief 敵の行動処理を行う
     */
    @Override
    public void behavior(){

        float rangeX = this.startPos.x + 416;

        if(this.pos.x < this.startPos.x || this.pos.x + this.size.x > rangeX){

            this.move.x = -this.move.x;

        }

        if(this.pos.y >= this.startPos.y + 50f || this.pos.y <= this.startPos.y - 50f){

            this.move.y = -this.move.y;

        }

        this.pos.add(this.move);

    }

    /**
     * @brief 召喚の行動処理を行う
     */
    @Override
    public void callBehavior(){

        if(this.pos.y < this.startPos.y || this.pos.y > this.rangeY){

            this.move.y = -this.move.y;

        }

        this.pos.add(this.move);

    }

    /**
     * @brief ステージとの衝突処理を行う
     * @param buried 埋まり値
     */
    @Override
    public void callCollisionStage(Vector2 buried){

        //埋まり値を加算
        this.pos.add(buried);

        //下辺の衝突
        if(buried.y > 0 && this.move.y < 0){

            this.move.y = -this.move.y;

        //上辺の衝突
        }else if(buried.y < 0 && this.move.y > 0){

            this.move.y = -this.move.y;

        }

        //右辺の衝突
        if(buried.x < 0 && this.move.x > 0){

            this.bDead = true;

        //左辺の衝突
        }else if(buried.x > 0 && this.move.x < 0){

            this.bDead = true;

        }

    }

}
