/**
 * @file AnotherPlayer.java
 * @brief ゲーム内のアナザープレイヤークラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.effectmanager.EffectInfo;
import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.call.EnemyCall;
import info.u6182.mygdx.game.myactiongame.controller.Controller;
import info.u6182.mygdx.game.myactiongame.controller.JoyStickController;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy.AssassinWolf;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class AnotherPlayer
 * @brief ゲーム内のアナザープレイヤークラス
 */
public class AnotherPlayer extends Player {

    //! 呪いなし
    public final int CURSE_NOT = -1;

    //! ジャンプ時の移動量
    private final float ANOTHERPLAYER_MOVEJUMP = 3f;

    //! 召喚回復のインターバルタイム
    private final float ANOTHERPLAYER_CALLRECOVERYINTERVALTIME = 2.5f;

    //! 召喚回復のインターバル
    private final float ANOTHERPLAYER_CALLRECOVERYINTERVAL = 0.5f;

    //! 誘惑のインターバルタイム
    private final float ANOTHERPLAYER_TEMPTATIONINTERVALTIME = 30f;

    //! 誘惑のインターバル
    private final float ANOTHERPLAYER_TEMPTATIONINTERVAL = 0.2f;

    //! 呪いの時間
    private final float ANOTHERPLAYER_CURSETIME = 10f;

    //! 召喚座標
    private Vector2 callPos;

    //! 移動ジャンプフラグ
    private boolean bMoveJump;

    //! 誘惑フラグ
    private boolean bTemptation;

    //! 呪い
    private int curse;

    //! 敵タイプ
    private int enemyType;

    //! 召喚コスト
    private final int[] CALLCOST = {GameElement.CALLCOST_PUMPKIN, GameElement.CALLCOST_GREENPUMPKIN, GameElement.CALLCOST_GHOSTPUMPKIN, GameElement.CALLCOST_WHITEGHOST, GameElement.CALLCOST_BLACKGHOST, GameElement.CALLCOST_KINGGHOST};

    //! 召喚力
    private int callPower;

    //! 召喚力回復
    private int recoveryCallPower;

    //! 誘惑のインターバル
    private float temptationInterval;

    //! 呪いの時間
    private float curseTime;

    //! 召喚力回復のインターバル
    private float callRecoveryInterval;

    /**
     * @brief コンストラクタ
     * @param startPos 開始位置
     */
    public AnotherPlayer(Vector2 startPos){

        this.rect = new CustomRectangle();

        //座標の初期化
        this.pos = new Vector2(startPos);

        //移動量の初期化
        this.move = new Vector2();

        //サイズの初期化
        this.size = new Vector2(64f, 64f);

        this.callPos = new Vector2();

        this.bMoveJump = false;

        this.bTemptation = false;

        this.curse = CURSE_NOT;

        this.callPower = 100;

        this.recoveryCallPower = 0;

        this.enemyType = 0;

        this.temptationInterval = ANOTHERPLAYER_TEMPTATIONINTERVALTIME;

        this.curseTime = 0f;

        this.callRecoveryInterval = ANOTHERPLAYER_CALLRECOVERYINTERVALTIME;

        this.life = GameElement.getGameElement().getLife();

    }

    /**
     * @brief 初期化を行う
     * @param spriteBatch スプライトバッチ
     */
    public void initialize(CustomSpriteBatch spriteBatch){

        this.enemyCall = new EnemyCall(spriteBatch);

        Texture texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PUMPKING);

        AnimeData[] animeData = {new AnimeData(texture, "待機", this.size, true, new int[][]{{0,0},{1,0},{2,0}},0,0,0.08f), new AnimeData(texture, "移動", this.size, true, new int[][]{{0,2},{1,2},{2,2}},0,0,0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief 更新処理を行う
     * @param deltaTime デルタタイム
     */
    @Override
    public void update(float deltaTime){

        //モーションの更新
        changeMotion();

        //ゴール処理の更新
        if(this.bGoal){

            this.bGoalEnd = true;

            return;

        }

        //死亡動作処理の更新
        deadBehavior();

        if(this.bDead || this.bDamage){

            return;

        }

        //誘惑状態の動作処理の更新
        if(this.bTemptation){

            temptaionBehavior();

        }

        //呪い状態の動作処理の更新
        if(this.curse != CURSE_NOT){

            curseBehavior();

        }

        if(this.recoveryCallPower != 0){

            this.callRecoveryInterval -= ANOTHERPLAYER_CALLRECOVERYINTERVAL;

            if(this.callRecoveryInterval <= 0){

                this.callPower++;

                this.recoveryCallPower--;

                this.callRecoveryInterval = ANOTHERPLAYER_CALLRECOVERYINTERVALTIME;

            }

        }

        //操作処理の更新
        operation();

        for(int i = 0;i < this.enemyCall.getCallCount();i++){

            if(this.enemyCall.getEnemy(i).isDead()){

                continue;

            }

            if(this.enemyCall.getEnemy(i).getType() == GameElement.ENEMY_WHITEGHOST){

                //召喚した敵との衝突判定
                callCollision(i);

            }

        }

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 操作処理を行う
     */
    @Override
    protected void operation(){

        if(SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_ASSASSINWOLFBEGIN) || SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_HELLISHESTKINGBEGIN)){

            return;

        }

        //召喚の操作処理
        callOperation();

        if(this.enemyCall.isChange()){

            return;

        }

        //移動の操作処理
        moveOperation();

        //ジャンプの操作処理
        jumpOperation();

        //移動時のジャンプ処理
        if(this.move.x != 0 && !this.bMoveJump && !this.bJump){

            this.bMoveJump = true;

            this.move.y = ANOTHERPLAYER_MOVEJUMP;

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

    /**
     * @brief 召喚操作処理を行う
     */
    @Override
    protected void callOperation(){

        if(Controller.bRightFling){

            this.enemyType--;

            if(this.enemyType < 0){

                this.enemyType = GameElement.ENEMY_COUNT - 2;

            }

        }else if(Controller.bLeftFling){

            this.enemyType++;

            if(this.enemyType >= GameElement.ENEMY_COUNT - 1){

                this.enemyType = 0;

            }

        }

        if(Controller.bCallButton){

            if(!this.bTemptation && this.callPower >= this.CALLCOST[this.enemyType]){

                this.callPower -= this.CALLCOST[this.enemyType];

                Vector2 size = EffectManager.getEffectManager().getSize(EffectInfo.EFFECT_CALL);

                float ew = 0f;

                if(this.bReverse){

                    ew = -this.size.x * 2f;

                }

                SoundManager.getSoundManager().play(SoundInfo.SOUND_CALL);

                EffectManager.getEffectManager().start(EffectInfo.EFFECT_CALL, this.pos.x + ew, (this.pos.y - 32f));

                this.enemyCall.call(this.callPos.set(this.pos.x, this.pos.y + this.size.y - 16f), this.size, this.bReverse, this.enemyType);

            }

        }

        this.recoveryCallPower += this.enemyCall.update();

    }

    /**
     * @brief 移動操作処理を行う
     */
    @Override
    protected void moveOperation(){

        Vector2 actuator = JoyStickController.actuator;

        if(actuator.x > 0.1f && !this.bTemptation) {

            if (this.move.x < 0 && this.bJump) {

                this.move.x = 0f;

            }

            this.move.x += PLAYER_MOVE;

            //スティックの移動力に合わせて現在の最大スピードを決定
            float nowMaxSpeed = (Math.round(actuator.x * 100) / 100f) * PLAYER_MAXSPEED;

            //1フレーム前の最大スピードが現在の最大スピードより大きい場合現在の最大スピードに設定
            if(this.maxSpeed > nowMaxSpeed || this.maxSpeed < nowMaxSpeed){

                this.maxSpeed = nowMaxSpeed;

            }

            //現在の移動量が最大スピードより大きい場合
            if(this.move.x > this.maxSpeed) {

                //移動量を徐々に減らす
                this.move.x -= PLAYER_MOVE * 2;

                if (this.move.x <= this.maxSpeed) {

                    this.move.x = this.maxSpeed;

                }

            }

        }else if(actuator.x < -0.1f && !this.bTemptation){

            if(this.move.x > 0 && this.bJump){

                this.move.x = 0f;

            }

            this.move.x -= PLAYER_MOVE;

            float nowMaxSpeed = (Math.round(actuator.x * 100) / 100f) * PLAYER_MAXSPEED;

            //1フレーム前の最大スピードが現在の最大スピードより大きい場合現在の最大スピードに設定
            if(this.maxSpeed > nowMaxSpeed || this.maxSpeed < nowMaxSpeed){

                this.maxSpeed = nowMaxSpeed;

            }

            //現在の移動量が最大スピードより大きい場合
            if(this.move.x < this.maxSpeed) {

                //移動量を徐々に減らす
                this.move.x += PLAYER_MOVE * 2;

                if (this.move.x >= this.maxSpeed) {

                    this.move.x = this.maxSpeed;

                }

            }


        }else{

            if(this.move.x > 0){

                this.move.x -= PLAYER_MOVE;

                if(this.move.x < 0){

                    this.move.x = 0f;

                }

            }else if(this.move.x < 0){

                this.move.x += PLAYER_MOVE;

                if(this.move.x > 0){

                    this.move.x = 0f;

                }

            }

        }

    }

    /**
     * @brief ジャンプ操作処理を行う
     */
    @Override
    protected void jumpOperation(){

        if(Controller.bJumpButton && !this.bJump && !this.bTemptation){

            SoundManager.getSoundManager().play(SoundInfo.SOUND_JUMP);

            this.bJump = true;

            this.move.y = PLAYER_JUMP;

        }else if(this.bEnemyCollision && !this.bJump){

            SoundManager.getSoundManager().play(SoundInfo.SOUND_ATTACK);

            this.bJump = true;

            this.move.y = PLAYER_JUMP;

        }

    }

    /**
     * @brief 誘惑動作処理を行う
     */
    public void temptaionBehavior(){

        //誘惑エフェクトを表示
        if(!EffectManager.getEffectManager().isEffect(EffectInfo.EFFECT_TEMPTATIONSTATE)){

            Vector2 size = EffectManager.getEffectManager().getSize(EffectInfo.EFFECT_TEMPTATIONSTATE);

            EffectManager.getEffectManager().start(EffectInfo.EFFECT_TEMPTATIONSTATE, this.pos.x - size.x, this.pos.y + this.size.y);

            SoundManager.getSoundManager().play(SoundInfo.SOUND_TEMPTATIONSTATE);

        }

        this.temptationInterval -= ANOTHERPLAYER_TEMPTATIONINTERVAL;

        if(this.temptationInterval <= 0){

            this.bTemptation = false;

            this.temptationInterval = ANOTHERPLAYER_TEMPTATIONINTERVALTIME;

        }

    }

    /**
     * @brief 呪い動作処理を行う
     */
    public void curseBehavior(){

        if(!EffectManager.getEffectManager().isEffect(EffectInfo.EFFECT_CURSESTATE)){

            Vector2 size = EffectManager.getEffectManager().getSize(EffectInfo.EFFECT_CURSESTATE);

            //y位置直し
            EffectManager.getEffectManager().start(EffectInfo.EFFECT_CURSESTATE, this.pos.x - size.x, this.pos.y + this.size.y);

            SoundManager.getSoundManager().play(SoundInfo.SOUND_CURSESTATE);

        }

        this.curseTime += Gdx.graphics.getDeltaTime();

        if(this.curseTime >= ANOTHERPLAYER_CURSETIME){

            this.bDamage = true;

        }

    }

    /**
     * @brief モーションの変更を行う
     */
    @Override
    protected void changeMotion(){

        //移動時に移動モーション
        if(this.move.x != 0){

            this.anime.changeAnime(ANIME_MOVE);

        //待機時に待機モーション
        }else{

            this.anime.changeAnime(ANIME_WAIT);

        }

        if(this.move.x < 0){

            this.bReverse = true;

        }else if(this.move.x > 0){

            this.bReverse = false;

        }

    }

    /**
     * @brief 描画処理を行う
     * @param spriteBatch スプライトバッチ
     */
    @Override
    public void render(CustomSpriteBatch spriteBatch){

        //アナザープレイヤーの描画
        this.anime.render(this.pos.x, this.pos.y, this.bReverse, this.bDamage);

        //召喚の描画
        this.enemyCall.render();

    }

    /**
     * @brief 敵との衝突判定を行う
     * @param enemy 敵
     * @return 衝突した場合はtrueを返す
     */
    @Override
    public boolean collisionEnemy(Enemy enemy){

        if(enemy instanceof AssassinWolf){

            AssassinWolf assassinWolf = (AssassinWolf)enemy;

            if(assassinWolf.isDamageMotion()){

                return false;

            }

        }

        if(!enemy.isShow() || enemy.isDead() || this.bDamage){

            return false;

        }

        for(int i = 0;i < this.enemyCall.getCallCount();i++){

            if(this.enemyCall.getEnemy(i).isDead()){

                continue;

            }

            //召喚した敵と敵の衝突判定
            if(collisionShotEnemy(enemy, i)){

                return true;

            }

        }

        CustomRectangle prec = getRect();

        CustomRectangle erec = enemy.getRect();

        if(prec.overlaps(erec)){

            if(enemy.getType() == GameElement.ENEMY_ASSASSINWOLF || enemy.getType() == GameElement.ENEMY_HELLISHESTKING){

                if(!enemy.isAttackPossible()){

                    this.bDamage = true;

                    return true;

                }

            }

            float offsetSize = (this.size.y - enemy.getSize().y) <= 0 ? 10f : 0f;

            //敵を踏んだ場合
            if(prec.top - erec.height + offsetSize > erec.top && prec.right > erec.left && prec.left < erec.right){

                if(this.bJump){

                    this.bJump = false;

                }

                if(this.bEnemyCollision){

                    this.bEnemyCollision = false;

                }

                if(enemy.getType() == GameElement.ENEMY_ASSASSINWOLF || enemy.getType() == GameElement.ENEMY_HELLISHESTKING){

                    enemy.setDamage(true);

                    this.bEnemyCollision = true;

                    return true;

                }

                enemy.setDead(true);

                if(this.curse != CURSE_NOT && this.curse == enemy.getIdNo()){

                    this.curse = CURSE_NOT;

                    this.curseTime = 0f;

                }

                this.bEnemyCollision = true;

            }else{

                this.bDamage = true;

            }

            return true;

        }

        return false;

    }

    /**
     * @brief 召喚した敵との衝突判定を行う
     * @param i 召喚した敵のインデックス
     * @return 衝突した場合はtrueを返す
     */
    public boolean callCollision(int i){

        CustomRectangle prec = getRect();

        CustomRectangle erec = this.enemyCall.getEnemy(i).getRect();

        if(prec.overlaps(erec)){

            //敵を踏んだ場合
            if(prec.top - erec.height > erec.top && prec.right > erec.left && prec.left < erec.right){

                if(this.bJump){

                    this.bJump = false;

                }

                if(this.bEnemyCollision){

                    this.bEnemyCollision = false;

                }

                this.enemyCall.getEnemy(i).setDead(true);

                this.bEnemyCollision = true;

            }else{

                this.enemyCall.getEnemy(i).setDead(true);

                return false;

            }

        }

        return true;

    }

    /**
     * @brief 召喚した敵と他の敵の衝突判定を行う
     * @param enemy 敵
     * @param i 召喚した敵のインデックス
     * @return 衝突した場合はtrueを返す
     */
    public boolean collisionShotEnemy(Enemy enemy, int i){

        CustomRectangle srec = this.enemyCall.getEnemy(i).getRect();

        CustomRectangle erec = enemy.getRect();

        if(srec.overlaps(erec)){

            SoundManager.getSoundManager().play(SoundInfo.SOUND_ATTACK);

            if(enemy.getType() == GameElement.ENEMY_ASSASSINWOLF || enemy.getType() == GameElement.ENEMY_HELLISHESTKING){

                if(enemy.isAttackPossible()){

                    enemy.setDamage(true);

                    this.enemyCall.getEnemy(i).setDead(true);

                    return true;

                }

                this.enemyCall.getEnemy(i).setDead(true);

                return true;

            }

            enemy.setDead(true);

            this.enemyCall.getEnemy(i).setDead(true);

            if(this.curse != CURSE_NOT && this.curse == enemy.getIdNo()){

                this.curse = CURSE_NOT;

                this.curseTime = 0f;

                SoundManager.getSoundManager().stop(SoundInfo.SOUND_CURSESTATE);

            }

            return true;

        }

        return false;

    }

    /**
     * @brief ステージとの衝突処理を行う
     * @param buried 埋まり量
     */
    @Override
    public void collisionStage(Vector2 buried){

        //埋まり値を加算
        this.pos.add(buried);

        //下辺の衝突
        if(buried.y > 0 && this.move.y < 0){

            this.move.y = 0f;

            if(this.bMoveJump){

                this.bMoveJump = false;

            }

            if(this.bJump){

                this.bJump = false;

            }

            if(this.bEnemyCollision){

                this.bEnemyCollision = false;

            }

            //上辺の衝突
        }else if(buried.y < 0 && this.move.y > 0){

            this.move.y = 0f;

        }

        //右辺の衝突
        if(buried.x < 0 && this.move.x > 0){

            this.move.x = 0f;

            //左辺の衝突
        }else if(buried.x > 0 && this.move.x < 0){

            this.move.x = 0f;

        }

    }

    /**
     * @brief 誘惑フラグの設定を行う
     * @param bTemptation 誘惑フラグ
     */
    public void setTemptation(boolean bTemptation){

        this.bTemptation = bTemptation;

    }

    /**
     * @brief 呪いの設定を行う
     * @param idNo 呪いのID番号
     */
    public void setCurse(int idNo){

        this.curse = idNo;

    }

    /**
     * @brief 召喚力の追加を行う
     * @param callPower 追加する召喚力
     */
    public void addCallPower(int callPower){

        this.callPower += callPower;

    }

    /**
     * @brief 矩形の取得を行う
     * @return 矩形
     */
    @Override
    public CustomRectangle getRect(){

        return this.rect.set(this.pos, this.size);

    }

    /**
     * @brief 敵の種類の取得を行う
     * @return 敵の種類
     */
    public int getEnemyType(){

        return this.enemyType;

    }

    /**
     * @brief 召喚力の取得を行う
     * @return 召喚力
     */
    public int getCallPower(){

        return this.callPower;

    }

    /**
     * @brief 呪いの取得を行う
     * @return 呪いのID番号
     */
    public int getCurse(){

        return this.curse;

    }

}
