/**
 * @file Player.java
 * @brief ゲーム内のプレイヤークラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.call.Call;
import info.u6182.mygdx.game.myactiongame.call.EnemyCall;
import info.u6182.mygdx.game.myactiongame.call.FireBallCall;
import info.u6182.mygdx.game.myactiongame.controller.Controller;
import info.u6182.mygdx.game.myactiongame.controller.JoyStickController;
import info.u6182.mygdx.game.myactiongame.substance.anime.Anime;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;
import info.u6182.mygdx.game.myactiongame.substance.fireball.FireBall;
import info.u6182.mygdx.game.myactiongame.substance.item.Item;

import info.u6182.api.Utiility;
import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class Player
 * @brief ゲーム内のプレイヤークラス
 */
public class Player extends Anime {

    //! ファイアボールの召喚
    private FireBallCall fireBallCall;

    //! 強迫行為設定フラグ
    private boolean bObsessionCountOne = false;

    //! 強迫行為のカウント
    private int obsessionCount;

    //! 罪科
    private int guilt;

    //! 地位
    private int status;

    //! 獲得名声スコア
    private int acquistionFameScore;

    //! 表示用名声スコア
    private int showFameScore;

    //! 表示用名声総スコア
    private int sumShowFameScore;

    //! 待機アニメーション番号
    protected final int ANIME_WAIT = 0;

    //! 移動アニメーション番号
    protected final int ANIME_MOVE = 1;

    //! ジャンプアニメーション番号
    protected final int ANIME_JUMP = 2;

    //! 下降アニメーション番号
    protected final int ANIME_DOWN = 3;

    //! 強迫観念アニメーション番号
    private final int ANIME_DAMAGE = 4;

    private final int PLAYER_GOALINTERVAL = 10;

    protected EnemyCall enemyCall;

    //! 強迫観念フラグ
    protected boolean bObsession;

    //! 敵の衝突判定フラグ
    protected boolean bEnemyCollision;

    //! ゴールフラグ
    protected boolean bGoal;

    //! 終了フラグ
    protected boolean bGoalEnd;

    //! 残機
    protected int life;

    protected float maxSpeed;

    //! プレイヤーの移動量
    protected final float PLAYER_MOVE = 0.2f;

    //! プレイヤーのジャンプ力
    protected final float PLAYER_JUMP = 10f;

    //! プレイヤーの最大スピード
    protected final float PLAYER_MAXSPEED = 8f;

    /**
     * @brief コンストラクタ
     */
    public Player(){
        ;
    }

    /**
     * @brief コンストラクタ
     * @param startPos 初期座標
     * @param fameScore 初期名声スコア
     */
    public Player(Vector2 startPos, int fameScore){

        //座標の初期化
        this.pos = new Vector2(startPos);

        //移動量の初期化
        this.move = new Vector2();

        //サイズの初期化
        this.size = new Vector2(60f, 64f);

        //矩形の初期化
        this.rect = new CustomRectangle();

        //ファイアボールの初期化
        this.fireBallCall = new FireBallCall();

        //強迫行為設定フラグの初期化
        this.bObsessionCountOne = false;

        //強迫観念フラグの初期化
        this.bObsession = false;

        //敵の衝突判定フラグの初期化
        this.bEnemyCollision = false;

        //ゴールフラグの初期化
        this.bGoal = false;

        //終了フラグの初期化
        this.bGoalEnd = false;

        //獲得名声スコアの初期化
        this.acquistionFameScore = 0;

        //表示用名声総スコア
        this.sumShowFameScore = fameScore;

        //強迫行為のカウントの初期化
        this.obsessionCount = 0;

        //罪科の初期化
        this.guilt = 0;

        //地位の初期化
        for(this.status = 0; this.status < GameElement.STATUS_FAMESCORE.length - 1 && this.sumShowFameScore >= GameElement.STATUS_FAMESCORE[this.status + 1]; this.status++){


        }

        //残機の初期化
        this.life = GameElement.getGameElement().getLife();

        //表示用名声スコアの初期化
        this.showFameScore = 0;

    }

    /**
     * @brief 初期化を行う
     * @param spriteBatch スプライトバッチ
     */
    public void initialize(CustomSpriteBatch spriteBatch){

        Texture texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PLAYER);

        AnimeData[] animeData = {new AnimeData(texture,"待機", size,true, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,0.05f),
                new AnimeData(texture,"移動", size,true, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0}}, 0,70,0.05f),
                new AnimeData(texture,"上昇", size,false, new int[][]{{0,0},{1,0},{2,0}}, 0,150,0.03f),
                new AnimeData(texture,"降下", size,false, new int[][]{{3,0},{4,0},{5,0},{0,0}}, 0,150,0.03f),
                new AnimeData(texture,"ダメージ", size,false, new int[][]{{0,0}}, 480,0,0.03f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief 更新処理を行う
     * @param deltaTime デルタタイム
     */
    public void update(float deltaTime){

        //地位の設定の更新
        setStatus();

        //強迫観念の更新
        obsession();

        //モーションの更新
        changeMotion();

        //ゴール処理の更新
        if(this.bGoal){

            goalBehavior();

            return;

        }

        //死亡動作処理の更新
        deadBehavior();

        if(this.bDead || this.bDamage){

            return;

        }

        //操作処理の更新
        operation();

        //名声スコアの動作処理の更新
        fameScoreBehavior();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 操作処理を行う
     */
    protected void operation(){

        if(SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_BOSSBEGIN)){

            return;

        }

        //ファイアボールの操作処理
        callOperation();

        //移動の操作処理
        moveOperation();

        //ジャンプの操作処理
        jumpOperation();

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

    /**
     * @brief ファイアボール召喚処理を行う
     */
    protected void callOperation(){

        if(Controller.bCallButton && !this.bObsession){

            this.fireBallCall.fire(this.pos, this.size, this.bReverse);

        }

        this.fireBallCall.update();

    }

    /**
     * @brief 移動操作処理を行う
     */
    protected void moveOperation(){

        Vector2 actuator = JoyStickController.actuator;

        if(actuator.x > 0.1f && !this.bObsession) {

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

        }else if(actuator.x < -0.1f && !this.bObsession){

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
    protected void jumpOperation(){

        if(Controller.bJumpButton && !this.bJump){

            //強迫観念の状態の場合強迫行為のカウントを減らす
            if(this.bObsession){

                this.obsessionCount--;

                if(this.obsessionCount == 0){

                    this.bObsession = false;

                    this.bObsessionCountOne = false;

                }

            }

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
     * @brief 強迫観念の処理を行う
     */
    public void obsession(){

        //強迫観念の場合
        if(this.bObsession){

            if(!this.bObsessionCountOne){

                this.obsessionCount = Utiility.getObsessionCount(this.guilt);

                if(this.obsessionCount == 0){

                    this.bObsession = false;

                }

                this.bObsessionCountOne = true;

            }

        }

    }

    /**
     * @brief ゴール処理を行う
     */
    public void goalBehavior(){

        if(this.fameScore == 0){

            this.fameScore = this.showFameScore;

            SoundManager.getSoundManager().loopPlay(SoundInfo.SOUND_FAME);

        }

        if(this.showFameScore > 0){

            this.showFameScore -= PLAYER_GOALINTERVAL;

            this.sumShowFameScore += PLAYER_GOALINTERVAL;

        }else{

            this.bGoalEnd = true;

        }

    }

    /**
     * @brief 死亡動作処理を行う
     */
    public void deadBehavior(){

        //ステージの外に落ちると死亡
        if(this.pos.y + this.size.y < 0 && !this.bDead){

            this.move.set(0f, 0f);

            this.bDead = true;

            return;

        }

        //敵などに当たった場合
        if(this.bDamage){

            if(!this.bJump){

                this.move.y = PLAYER_JUMP;

                this.bJump = true;

            }

            this.move.y += GameElement.GRAVITY;

            this.move.x = 0f;

            this.pos.y += this.move.y;

            Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

            Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

            //画面下に出た場合死亡
            if(pos.y + this.size.y < 0){

                if(!this.bDead){

                    this.bDead = true;

                }

            }

        }

    }

    /**
     * @brief 名声スコアの動作処理を行う
     */
    public void fameScoreBehavior(){

        if(this.acquistionFameScore > 0){

            this.acquistionFameScore -= PLAYER_GOALINTERVAL;

            this.showFameScore += PLAYER_GOALINTERVAL;

        }

    }

    /**
     * @brief モーション変更を行う
     */
    protected void changeMotion(){

        //ダメージ状態か強迫観念の状態の場合ダメージモーション
        if(this.bDamage || this.bObsession){

            this.anime.changeAnime(ANIME_DAMAGE);

            return;

        }

        //ジャンプ上昇時に上昇モーション
        if(this.bJump && this.move.y > 0){

            this.anime.changeAnime(ANIME_JUMP);

        }else if(this.bJump && this.move.y <= 0){

            this.anime.changeAnime(ANIME_DOWN);

        }else if(this.move.x != 0){

            this.anime.changeAnime(ANIME_MOVE);

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
     * @brief 敵との衝突判定を行う
     * @param enemy 敵のインスタンス
     * @return 衝突している場合はtrueを返す
     */
    public boolean collisionEnemy(Enemy enemy){

        if(!enemy.isShow() || enemy.isDead() || this.bDamage){

            return false;

        }

        //ファイアボールと敵の衝突判定
        if(collisionShotEnemy(enemy)){

            return true;

        }

        //プレイヤーとボスの衝突判定
        if(enemy.getType() == GameElement.ENEMY_PUMPKING){

            if(collisionBoss(enemy)){

                return true;

            }

        }else{

            CustomRectangle prec = getRect();

            CustomRectangle erec = enemy.getRect();

            if(prec.overlaps(erec)){

                //敵を踏んだ場合
                if(prec.top - erec.height > erec.top && prec.right > erec.left && prec.left < erec.right){

                    if(this.bJump){

                        this.bJump = false;

                    }

                    if(this.bEnemyCollision){

                        this.bEnemyCollision = false;

                    }

                    enemy.setDead(true);

                    this.bEnemyCollision = true;

                    this.acquistionFameScore += enemy.getFameScore();

                    this.guilt++;

                }else{

                    this.bDamage = true;

                }

                return true;

            }

        }

        return false;

    }

    /**
     * @brief ボスとの衝突判定を行う
     * @param enemy 敵のインスタンス
     * @return 衝突している場合はtrueを返す
     */
    public boolean collisionBoss(Enemy enemy){

        CustomRectangle prec = getRect();

        CustomRectangle erec = enemy.getRect();

        if(prec.overlaps(erec)){

            //敵を踏んだ場合
            if(prec.top > erec.top && prec.right > erec.left && prec.left < erec.right){

                //ボスが攻撃可能な状態の場合
                if(enemy.isAttackPossible()){

                    enemy.setPlayerCollision();

                    if(this.bJump){

                        this.bJump = false;

                    }

                    if(this.bEnemyCollision){

                        this.bEnemyCollision = false;

                    }

                    this.bEnemyCollision = true;

                }else{

                    this.bDamage = true;

                }

            }else{

                this.bDamage = true;

            }

            return true;

        }

        return false;

    }

    /**
     * @brief 強迫観念との衝突判定を行う
     * @param orec 強迫観念の矩形
     * @param bShow 表示フラグ
     * @return 衝突している場合はtrueを返す
     */
    public boolean collisionObsession(CustomRectangle orec, boolean bShow){

        if(!bShow){

            return false;

        }

        CustomRectangle prec = getRect();

        //プレイヤーと強迫観念が衝突した場合
        if(prec.overlaps(orec)){

            this.bObsession = true;

            return true;

        }

        return false;

    }

    /**
     * @brief ファイアボールと敵の衝突判定を行う
     * @param enemy 敵のインスタンス
     * @return 衝突している場合はtrueを返す
     */
    public boolean collisionShotEnemy(Enemy enemy){

        FireBall fireBall = this.fireBallCall.getFireBall();

        if(!fireBall.isShow()){

            return false;

        }

        if(fireBall.collisionEnemy(enemy)){

            if(enemy.getType() == GameElement.ENEMY_PUMPKING){

                enemy.decreaseFireBallCount();

            }else{

                enemy.setDead(true);

                this.acquistionFameScore += enemy.getFameScore();

                this.guilt++;

            }

            fireBall.setShow(false);

            return true;

        }

        return false;

    }

    /**
     * @brief アイテムとの衝突判定を行う
     * @param item アイテムのインスタンス
     * @return 衝突している場合はtrueを返す
     */
    public boolean collisionItem(Item item){

        if(!item.isShow()){

            return false;

        }

        CustomRectangle prec = getRect();

        CustomRectangle irec = item.getRect();

        //プレイヤとアイテムが衝突した場合
        if(prec.overlaps(irec)){

            switch (item.getType()){

                case GameElement.ITEM_CLEARGOAL:

                    this.bGoal = true;

                    GameElement.getGameElement().setClearItem(true);

                    break;

                case GameElement.ITEM_ANOTHERBRANCHGOAL:

                    this.bGoal = true;

                    GameElement.getGameElement().setStoryClear(GameElement.BRANCHSTORY_CLEAR);

                    break;

                    default:

                        this.bGoal = true;

                        break;

            }

            item.setGetItem(true);

            item.setShow(false);

            if(this.acquistionFameScore >= 0){

                this.showFameScore += this.acquistionFameScore;

            }

            this.showFameScore += item.getFameScore();

            return true;

        }

        return false;

    }

    /**
     * @brief ステージとの衝突処理を行う
     * @param buried 埋まり値
     */
    public void collisionStage(Vector2 buried){

        //埋まり値を加算
        this.pos.add(buried);

        //下辺の衝突
        if(buried.y > 0 && this.move.y < 0){

            this.move.y = 0f;

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
     * @brief プレイヤーの描画を行う
     * @param spriteBatch スプライトバッチ
     */
    public void render(CustomSpriteBatch spriteBatch){

        //プレイヤーの描画
        this.anime.render(this.pos.x, this.pos.y, this.bReverse, this.bDamage);

        //ファイアボールの描画
        this.fireBallCall.render(spriteBatch);

    }

    /**
     * @brief 地位の設定を行う
     */
    public void setStatus(){

        int fameScore = this.sumShowFameScore + this.showFameScore;

        if(this.status < GameElement.STATUS_FAMESCORE.length - 1 && fameScore >= GameElement.STATUS_FAMESCORE[this.status + 1]){

            this.status++;

            if(this.status == GameElement.STATUS_FAMESCORE.length - 1){

                SoundManager.getSoundManager().play(SoundInfo.SOUND_STATUSKING);

            }else{

                SoundManager.getSoundManager().play(SoundInfo.SOUND_STATUSUP);

            }

        }

    }

    /**
     * @brief 矩形の取得を行う
     * @return プレイヤーの矩形
     */
    @Override
    public CustomRectangle getRect(){

        float left = 0f, buttom = 0f, right = 0f, top = 0f;

        if(this.anime.getAnimeNo() == ANIME_WAIT){

            left = 15f; buttom = 0f; right = 15f; top = 15f;

        }else if(this.anime.getAnimeNo() == ANIME_MOVE){

            left = 15f; buttom = 0f; right = 15f; top = 15f;

        }else if(this.anime.getAnimeNo() == ANIME_JUMP){

            left = 15f; buttom = 5f; right = 15f; top = 5f;

        }else if(this.anime.getAnimeNo() == ANIME_DOWN){

            left = 15f; buttom = 5f; right = 15f; top = 5f;

        }else if(this.anime.getAnimeNo() == ANIME_DAMAGE){

            left = 10f; buttom = 0f; right = 10f; top = 15f;

        }

        return this.rect.setCustomRect(this.pos.x + left, this.pos.y + buttom, this.pos.x + this.size.x - right, this.pos.y + this.size.y - top);

    }

    /**
     * @brief 残機の減少を行う
     */
    public void decreaseLife(){

        this.life--;

    }

    /**
     * @brief 名声スコアの設定を行う
     * @param fameScore 名声スコア
     */
    public void setFameScore(int fameScore){

        this.showFameScore += fameScore;

    }

    /**
     * @brief ファイアボール召喚の取得を行う
     * @return ファイアボール召喚インスタンス
     */
    public FireBallCall getFireBallCall(){

        return this.fireBallCall;

    }

    /**
     * @brief 敵召喚の取得を行う
     * @return 敵召喚インスタンス
     */
    public EnemyCall getEnemyCall(){

        return this.enemyCall;

    }

    /**
     * @brief 召喚の取得を行う
     * @return コールインスタンス
     */
    public Call getCall(){

        return this.fireBallCall;

    }

    /**
     * @brief 強迫観念かどうかを取得する
     * @return 強迫観念の場合はtrueを返す
     */
    public boolean isObsession(){

        return this.bObsession;

    }

    /**
     * @brief ゴール状態の取得を行う
     * @return ゴール状態の場合はtrueを返す
     */
    public boolean isGoal(){

        return this.bGoal;

    }

    /**
     * @brief ゴール終了状態の取得を行う
     * @return ゴール終了状態の場合はtrueを返す
     */
    public boolean isGoalEnd(){

        return this.bGoalEnd;

    }

    /**
     * @brief 罪科の取得を行う
     * @return 罪科
     */
    public int getGuilt(){

        return this.guilt;

    }

    /**
     * @brief 強迫観念カウントの取得を行う
     * @return 強迫観念カウント
     */
    public int getObsessionCount(){

        return this.obsessionCount;

    }

    /**
     * @brief 地位の取得を行う
     * @return 地位
     */
    public int getStatus(){

        return this.status;

    }

    /**
     * @brief 表示用名声総スコアの取得を行う
     * @return 表示用名声総スコア
     */
    public int getSumShowFameScore(){

        return this.sumShowFameScore;

    }

    /**
     * @brief 表示用名声スコアの取得を行う
     * @return 表示用名声スコア
     */
    public int getShowFameScore(){

        return this.showFameScore;

    }

    /**
     * @brief 残機の取得を行う
     * @return 残機
     */
    public int getLife(){

        return this.life;

    }

}
