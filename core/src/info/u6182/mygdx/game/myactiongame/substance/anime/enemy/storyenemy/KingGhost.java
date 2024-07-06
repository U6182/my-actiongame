/**
 * @file KingGhost.java
 * @brief ゲーム内のキングゴーストクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.controller.Controller;
import info.u6182.mygdx.game.myactiongame.controller.JoyStickController;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.Player;

import info.u6182.api.Utiility;
import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.effectmanager.EffectInfo;

/**
 * @class KingGhost
 * @brief ゲーム内のキングゴーストクラス
 */
public class KingGhost extends Enemy {

    //! 誘惑フラグ
    private boolean bTemptation;

    //! インターバル
    private float interval;

    //! 最大スピード
    protected float maxSpeed;

    //! キングゴーストのスピード
    private final float KINGGHOST_SPEED = 3f;

    //! キングゴーストの移動量
    protected final float KINGGHOST_MOVE = 0.2f;

    //! キングゴーストの最大スピード
    private final float KINGGHOST_MAXSPEED = 3f;

    //! キングゴーストのインターバル
    private final float KINGGHOST_INTERVAL = 0.2f;

    //! キングゴーストのインターバルタイム
    private final float KINGGHOST_INTERVALTIME = 30f;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     */
    public KingGhost(Texture texture, Vector2 pos, int type){

        super(pos, type);

        //サイズの初期化
        this.size = new Vector2(32f, 32f);

        //移動量の初期化
        this.move = new Vector2(KINGGHOST_SPEED, KINGGHOST_SPEED);

        //名声スコアの初期化
        this.fameScore = GameElement.FAMESCORE_KINGGHOST;

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
    public KingGhost(CustomSpriteBatch spriteBatch, Texture texture, Vector2 pos, Vector2 size, boolean bReverse, int type){

        super(pos, size, bReverse, type);

        //移動量の初期化
        this.move.set(0f, 0f);

        //誘惑フラグの初期化
        this.bTemptation = false;

        //召喚コストの初期化
        this.callCost = GameElement.CALLCOST_KINGGHOST;

        //インターバルの初期化
        this.interval = KINGGHOST_INTERVALTIME;

        AnimeData[] animeData = {new AnimeData(texture, "移動", this.size, true, new int[][]{{0,2},{1,2},{2,2}},0,0,0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief プレイヤーと敵の更新処理を行う
     * @param player プレイヤー
     * @param deltaTime デルタタイム
     */
    @Override
    public void update(Player player, float deltaTime){

        //死亡動作処理の更新
        if(this.bDead){

            deadBehavior();

            return;

        }

        //行動処理の更新
        behavior(player.getPos(), player.getSize(), player.isReverse());

        //モーションの更新
        changeMotion();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 敵の更新処理を行う
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

        //誘惑状態の動作処理の更新
        if(this.bTemptation){

            temptationBehavior();

        }

        //行動処理の更新
        callBehavior();

        //モーションの更新
        changeMotion();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 敵の行動処理を行う
     * @param pos プレイヤーの位置
     * @param size プレイヤーのサイズ
     * @param bReverse プレイヤーの反転フラグ
     */
    public void behavior(Vector2 pos, Vector2 size, boolean bReverse){

        boolean bMove = false;

        //同じ向きなら移動
        if(this.bReverse && bReverse || !this.bReverse && !bReverse){

            bMove = true;

        }

        if(this.bReverse != bReverse){

            if(this.bReverse){

                if(pos.x > this.pos.x + this.size.x){

                    bMove = true;

                }

            }else{

                if(this.pos.x > pos.x){

                    bMove = true;

                }

            }

        }

        if(bMove){

            if(this.pos.x + this.size.x < pos.x){

                this.move.x = KINGGHOST_SPEED;

            }else if(this.pos.x > pos.x){

                this.move.x = -KINGGHOST_SPEED;

            }

            if(this.pos.y > pos.y + size.y){

                this.move.y = -KINGGHOST_SPEED;

            }else if(this.pos.y + this.size.y < pos.y){

                this.move.y = KINGGHOST_SPEED;

            }

        }else{

            this.move.set(0f, 0f);

        }

        this.pos.add(this.move);

    }

    /**
     * @brief 召喚の行動処理を行う
     */
    public void callBehavior(){

        //誘惑状態の場合行動できない
        if(this.bTemptation){

            return;

        }

        //自殺ボタンで死亡
        if(Controller.bDeadButton){

            this.bDead = true;

        }

        Vector2 actuator = JoyStickController.actuator;

        boolean bMoveX = false;

        if(actuator.x > 0.1f) {

            bMoveX = true;

            this.move.x += KINGGHOST_MOVE;

            //スティックの移動力に合わせて現在の最大スピードを決定
            float nowMaxSpeed = (Math.round(actuator.x * 100) / 100f) * KINGGHOST_MAXSPEED;

            //1フレーム前の最大スピードが現在の最大スピードより大きい場合現在の最大スピードに設定
            if(this.maxSpeed > nowMaxSpeed || this.maxSpeed < nowMaxSpeed){

                this.maxSpeed = nowMaxSpeed;

            }

            //現在の移動量が最大スピードより大きい場合
            if(this.move.x > this.maxSpeed) {

                //移動量を徐々に減らす
                this.move.x -= KINGGHOST_MOVE * 2;

                if (this.move.x <= this.maxSpeed) {

                    this.move.x = this.maxSpeed;

                }

            }

        }else if(actuator.x < -0.1f){

            bMoveX = true;

            this.move.x -= KINGGHOST_MOVE;

            float nowMaxSpeed = (Math.round(actuator.x * 100) / 100f) * KINGGHOST_MAXSPEED;

            //1フレーム前の最大スピードが現在の最大スピードより大きい場合現在の最大スピードに設定
            if(this.maxSpeed > nowMaxSpeed || this.maxSpeed < nowMaxSpeed){

                this.maxSpeed = nowMaxSpeed;

            }

            //現在の移動量が最大スピードより大きい場合
            if(this.move.x < this.maxSpeed) {

                //移動量を徐々に減らす
                this.move.x += KINGGHOST_MOVE * 2;

                if (this.move.x >= this.maxSpeed) {

                    this.move.x = this.maxSpeed;

                }

            }


        }

        boolean bMoveY = false;

        if(actuator.y > 0.1f) {

            bMoveY = true;

            this.move.y += KINGGHOST_MOVE;

            //スティックの移動力に合わせて現在の最大スピードを決定
            float nowMaxSpeed = (Math.round(actuator.y * 100) / 100f) * KINGGHOST_MAXSPEED;

            //1フレーム前の最大スピードが現在の最大スピードより大きい場合現在の最大スピードに設定
            if(this.maxSpeed > nowMaxSpeed || this.maxSpeed < nowMaxSpeed){

                this.maxSpeed = nowMaxSpeed;

            }

            //現在の移動量が最大スピードより大きい場合
            if(this.move.y > this.maxSpeed) {

                //移動量を徐々に減らす
                this.move.y -= KINGGHOST_MOVE * 2;

                if (this.move.y <= this.maxSpeed) {

                    this.move.y = this.maxSpeed;

                }

            }

        }else if(actuator.y < -0.1f){

            bMoveY = true;

            this.move.y -= KINGGHOST_MOVE;

            float nowMaxSpeed = (Math.round(actuator.y * 100) / 100f) * KINGGHOST_MAXSPEED;

            //1フレーム前の最大スピードが現在の最大スピードより大きい場合現在の最大スピードに設定
            if(this.maxSpeed > nowMaxSpeed || this.maxSpeed < nowMaxSpeed){

                this.maxSpeed = nowMaxSpeed;

            }

            //現在の移動量が最大スピードより大きい場合
            if(this.move.y < this.maxSpeed) {

                //移動量を徐々に減らす
                this.move.y += KINGGHOST_MOVE * 2;

                if (this.move.y >= this.maxSpeed) {

                    this.move.y = this.maxSpeed;

                }

            }

        }

        if(!bMoveX){

            if(this.move.x > 0){

                this.move.x -= KINGGHOST_MOVE;

                if(this.move.x < 0){

                    this.move.x = 0f;

                }

            }else if(this.move.x < 0){

                this.move.x += KINGGHOST_MOVE;

                if(this.move.x > 0){

                    this.move.x = 0f;

                }

            }

        }

        if(!bMoveY){

            if(this.move.y > 0){

                this.move.y -= KINGGHOST_MOVE;

                if(this.move.y < 0){

                    this.move.y = 0f;

                }

            }else if(this.move.y < 0){

                this.move.y += KINGGHOST_MOVE;

                if(this.move.y > 0){

                    this.move.y = 0f;

                }

            }

        }

        this.pos.add(this.move);

    }

    /**
     * @brief 誘惑状態の動作処理を行う
     */
    public void temptationBehavior(){

        //誘惑エフェクトを表示
        if(!EffectManager.getEffectManager().isEffect(EffectInfo.EFFECT_TEMPTATION)){

            Vector2 size = EffectManager.getEffectManager().getSize(EffectInfo.EFFECT_TEMPTATION);

            EffectManager.getEffectManager().start(EffectInfo.EFFECT_TEMPTATION, this.pos.x - size.x, this.pos.y + size.y);

        }

        this.interval -= KINGGHOST_INTERVAL;

        if(this.interval <= 0){

            this.bTemptation = false;

            this.interval = KINGGHOST_INTERVALTIME;

        }

    }

    /**
     * @brief 誘惑フラグの設定を行う
     */
    public void setTemptation(){

        this.bTemptation = true;

    }

}
