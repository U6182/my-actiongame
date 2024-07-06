/**
 * @file HellishestKing.java
 * @brief ゲーム内の非道の王敵クラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.effectmanager.EffectInfo;
import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.AnotherPlayer;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.Player;

import info.u6182.api.Utiility;
import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.soundmanager.SoundManager;

/**
 * @class HellishestKing
 * @brief ゲーム内の非道の王クラス
 */
public class HellishestKing extends Enemy {

    //! 状態カウント
    private final int HELLISHESTKING_STATUSCOUNT = 15;

    //! 魔法位置カウント
    private final int HELLISHESTKING_MAGICPOSCOUNT = 6;

    //! 魔法カウント
    private final int HELLISHESTKING_MAGICCOUNT = 10;

    //! アニメーション状態: 待機
    private final int ANIME_WAIT = 0;

    //! アニメーション状態: ダメージ
    private final int ANIME_DAMAGE = 1;

    //! インターバル時間
    private final float HELLISHESTKING_INTERVALTIME = 5f;

    //! インターバル
    private final float HELLISHESTKING_INTERVAL = 0.003921569f;

    //! 魔法インターバル時間1
    private final float HELLISHESTKING_MAGICINTERVALTIME1 = 3f;

    //! 魔法インターバル時間2
    private final float HELLISHESTKING_MAGICINTERVALTIME2 = 1f;

    //! 魔法インターバル時間3
    private final float HELLISHESTKING_MAGICINTERVALTIME3 = 0f;

    //! 活動インターバル時間
    private final float HELLISHESTKING_ACTIVITYINTERVALTIME = 0.5f;

    //! 岩のインターバル時間
    private final float HELLISHESTKING_ROCKINTERVALTIME = 10f;

    //! 岩のインターバル
    private final float HELLISHESTKING_ROCKINTERVAL = 2f;

    //! 岩のオフセット
    private final float HELLISHESTKING_ROCKOFFSET = 25f;

    //! 魔法エフェクトの配列
    private final String[] MAGIC = {EffectInfo.EFFECT_HELLISHESTKING_ROCKWALL, EffectInfo.EFFECT_HELLISHESTKING_EXPLOSIONWALL, EffectInfo.EFFECT_HELLISHESTKING_WINDBEAST,
            EffectInfo.EFFECT_MAGETHUNDERWALL, EffectInfo.EFFECT_MAGEICEWALL,
            EffectInfo.EFFECT_HELLISHESTKING_EXPLOSION, EffectInfo.EFFECT_HELLISHESTKING_WINDMAGIC, EffectInfo.EFFECT_HELLISHESTKING_THUNDERMAGIC,
            EffectInfo.EFFECT_HELLISHESTKING_ICEMAGIC, EffectInfo.EFFECT_ROCKWALL};

    //! 魔法サウンドの配列
    private final String[] MAGIC_SOUND = {SoundInfo.SOUND_ROCKWALL, SoundInfo.SOUND_EXPLOSIONWALL, SoundInfo.SOUND_WINDBEAST, SoundInfo.SOUND_THUNDERWALL,
            SoundInfo.SOUND_ICEWALL,
            SoundInfo.SOUND_EXPLOSION, SoundInfo.SOUND_WINDMAGIC, SoundInfo.SOUND_THUNDERMAGIC, SoundInfo.SOUND_ICEMAGIC, SoundInfo.SOUND_ROCKWALL};

    //! 魔法位置の配列
    private final Vector2[] MAGIC_POS = {new Vector2(512f, 608f), new Vector2(48f, 448f), new Vector2(240f, 320f), new Vector2(976f, 448f), new Vector2(784f, 320f), new Vector2(0f, 96f)};

    //! 攻撃可能フラグ
    private boolean bAttackPossible;

    //! エゴフラグ
    private boolean bEgo;

    //! 魔法状態
    private int magicState;

    //! 魔法カウント
    private int magicCount;

    //! 魔法位置番号
    private int magicPosNo;

    //! 魔法タイプ番号
    private int magicTypeNo;

    //! 状態
    private int status;

    //! エゴインターバル
    private float egoInterval;

    //! 魔法インターバル
    private float magicInterval;

    //! 活動インターバル
    private float activityInterval;

    //! 岩のインターバル
    private float rockInterval;

    //! エゴ
    private float ego;

    //! エゴ状態
    private float[] egoState;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     * @param idNo 個体識別番号
     */
    public HellishestKing(Texture texture, Vector2 pos, int type, int idNo){

        super(pos, type);

        //サイズの初期化
        this.size = new Vector2(60f, 64f);

        //移動量の初期化
        this.move = new Vector2(0f, -ENEMY_SPEED);

        this.bAttackPossible = true;

        this.bEgo = false;

        this.magicState = 0;

        this.magicCount = 0;

        //個体識別番号の設定
        this.idNo = idNo;

        this.status = 0;

        this.egoInterval = HELLISHESTKING_INTERVALTIME;

        this.magicInterval = HELLISHESTKING_MAGICINTERVALTIME1;

        this.activityInterval = HELLISHESTKING_ACTIVITYINTERVALTIME;

        this.rockInterval = HELLISHESTKING_ROCKINTERVALTIME;

        this.ego = 1f;

        this.egoState = new float[4];

        this.egoState[3] = 1f;

        AnimeData[] animeData = {new AnimeData(texture, "待機", this.size, true, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}},0,0, 5),
                new AnimeData(texture, "ダメージ", this.size, false, new int[][]{{0,0}},480,0, 20),};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData);

    }

    /**
     * @brief 初期化処理を行う
     */
    private void initialize(){

        this.magicPosNo = Utiility.getRandomInt(HELLISHESTKING_MAGICPOSCOUNT);

        float offsetX = 0f;

        switch (this.magicPosNo){

            case 0:

                this.magicTypeNo = Utiility.getRandomInt(5);

                break;

            case HELLISHESTKING_MAGICPOSCOUNT - 1:

                this.magicTypeNo = HELLISHESTKING_MAGICCOUNT - 1;

                Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

                offsetX = viewSize.x * 0.5f;

                break;

                default:

                    this.magicTypeNo = Utiility.getRandomInt(5, HELLISHESTKING_MAGICCOUNT - 1);

                    break;

        }

        Vector2 magicSize = EffectManager.getEffectManager().getSize(EffectInfo.EFFECT_HELLISHESTKING_MAGIC);

        EffectManager.getEffectManager().start(EffectInfo.EFFECT_HELLISHESTKING_MAGIC, this.MAGIC_POS[this.magicPosNo].x - magicSize.x * 0.5f + offsetX, this.MAGIC_POS[this.magicPosNo].y - magicSize.y * 0.5f);

        SoundManager.getSoundManager().play(SoundInfo.SOUND_MAGIC);

        this.magicState++;

        if(this.status > 13 || this.status <= 4){

            this.magicInterval = HELLISHESTKING_MAGICINTERVALTIME1;

        }else if(this.status > 4 && this.status <= 9){

            this.magicInterval = HELLISHESTKING_MAGICINTERVALTIME2;

        }else{

            this.magicInterval = HELLISHESTKING_MAGICINTERVALTIME3;

        }

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

        if(this.bDamage){

            this.bDead = true;

        }

        if(this.bAttackPossible){

            if(this.bEgo){

                this.egoInterval -= deltaTime;

            }else{

                this.ego += HELLISHESTKING_INTERVAL;

            }

            if(this.ego >= 1){

                this.bEgo = true;

                this.ego = 1f;

            }

            if(this.egoInterval <= 0){

                this.ego -= HELLISHESTKING_INTERVAL;

                if(this.ego <= 0){

                    this.bAttackPossible = false;

                    this.bEgo = false;

                    this.ego = 1f;

                    this.status = 0;

                    this.egoInterval = HELLISHESTKING_INTERVALTIME;

                }

            }

        }else{

            //行動処理の更新
            behavior(player, deltaTime);

        }

        //モーションの更新
        changeMotion();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 敵の行動処理を行う
     * @param player プレイヤー
     * @param deltaTime デルタタイム
     */
    private void behavior(Player player, float deltaTime){

        if(SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_HELLISHESTKINGBEGIN)){

            return;

        }

        switch (this.magicState){

            case 0:

                this.magicInterval -= deltaTime;

                if(this.magicInterval <= 0){

                    initialize();

                }

                break;

            case 1:

                if(!EffectManager.getEffectManager().isEffect(EffectInfo.EFFECT_HELLISHESTKING_MAGIC)){

                    this.activityInterval -= deltaTime;

                    if(this.activityInterval <= 0){

                        magic();

                    }

                }

                break;

            case 2:

                if(!EffectManager.getEffectManager().isEffect(this.MAGIC[this.magicTypeNo])){

                    this.magicState = 0;

                    this.magicCount = 0;

                }

                break;

        }

        AnotherPlayer anotherPlayer = null;

        if(player instanceof AnotherPlayer){

            anotherPlayer = (AnotherPlayer)player;

        }

        CustomRectangle prec = anotherPlayer.getRect();

        for(int i = 0;i < this.magicCount;i++){

            CustomRectangle rrect = EffectManager.getEffectManager().getRect(this.MAGIC[this.magicTypeNo], i);

            if(rrect.overlaps(prec)){

                anotherPlayer.setDamage(true);

            }

            for(Enemy callEnemy : anotherPlayer.getEnemyCall().getEnemyList()){

                CustomRectangle cprec = callEnemy.getRect();

                if(rrect.overlaps(cprec)){

                    callEnemy.setDead(true);

                }

            }

        }

        if(this.status == HELLISHESTKING_STATUSCOUNT){

            this.bAttackPossible = true;

            this.ego = 0;

        }

    }

    /**
     * @brief 魔法の処理を行う
     */
    private void magic(){

        if(this.magicPosNo == HELLISHESTKING_MAGICPOSCOUNT - 1){

            Vector2 rockSize = EffectManager.getEffectManager().getSize(EffectInfo.EFFECT_ROCKWALL);

            if(this.rockInterval <= 0){

                float test = (this.MAGIC_POS[this.magicPosNo].x + (this.magicCount - 1) * rockSize.x) - rockSize.x - HELLISHESTKING_ROCKOFFSET;

                EffectManager.getEffectManager().start(EffectInfo.EFFECT_ROCKWALL, (this.MAGIC_POS[this.magicPosNo].x + (this.magicCount - 1) * rockSize.x) - rockSize.x - HELLISHESTKING_ROCKOFFSET, this.MAGIC_POS[this.magicPosNo].y - rockSize.y * 0.5f);

                SoundManager.getSoundManager().play(SoundInfo.SOUND_ROCKWALL);

                this.magicCount++;

                this.rockInterval = HELLISHESTKING_ROCKINTERVALTIME;

            }else{

                this.rockInterval -= HELLISHESTKING_ROCKINTERVAL;

            }

            Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

            if((this.magicCount - 1) * rockSize.x - (rockSize.x) > viewSize.x){

                this.magicState++;

                this.status++;

                this.activityInterval = HELLISHESTKING_ACTIVITYINTERVALTIME;

            }

        }else{

            Vector2 magicSize = EffectManager.getEffectManager().getSize(this.MAGIC[this.magicTypeNo]);

            EffectManager.getEffectManager().start(this.MAGIC[this.magicTypeNo], this.MAGIC_POS[this.magicPosNo].x - magicSize.x * 0.5f, this.MAGIC_POS[this.magicPosNo].y - magicSize.y * 0.5f);

            SoundManager.getSoundManager().play(this.MAGIC_SOUND[this.magicTypeNo]);

            this.magicState++;

            this.status++;

            this.magicCount++;

            this.activityInterval = HELLISHESTKING_ACTIVITYINTERVALTIME;

        }

    }

    /**
     * @brief 死亡動作処理を行う
     */
    @Override
    public void deadBehavior(){

        if(this.bDead){

            if(!this.bJump){

                this.move.y = ENEMY_JUMP;

                this.bJump = true;

            }

            this.move.y += GameElement.GRAVITY;

            this.pos.add(0.5f, this.move.y);

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

        if(pos.y + this.size.y < 0){

            if(!this.bDead){

                this.bDead = true;

            }

            if(this.bDead){

                this.bShow = false;

                GameElement.getGameElement().setStoryClear(GameElement.ANOTHERSTORY_CLEAR);

            }

        }

    }

    /**
     * @brief モーションの変更処理を行う
     */
    @Override
    public void changeMotion(){

        if(this.bAttackPossible){

            this.anime.changeAnime(ANIME_DAMAGE);

        }else{

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

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

        if(!((pos.x + this.size.x > 0 && pos.x < viewSize.x) && (pos.y + this.size.y > 0 && pos.y < viewSize.y))){

            return;

        }

        for(int i = 0;i < this.egoState.length - 1;i++){

            this.egoState[i] = this.ego;

        }

        //非道の王の描画
        this.anime.render(this.pos.x, this.pos.y, this.bReverse, this.bDead, this.egoState);

    }

    /**
     * @brief 攻撃可能かどうかの判定を行う
     * @return 攻撃可能かどうか
     */
    @Override
    public boolean isAttackPossible(){

        return this.bAttackPossible;

    }

}
