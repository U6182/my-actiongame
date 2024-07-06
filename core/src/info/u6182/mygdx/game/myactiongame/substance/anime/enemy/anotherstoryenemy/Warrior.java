/**
 * @file Warrior.java
 * @brief ゲーム内の戦士クラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.Player;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.effectmanager.EffectInfo;
import info.u6182.api.soundmanager.SoundManager;

/**
 * @class Warrior
 * @brief ゲーム内の戦士クラス
 */
public class Warrior extends Enemy {

    //! 戦士の最大スピード
    private final float WARRIOR_MAXSPEED = 5f;

    //! 移動範囲を示す矩形
    private CustomRectangle rangeRect;

    //! 一時的な移動量X
    private boolean bTempMoveX;

    //! ダッシュフラグ
    private boolean bDash;

    //! 落下フラグ
    private boolean bFall;

    //! 攻撃状態
    private int attack;

    //! 一時的な移動量X
    private float tempMoveX;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     * @param idNo 個体識別番号
     */
    public Warrior(Texture texture, Vector2 pos, int type, int idNo){

        super(pos, type);

        //サイズの初期化
        this.size = new Vector2(32f, 32f);

        //移動量の初期化
        this.move = new Vector2(-ENEMY_SPEED, 0f);

        this.rangeRect = new CustomRectangle();

        this.bTempMoveX = false;

        this.bFall = false;

        //個体識別番号の設定
        this.idNo = idNo;

        this.attack = 0;

        this.tempMoveX = this.move.x;

        AnimeData[] animeData = {new AnimeData(texture, "移動", this.size, true, new int[][]{{0,2},{1,2},{2,2}},0,0,0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData);

    }

    /**
     * @brief 更新処理を行う
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

        if(this.attack == 1){

            EffectManager.getEffectManager().start(EffectInfo.EFFECT_WARRIORATTACK, this.pos);

            SoundManager.getSoundManager().play(SoundInfo.SOUND_SEVER);

            this.attack++;

        }

        this.rangeRect.setCustomRect(this.pos.x - 100f, this.pos.y, this.pos.x + this.size.x + 100f, this.pos.y + this.size.y);

        CustomRectangle prec = player.getRect();

        boolean bDead = false;

        if(player.isDamage() || player.isDead()){

            bDead = true;

        }

        //行動処理の更新
        behavior(prec, bDead);

        //モーションの更新
        changeMotion();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 敵の行動処理を行う
     * @param prec プレイヤーの矩形
     * @param bDead プレイヤーが死亡しているかどうかのフラグ
     */
    private void behavior(CustomRectangle prec, boolean bDead){

        if(this.attack == 0 && getRect().overlaps(prec)){

            this.attack++;

        }

        if(this.attack == 0 && this.rangeRect.overlaps(prec)){

            if(!this.bTempMoveX){

                this.tempMoveX = this.move.x;

                this.bTempMoveX = true;

            }

            this.bDash = true;

            if(this.pos.x > prec.right){

                this.move.x = -WARRIOR_MAXSPEED;

            }else if(this.pos.x + this.size.x < prec.left){

                this.move.x = WARRIOR_MAXSPEED;

            }

        }else{

            this.move.x = this.tempMoveX;

            this.tempMoveX = this.move.x;

            this.bDash = false;

            this.bTempMoveX = false;

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

        if(this.bFall){

            this.move.x = -this.move.x;

            this.tempMoveX = this.move.x;

        }

    }

    /**
     * @brief ステージとの衝突処理を行う
     * @param buried 埋まり値
     */
    @Override
    public void collisionStage(Vector2 buried){

        //埋まり値を加算
        this.pos.add(buried);

        //下辺の衝突
        if(buried.y > 0 && this.move.y < 0){

            this.move.y = 0f;

            if(this.bJump){

                this.bJump = false;

            }

        //上辺の衝突
        }else if(buried.y < 0 && this.move.y > 0){

            this.move.y = 0f;

        }

        //右辺の衝突
        if(buried.x < 0 && this.move.x > 0){

            this.tempMoveX = -this.tempMoveX;

        //左辺の衝突
        }else if(buried.x > 0 && this.move.x < 0){

            this.tempMoveX = -this.tempMoveX;

        }

    }

    /**
     * @brief 落下フラグの設定を行う
     * @param bFall 落下フラグ
     */
    public void setFall(boolean bFall){

        this.bFall = bFall;

    }

}
