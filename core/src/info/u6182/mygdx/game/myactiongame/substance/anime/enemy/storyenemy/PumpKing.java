/**
 * @file PumpKing.java
 * @brief ゲーム内のパンプキングクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy;

import info.u6182.api.effectmanager.EffectInfo;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.Player;


import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.soundmanager.SoundManager;

/**
 * @class PumpKing
 * @brief ゲーム内のパンプキングクラス
 */
public class PumpKing extends Enemy {

    //! 全エフェクトの終了フラグ
    private boolean bEffectEnd;

    //! 座標を初期座標に設定するフラグ
    private boolean bSetPos;

    //! 初期座標移動時のジャンプフラグ
    private boolean bSetJump;

    //! 休憩フラグ
    private boolean bBreak;

    //! ダメージ後の休憩フラグ
    private boolean bCollisionBreak;

    //! プレイヤーにダメージを与えられた時のフラグ
    private boolean bPlayerCollision;

    //! ダメージ可能フラグ
    private boolean bAttackPossible;

    //! 第一形態フラグ
    private boolean bForm1;

    //! 第二形態フラグ
    private boolean bForm2;

    //! 攻撃されたファイアボールの数
    private int fireBallCount;

    //! 残機
    private int life;

    //! ステージ端に到達した回数
    private int edgeTouchCount;

    //! 爆破エフェクトの回数
    private int effectCount;

    //! 待機アニメーション番号
    public final int ANIME_WAIT = 0;

    //! 移動アニメーション番号
    public final int ANIME_MOVE = 1;

    //! 行動番号1
    public final int PUMPKING_BEHAVIOR1 = 3;

    //! 行動番号2
    public final int PUMPKING_BEHAVIOR2 = 2;

    //! 行動番号3
    public final int PUMPKING_BEHAVIOR3 = 1;

    //! パンプキングの残機
    private final int PUMPKING_LIFE = 3;

    //! パンプキングのファイアボールカウント
    private final int PUMPKING_FIREBALLCOUNT = 3;

    //! パンプキングの移動回数
    private final int PUMPKING_ACTIONCOUNT = 2;

    //! パンプキングの爆破カウント
    private final int PUMPKING_DEFEATCOUNT = 3;

    //! 休憩中のインターバル
    private float breakInterval;

    //! ダメージ後のインターバル
    private float damageInterval;

    //! ダメージ可能中のインターバル
    private float collisionBreakInterval;

    //! パンプキングの爆破座標の基準値
    private final float PUMPKING_DEFEATOFFSET = 100f;

    //! パンプキングのインターバルタイム
    private final float PUMPKIN_INTERVALTIME = 200f;

    //! パンプキングの最大スピード
    private final float PUMPKING_MAXSPEED = -5f;

    //! パンプキングの最大ジャンプ力
    private final float PUMPKING_MAXJUMP = 30f;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     */
    public PumpKing(Texture texture, Vector2 pos, int type){

        super(pos, type);

        //サイズの初期化
        this.size = new Vector2(64f, 64f);

        //移動量の初期化
        this.move = new Vector2(-ENEMY_SPEED, 0f);

        //全エフェクトの終了フラグの初期化
        this.bEffectEnd = false;

        //座標を初期座標に設定するフラグの初期化
        this.bSetPos = false;

        //初期座標移動時のジャンプフラグの初期化
        this.bSetJump = false;

        //休憩フラグの初期化
        this.bBreak = false;

        //ダメージ後の休憩フラグの初期化
        this.bCollisionBreak = false;

        //プレイヤーにダメージを与えられた時のフラグの初期化
        this.bPlayerCollision = false;

        //ダメージ可能フラグの初期化
        this.bAttackPossible = false;

        //第一形態フラグの初期化
        this.bForm1 = true;

        //第二形態フラグの初期化
        this.bForm2 = false;

        //名声スコアの初期化
        this.fameScore = GameElement.FAMESCORE_PUMPKING;

        //攻撃されたファイアボールの数の初期化
        this.fireBallCount = PUMPKING_FIREBALLCOUNT;

        //残機の初期化
        this.life = PUMPKING_LIFE;

        //ステージ端に到達した回数の初期化
        this.edgeTouchCount = 0;

        //爆破エフェクトの回数の初期化
        this.effectCount = 0;

        //休憩中のインターバルの初期化
        breakInterval = PUMPKIN_INTERVALTIME;

        //ダメージ後のインターバルの初期化
        damageInterval = PUMPKIN_INTERVALTIME;

        //ダメージ可能中のインターバルの初期化
        collisionBreakInterval = PUMPKIN_INTERVALTIME;


        AnimeData[] animeData = {new AnimeData(texture, "待機", this.size, true, new int[][]{{0,0},{1,0},{2,0}},0,0,0.08f), new AnimeData(texture, "移動", this.size, true, new int[][]{{0,2},{1,2},{2,2}},0,0,0.08f)};

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

        //行動処理の更新
        behavior();

        //モーションの更新
        changeMotion();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 行動処理を行う
     */
    @Override
    public void behavior(){

        if(SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_BOSSBEGIN)){

            return;

        }

        //爆破エフェクト
        if(this.life <= 0){

            if(!EffectManager.getEffectManager().isEffect(EffectInfo.EFFECT_DEFEAT)){

                this.effectCount++;

                Vector2 defeatPos = new Vector2(this.pos.x - this.size.x * 0.5f, this.pos.y - this.size.y * 0.5f);

                switch (this.effectCount){

                    case 1:

                        defeatPos.x -= PUMPKING_DEFEATOFFSET;

                        break;

                    case 2:

                        defeatPos.y += PUMPKING_DEFEATOFFSET;

                        break;

                    case 3:

                        defeatPos.x += PUMPKING_DEFEATOFFSET;

                        break;

                        default:

                            this.bEffectEnd = true;

                            break;

                }

                if(this.effectCount <= PUMPKING_DEFEATCOUNT){

                    SoundManager.getSoundManager().play(SoundInfo.SOUND_PUMPKINGBLASTING);

                    EffectManager.getEffectManager().start(EffectInfo.EFFECT_DEFEAT, defeatPos);

                }

            }

            //爆破エフェクトの終了
            if(this.bEffectEnd){

                this.bDead = true;

                GameElement.getGameElement().setStoryClear(GameElement.STORY_CLEAR);

            }

            return;

        }

        //ボスの着地音
        if(this.move.y == 0 && !this.bAttackPossible && !this.bBreak && !this.bCollisionBreak){

            SoundManager.getSoundManager().play(SoundInfo.SOUND_PUMPKINGSTEPON);

        }

        //ファイアボールをカウント分当てられた場合攻撃可能状態
        if(this.fireBallCount == 0){

            this.breakInterval = PUMPKIN_INTERVALTIME;

            if(this.bBreak){

                this.bBreak = false;

                this.edgeTouchCount = 0;

            }

            this.bAttackPossible = true;

        }

        //攻撃可能状態
        if(this.bAttackPossible){

            attackPossible();

        }else{

            //パンプキングが往復し地面に着地した時の待機処理
            if(this.edgeTouchCount == PUMPKING_ACTIONCOUNT && this.move.y == 0){

                this.bBreak = true;

                pumpKingWait();

                return;

            }

            //プレイヤーからの攻撃後の待機処理
            if(this.bCollisionBreak){

                collisionWait();

                return;

            }

            //プレイヤーからの攻撃後の待機処理後の出現座標に戻る処理
            if(this.bSetPos){

                ressetPos();

                return;

            }

            //ボスの行動処理
            switch (this.life){

                case PUMPKING_BEHAVIOR1:

                    behavior1();

                    break;

                case PUMPKING_BEHAVIOR2:

                    behavior2();

                    break;

                case PUMPKING_BEHAVIOR3:

                    behavior3();

                    break;

            }

        }

    }

    /**
     * @brief 第一形態の行動処理を行う
     */
    public void behavior1(){

        if(this.bForm1){

            this.move.x = -ENEMY_SPEED;

            this.bForm1 = false;

        }

        if(!this.bJump){

            this.move.y = ENEMY_JUMP;

            this.bJump = true;

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        if(this.pos.x < 0 || this.pos.x + this.size.x > viewSize.x){

            this.edgeTouchCount++;

            this.move.x = -this.move.x;

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

    /**
     * @brief 第二形態の行動処理を行う
     */
    public void behavior2(){

        if(!this.bJump){

            this.move.y = PUMPKING_MAXJUMP;

            this.bJump = true;

        }

        this.move.y += GameElement.GRAVITY;

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        if(this.pos.x < 0 || this.pos.x + this.size.x > viewSize.x){

            this.edgeTouchCount++;

            this.move.x = -this.move.x;

        }

        this.pos.add(this.move);

    }

    /**
     * @brief 第三形態の行動処理を行う
     */
    public void behavior3(){

        if(!this.bForm2 && this.edgeTouchCount == 0){

            this.move.x = PUMPKING_MAXSPEED;

            this.bForm2 = true;

            this.bForm1 = false;

        }

        if(!this.bForm1 && this.edgeTouchCount == 1){

            this.move.x = ENEMY_SPEED;

            if(this.move.x < 0){

                this.move.x = -this.move.x;

            }

            this.bForm1 = true;

            this.bForm2 = false;

        }

        if(this.edgeTouchCount == 0){

            if(!this.bJump){

                this.move.y = ENEMY_JUMP;

                this.bJump = true;

            }

        }else{

            if(!this.bJump){

                this.move.y = PUMPKING_MAXJUMP;

                this.bJump = true;

            }

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        if(this.pos.x < 0 || this.pos.x + this.size.x > viewSize.x){

            this.edgeTouchCount++;

            this.move.x = -this.move.x;

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

    /**
     * @brief パンプキングの待機処理を行う
     */
    public void pumpKingWait(){

        if(this.bBreak){

            this.breakInterval--;

            if(this.move.x > 0){

                this.move.x = -this.move.x;

            }

            if(this.breakInterval <= 0){

                this.breakInterval = PUMPKIN_INTERVALTIME;

                this.bBreak = false;

                this.edgeTouchCount = 0;

            }

        }

    }

    /**
     * @brief 初期座標に戻る処理を行う
     */
    public void ressetPos(){

        if(!this.bSetJump){

            this.move.y = PUMPKING_MAXJUMP;

            this.bSetJump = true;

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        if(this.pos.y > viewSize.y){

            if(this.move.x > 0){

                this.move.x = -this.move.x;

            }

            this.pos.x = this.startPos.x;

        }

        if(this.move.y == 0){

            this.bSetJump = false;

            this.bSetPos = false;

            if(this.edgeTouchCount > 0){

                this.edgeTouchCount = 0;

            }

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.y += this.move.y;

    }

    /**
     * @brief 衝突後の待機処理を行う
     */
    public void collisionWait(){

        this.fireBallCount = PUMPKING_FIREBALLCOUNT;

        this.collisionBreakInterval--;

        if(this.collisionBreakInterval <= 0){

            this.bCollisionBreak = false;

            this.collisionBreakInterval = PUMPKIN_INTERVALTIME;

            this.bSetPos = true;

        }

    }

    /**
     * @brief 攻撃可能状態の処理を行う
     */
    public void attackPossible(){

        //空中でも落ちるように
        this.move.y += GameElement.GRAVITY;

        this.pos.y += this.move.y;

        this.damageInterval--;

        //プレイヤーが踏んだら
        if(this.bPlayerCollision){

            this.bCollisionBreak = true;

            this.bPlayerCollision = false;

            this.bAttackPossible = false;

            this.fireBallCount = PUMPKING_FIREBALLCOUNT;

            this.life--;

            this.damageInterval = PUMPKIN_INTERVALTIME;

        }

        if(this.damageInterval <= 0){

            this.bAttackPossible = false;

            this.fireBallCount = PUMPKING_FIREBALLCOUNT;

            this.damageInterval = PUMPKIN_INTERVALTIME;

        }

    }

    /**
     * @brief モーションの変更処理を行う
     */
    @Override
    public void changeMotion(){

        if(this.bAttackPossible || this.bCollisionBreak){

            this.anime.changeAnime(ANIME_WAIT);

        }else{

            if(this.move.x < 0){

                this.bReverse = true;

            }else if(this.move.x > 0){

                this.bReverse = false;

            }

            this.anime.changeAnime(ANIME_MOVE);

        }

    }

    /**
     * @brief 描画処理を行う
     */
    @Override
    public void render(){

        if(!this.bShow){

            return;

        }

        boolean bReverseHeight = (this.bDead || this.bAttackPossible) ? true : false;

        this.anime.render(this.pos.x, this.pos.y, this.bReverse, bReverseHeight);

    }

    /**
     * @brief ファイアボールカウントの減少を行う
     */
    @Override
    public void decreaseFireBallCount(){

        this.fireBallCount--;

    }

    /**
     * @brief 残機の減少を行う
     */
    @Override
    public void decreaseLife(){

        this.life--;

    }

    /**
     * @brief プレイヤー衝突フラグの設定を行う
     */
    @Override
    public void setPlayerCollision(){

        this.bPlayerCollision = true;

    }

    /**
     * @brief 攻撃可能状態かどうかの判定を行う
     * @return 攻撃可能状態ならtrueを返す
     */
    @Override
    public boolean isAttackPossible(){

        return this.bAttackPossible;

    }

}
