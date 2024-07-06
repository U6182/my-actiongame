/**
 * @file Succubus.java
 * @brief ゲーム内のサキュバスクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy;

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
 * @class Succubus
 * @brief ゲーム内のサキュバスクラス
 */
public class Succubus extends Enemy {

    //! アニメーション状態: 待機
    private final int ANIME_WAIT = 0;

    //! アニメーション状態: 移動
    private final int ANIME_MOVE = 1;

    //! アニメーション状態: 通常
    private final int ANIME_NOMAL = 2;

    //! サキュバスのインターバル時間
    private final float SUCCUBUS_INTERVALTIME = 30f;

    //! サキュバスのインターバル
    private final float SUCCUBUS_INTERVAL = 0.1f;

    //! 移動範囲を示す矩形
    private CustomRectangle rangeRect;

    //! 落下フラグ
    private boolean bFall;

    //! 誘惑フラグ
    private boolean bTemptation;

    //! インターバル
    private float interval;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     * @param idNo 個体識別番号
     */
    public Succubus(Texture texture, Vector2 pos, int type, int idNo){

        super(pos, type);

        //サイズの初期化
        this.size = new Vector2(32f, 32f);

        //移動量の初期化
        this.move = new Vector2(-ENEMY_SPEED, 0f);

        this.rangeRect = new CustomRectangle();

        this.bFall = false;

        this.bTemptation = false;

        //個体識別番号の設定
        this.idNo = idNo;

        this.interval = 0f;

        AnimeData[] animeData = {new AnimeData(texture, "待機", this.size, true, new int[][]{{0,4},{1,4},{2,4}},0,0,0.08f),
                                 new AnimeData(texture, "移動", this.size, true, new int[][]{{0,0},{1,0},{2,0}},0,217,0.08f),
                                 new AnimeData(texture, "通常", this.size, true, new int[][]{{0,2},{1,2},{2,2}},0,0,0.08f)};

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

        if(this.bTemptation && this.interval <= 0){

            temptationBehavior(player);

        }

        //死亡動作処理の更新
        if(this.bDead){

            deadBehavior();

            return;

        }

        this.rangeRect.setCustomRect(this.pos.x - 100f, this.pos.y, this.pos.x + this.size.x + 100f, this.pos.y + this.size.y + 10f);

        CustomRectangle prec = player.getRect();

        //行動処理の更新
        behavior(prec);

        //モーションの更新
        changeMotion();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 敵の行動処理を行う
     * @param prec プレイヤーの矩形
     */
    private void behavior(CustomRectangle prec){

        if(!this.bTemptation && this.rangeRect.overlaps(prec)){

            this.bTemptation = true;

        }else{

            this.bTemptation = false;

        }

        this.move.y += GameElement.GRAVITY;

        if(this.anime.getAnimeNo() != ANIME_WAIT){

            this.pos.x += this.move.x;

            this.interval -= this.SUCCUBUS_INTERVAL;

        }

        this.pos.y += this.move.y;

        if(this.bFall){

            this.move.x = -this.move.x;

        }

    }

    /**
     * @brief 誘惑の行動処理を行う
     * @param player プレイヤー
     */
    private void temptationBehavior(Player player){

        AnotherPlayer anotherPlayer = null;

        if(player instanceof AnotherPlayer){

            anotherPlayer = (AnotherPlayer) player;

        }

        EffectManager.getEffectManager().start(EffectInfo.EFFECT_TEMPTATION, this.pos.x, this.pos.y + this.size.y);

        SoundManager.getSoundManager().play(SoundInfo.SOUND_TEMPTAION);

        anotherPlayer.setTemptation(true);

        this.bTemptation = false;

        this.interval = this.SUCCUBUS_INTERVALTIME;

    }

    /**
     * @brief モーションの変更処理を行う
     */
    @Override
    public void changeMotion(){

        if(EffectManager.getEffectManager().isEffect(EffectInfo.EFFECT_TEMPTATION)){

            this.anime.changeAnime(ANIME_WAIT);

        }else{

            if(this.interval >= 0){

                this.anime.changeAnime(ANIME_NOMAL);

            }else{

                this.anime.changeAnime(ANIME_MOVE);

            }

            if(this.move.x < 0){

                this.bReverse = true;

            }else if(this.move.x > 0){

                this.bReverse = false;

            }

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
