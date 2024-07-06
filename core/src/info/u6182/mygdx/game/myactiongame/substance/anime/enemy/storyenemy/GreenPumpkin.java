/**
 * @file GreenPumpkin.java
 * @brief ゲーム内のグリーンパンプキンクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;

/**
 * @class GreenPumpkin
 * @brief ゲーム内のグリーンパンプキンクラス
 */
public class GreenPumpkin extends Enemy {

    //! ジャンプ時の反転フラグ
    private boolean bJumpReverse;

    //! インターバル
    private float interval;

    //! グリーンパンプキンのインターバルタイム
    private final float GREENPUMPKIN_INTERVALTIME = 20f;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     */
    public GreenPumpkin(Texture texture, Vector2 pos, int type){

        super(pos, type);

        //サイズの初期化
        this.size = new Vector2(32f, 32f);

        //移動量の初期化
        this.move = new Vector2(-ENEMY_SPEED, 0f);

        //ジャンプ時の反転フラグの初期化
        this.bJumpReverse = false;

        //名声スコアの初期化
        this.fameScore = GameElement.FAMESCORE_GREENPUMPKIN;

        //インターバルの初期化
        this.interval = GREENPUMPKIN_INTERVALTIME;

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
    public GreenPumpkin(CustomSpriteBatch spriteBatch, Texture texture, Vector2 pos, Vector2 size, boolean bReverse, int type){

        super(pos, size, bReverse, type);

        //召喚コストの初期化
        this.callCost = GameElement.CALLCOST_GREENPUMPKIN;

        AnimeData[] animeData = {new AnimeData(texture, "移動", this.size, true, new int[][]{{0,2},{1,2},{2,2}},0,0,0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief 敵の行動処理を行う
     */
    @Override
    public void behavior(){

        //待機状態の処理
        if(!this.bJump){

            this.move.x = 0f;

            this.interval--;

        }

        //ジャンプ処理
        if(this.interval <= 0){

            if(!this.bJumpReverse){

                this.move.x = -ENEMY_SPEED;

            }else{

                this.move.x = ENEMY_SPEED;

            }

            this.move.y = ENEMY_JUMP;

            this.bJump = true;

            this.bJumpReverse = !this.bJumpReverse;

            this.interval = GREENPUMPKIN_INTERVALTIME;

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

    /**
     * @brief 召喚後の行動処理を行う
     */
    @Override
    public void callBehavior(){

        //常時ジャンプ
        if(!this.bJump){

            this.move.y = ENEMY_JUMP;

            this.bJump = true;

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

}
