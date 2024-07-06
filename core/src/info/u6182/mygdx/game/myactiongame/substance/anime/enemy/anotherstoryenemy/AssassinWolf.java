/**
 * @file AssassinWolf.java
 * @brief ゲーム内のアサシン・ウルフクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.effectmanager.EffectInfo;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.AnotherPlayer;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.Player;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.soundmanager.SoundManager;

/**
 * @class AssassinWolf
 * @brief ゲーム内のアサシン・ウルフクラス
 */
public class AssassinWolf extends Enemy {

    //! アサシン・ウルフのライフ
    private final int ASSASSINWOLF_LIFE = 2;

    //! アサシン・ウルフの行動カウント
    private final int ASSASSINWOLF_ACTIONCOUNT = 2;

    //! アニメーション状態: 待機
    private final int ANIME_WAIT = 0;

    //! アニメーション状態: 移動
    private final int ANIME_MOVE = 1;

    //! アニメーション状態: 攻撃
    private final int ANIME_ATTACK = 2;

    //! アニメーション状態: 攻撃可能
    private final int ANIME_ATTACKPOSSIBLE = 3;

    //! アニメーション状態: ダメージ
    private final int ANIME_DAMAGE = 4;

    //! アサシン・ウルフのキックジャンプ力
    private final float ASSASSINWOLF_KICKJUMP = 7f;

    //! アサシン・ウルフのスピード
    private final float ASSASSINWOLF_SPEED = 8f;

    //! アサシン・ウルフの最大スピード
    private final float ASSASSINWOLF_MAXSPEED = 15f;

    //! アサシン・ウルフの透明度
    private final float ASSASSINWOLF_TRANSPARENCY = 0.3f;

    //! シャドウの矩形
    private CustomRectangle shadowRect;

    //! シャドウの座標
    private Vector2 shadowPos;

    //! エフェクトの座標
    private Vector2 effectPos;

    //! シャドウウルフフラグ
    private boolean bShadowWolf;

    //! アサシンキックフラグ
    private boolean bAssasinKick;

    //! 攻撃可能フラグ
    private boolean bAttackPossible;

    //! ダメージモーションフラグ
    private boolean bDamageMotion;

    //! ライフ
    private int life;

    //! エッジタッチカウント
    private int edgeTouchCount;

    //! 攻撃状態
    private int attack;

    //! 透明度
    private float tracsparency;

    //! シャドウ透明度
    private float shadowTransparency;

    //! 攻撃位置X座標
    private float attackPosX;

    //! インターバル
    private float interval;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     * @param idNo 個体識別番号
     */
    public AssassinWolf(Texture texture, Vector2 pos, int type, int idNo){

        super(pos, type);

        //サイズの初期化
        this.size = new Vector2(61f, 47f);

        //移動量の初期化
        this.move = new Vector2(0f, -ENEMY_SPEED);

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        this.shadowRect = new CustomRectangle();

        this.shadowPos = new Vector2(viewSize.x - this.pos.x - this.size.x, this.pos.y);

        this.effectPos = new Vector2();

        this.bReverse = false;

        this.bShadowWolf = false;

        this.bAssasinKick = false;

        this.bAttackPossible = false;

        //個体識別番号の設定
        this.idNo = idNo;

        this.life = ASSASSINWOLF_LIFE;

        this.edgeTouchCount = 0;

        this.attack = 0;

        this.tracsparency = 1f;

        this.shadowTransparency = 0f;

        //テスト
        this.attackPosX = 460;

        this.interval = 30f;

        AnimeData[] animeData = {new AnimeData(texture, "待機", this.size, true, new int[][]{{0,4},{1,4},{2,4}},0,0, 7),
                new AnimeData(texture, "移動", this.size, true, new int[][]{{0,1},{1,1},{2,1}},0,0, 4),
                new AnimeData(texture, "攻撃", this.size, false, new int[][]{{2,8},{1,8},{2,9},{1,9},{0,9}},0,0, 5),
                new AnimeData(texture, "攻撃可能", this.size, true, new int[][]{{0,6},{1,6},{2,6}},0,0, 8),
                new AnimeData(texture, "ダメージ", this.size, false, new int[][]{{0,5},{1,5}},0,0, 7),};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData);

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

            this.shadowTransparency -= 0.01f;

            if(this.shadowTransparency <= 0){

                this.shadowTransparency = 0;

            }

            return;

        }

        //行動処理の更新
        behavior(player);

        if(this.attack == 1){

            EffectManager.getEffectManager().start(EffectInfo.EFFECT_WARRIORATTACK, this.effectPos);

            SoundManager.getSoundManager().play(SoundInfo.SOUND_SEVER);

            this.attack++;

        }

        //モーションの更新
        changeMotion();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 敵の行動処理を行う
     * @param player プレイヤー
     */
    private void behavior(Player player){

        if(SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_ASSASSINWOLFBEGIN)){

            return;

        }

        if(this.life <= 0){

            this.bDead = true;

            GameElement.getGameElement().setClearItem(true);

        }

        if(this.bDamage){

            this.life--;

            this.bAttackPossible = false;

            this.bShadowWolf = true;

            this.bDamage = false;

            this.bDamageMotion = true;

        }

        if(this.life == 1){

            if(this.bAttackPossible){

                this.shadowTransparency -= 0.01f;

                if(this.shadowTransparency <= 0){

                    this.shadowTransparency = 0f;

                }

            }

        }

        if(this.bDamageMotion){

            damageState();

            return;

        }

        if(this.bAttackPossible){

            attackPossible();

        }else if(this.edgeTouchCount == 0 && this.tracsparency != ASSASSINWOLF_TRANSPARENCY){

            through();

        }

        assasinKick();

        edgeTouch();

        if(this.bShadowWolf && !this.bAttackPossible){

            shadowBehavior(player);

        }

        if(getRect().overlaps(player.getRect()) && (!this.bAttackPossible && !this.bDamageMotion)){

            if(this.attack == 0){

                this.attack++;

                this.effectPos.set(this.pos);

            }

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

    /**
     * @brief シャドウウルフの行動処理を行う
     * @param player プレイヤー
     */
    private void shadowBehavior(Player player){

        AnotherPlayer anotherPlayer = null;

        if(player instanceof AnotherPlayer){

            anotherPlayer = (AnotherPlayer) player;

        }

        if(this.getShadowRect().overlaps(anotherPlayer.getRect())){

            anotherPlayer.setDamage(true);

            if(this.attack == 0){

                this.attack++;

                this.effectPos.set(this.shadowPos);

            }

        }

        for(Enemy callEnemy : anotherPlayer.getEnemyCall().getEnemyList()){

            CustomRectangle crec = callEnemy.getRect();

            if(this.getShadowRect().overlaps(crec)){

                callEnemy.setDead(true);

            }

        }

        shadowThrough();

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        this.shadowPos.set(viewSize.x - this.pos.x - this.size.x, this.pos.y);

    }

    /**
     * @brief 通過処理を行う
     */
    private void through(){

        this.tracsparency -= Gdx.graphics.getDeltaTime();

        if(this.tracsparency <= ASSASSINWOLF_TRANSPARENCY){

            this.tracsparency = ASSASSINWOLF_TRANSPARENCY;

            this.move.x = -ASSASSINWOLF_SPEED;

        }

    }

    /**
     * @brief シャドウの通過処理を行う
     */
    private void shadowThrough(){

        this.shadowTransparency += 0.01f;

        if(this.shadowTransparency >= ASSASSINWOLF_TRANSPARENCY){

            this.shadowTransparency = ASSASSINWOLF_TRANSPARENCY;

        }

    }

    /**
     * @brief 攻撃可能状態の処理を行う
     */
    private void attackPossible(){

        this.interval -= 0.1f;

        if(this.interval <= 0){

            this.bAttackPossible = false;

        }

    }

    /**
     * @brief アサシンキックの処理を行う
     */
    private void assasinKick(){

        if(!this.bJump && !this.bAssasinKick && this.pos.x < this.attackPosX){

            this.move.y = ASSASSINWOLF_KICKJUMP;

            this.bJump = true;

            this.bAssasinKick = true;

        }

    }

    /**
     * @brief エッジタッチの処理を行う
     */
    private void edgeTouch(){

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        if(this.pos.x < 0 || this.pos.x + this.size.x > viewSize.x){

            this.move.x = -this.move.x;

            this.edgeTouchCount++;

        }

        if(this.edgeTouchCount == 1){

            this.move.x = ASSASSINWOLF_MAXSPEED;

            this.tracsparency += Gdx.graphics.getDeltaTime();

            if(this.tracsparency >= 1){

                this.tracsparency = 1f;

            }

        }else if(this.edgeTouchCount == ASSASSINWOLF_ACTIONCOUNT && this.pos.x < this.startPos.x){

            this.pos.x = this.startPos.x;

            this.move.x = 0f;

            this.bAssasinKick = false;

            this.bAttackPossible = true;

            this.edgeTouchCount = 0;

            this.interval = 30f;

        }

    }

    /**
     * @brief ダメージ状態の処理を行う
     */
    private void damageState(){

        if(!this.bJump){

            this.move.y = ENEMY_JUMP;

            this.bJump = true;

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        if(this.pos.x + this.size.x < viewSize.x){

            this.move.y += GameElement.GRAVITY;

            this.pos.x += 0.2f;
            this.pos.y += this.move.y;

        }

    }

    /**
     * @brief モーションの変更処理を行う
     */
    @Override
    public void changeMotion(){

        if(this.bDamageMotion){

            this.anime.changeAnime(ANIME_DAMAGE);

        }else if(this.bJump){

            this.anime.changeAnime(ANIME_ATTACK);

        }else if(this.bAttackPossible){

            this.anime.changeAnime(ANIME_ATTACKPOSSIBLE);

        }else if(this.move.x != 0){

            if(this.move.x > 0){

                this.bReverse = true;

            }else if(this.move.x < 0){

                this.bReverse = false;

            }

            this.anime.changeAnime(ANIME_MOVE);

        }else if(this.move.x == 0){

            this.anime.changeAnime(ANIME_WAIT);

        }

    }

    /**
     * @brief 敵の描画処理を行う
     */
    @Override
    public void render(){

        if(!this.bShow){

            return;

        }

        //アサシン・ウルフの描画
        this.anime.render(this.pos.x, this.pos.y, this.bReverse, this.bDead, this.tracsparency);

        if(this.bShadowWolf){

            //シャドウアサシン・ウルフの描画
            this.anime.render(this.shadowPos.x, this.shadowPos.y, !this.bReverse, this.bDead, this.shadowTransparency);

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

            if(this.bDamageMotion){

                this.bDamageMotion = false;

                this.bAttackPossible = false;

            }

        //上辺の衝突
        }else if(buried.y < 0 && this.move.y > 0){

            this.move.y = 0f;

        }

        //右辺の衝突
        if(buried.x < 0 && this.move.x > 0){

            this.move.x = -this.move.x;

        //左辺の衝突
        }else if(buried.x > 0 && this.move.x < 0){

            this.move.x = -this.move.x;

        }

    }

    /**
     * @brief シャドウの矩形の取得を行う
     * @return シャドウの矩形を返す
     */
    public CustomRectangle getShadowRect(){

        return this.shadowRect.set(this.shadowPos, this.size);

    }

    /**
     * @brief 攻撃可能かどうかの判定を行う
     * @return 攻撃可能かどうか
     */
    @Override
    public boolean isAttackPossible(){

        return this.bAttackPossible;

    }

    /**
     * @brief ダメージモーションフラグの取得を行う
     * @return ダメージモーションフラグを返す
     */
    public boolean isDamageMotion(){

        return this.bDamageMotion;

    }

}
