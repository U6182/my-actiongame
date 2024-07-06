/**
 * @file WhiteGhost.java
 * @brief ゲーム内のホワイトゴーストクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy;

import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.Utiility;
import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.batch.CustomSpriteBatch;

/**
 * @class WhiteGhost
 * @brief ゲーム内のホワイトゴーストクラス
 */
public class WhiteGhost extends Enemy {

    //! 移動範囲
    private float rangeX;

    //! ホワイトゴーストの移動範囲
    private final float WHITEGHOST_RANGE = 224f;

    //! ホワイトゴースト召喚時の移動範囲
    private final float CALLWHITEGHOST_RANGE = 80f;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     */
    public WhiteGhost(Texture texture, Vector2 pos, int type){

        super(pos, type);

        //サイズの初期化
        this.size = new Vector2(32f, 32f);

        //移動量の初期化
        this.move = new Vector2(-ENEMY_SPEED, 0f);

        //名声スコアの初期化
        this.fameScore = GameElement.FAMESCORE_WHITEGHOST;

        //移動範囲の初期化
        this.rangeX = WHITEGHOST_RANGE;

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
    public WhiteGhost(CustomSpriteBatch spriteBatch, Texture texture, Vector2 pos, Vector2 size, boolean bReverse, int type){

        super(pos, size, bReverse, type);

        //出現座標の設定
        this.startPos = new Vector2(bReverse ? this.pos.x : this.pos.x + CALLWHITEGHOST_RANGE, this.pos.y);

        //移動範囲の初期化
        this.rangeX = bReverse ? this.pos.x - CALLWHITEGHOST_RANGE : this.pos.x;

        //召喚コストの初期化
        this.callCost = GameElement.CALLCOST_WHITEGHOST;

        AnimeData[] animeData = {new AnimeData(texture, "移動", this.size, true, new int[][]{{0,2},{1,2},{2,2}},0,0,0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief 更新を行う
     * @param deltaTime デルタタイム
     */
    @Override
    public void update(float deltaTime){

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

        //画面外に出た場合
        if(pos.y + this.size.y < 0 || pos.y > viewSize.y || pos.x > viewSize.x || pos.x + this.size.x < 0){

            this.bShow = false;

        }

        //死亡動作処理の更新
        if(this.bDead){

            deadBehavior();

            return;

        }

        //行動処理の更新
        callBehavior();

        //モーションの更新
        changeMotion();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 行動処理を行う
     */
    @Override
    public void behavior(){

        //移動範囲内を往復
        if(this.pos.x < this.startPos.x || this.pos.x + this.size.x > this.startPos.x + this.rangeX){

            this.move.x = -this.move.x;

        }

        this.pos.x += this.move.x;

    }

    /**
     * @brief 召喚の行動処理を行う
     */
    @Override
    public void callBehavior(){

        //移動範囲内を往復
        if(this.pos.x > this.startPos.x || this.pos.x < this.rangeX){

            this.move.x = -this.move.x;

        }

        this.pos.x += this.move.x;

    }

    /**
     * @brief 衝突時の処理を行う
     * @param buried 埋まり値
     */
    @Override
    public void callCollisionStage(Vector2 buried){

        //埋まり値を加算
        this.pos.add(buried);

        //右辺の衝突
        if(buried.x < 0 && this.move.x > 0){

            this.move.x = -this.move.x;

        //左辺の衝突
        }else if(buried.x > 0 && this.move.x < 0){

            this.move.x = -this.move.x;

        }

    }

}
