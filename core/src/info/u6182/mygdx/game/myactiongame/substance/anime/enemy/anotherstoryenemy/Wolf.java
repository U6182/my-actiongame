/**
 * @file Wolf.java
 * @brief ゲーム内のウルフクラスを定義するファイル
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
 * @class Wolf
 * @brief ゲーム内のウルフクラス
 */
public class Wolf extends Enemy {

    //! 岩の最大数
    private final int ROCK_COUNT = 5;

    //! ウルフのインターバル時間
    private final float WOLF_INTERVALTIME = 25f;

    //! ウルフのインターバル
    private final float WOLF_INTERVAL = 0.1f;

    //! ウルフの岩のインターバル時間
    private final float WOLF_ROCKINTERVALTIME = 10f;

    //! ウルフの岩のインターバル
    private final float WOLF_ROCKINTERVAL = 2f;

    //! 岩壁の生成フラグ
    private boolean bRockWall;

    //! 岩の数
    private int rockCount;

    //! インターバル
    private float interval;

    //! 岩のインターバル
    private float rockInterval;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     * @param idNo 個体識別番号
     */
    public Wolf(Texture texture, Vector2 pos, int type, int idNo){

        super(pos, type);

        this.pos.y -= 17f;

        //サイズの初期化
        this.size = new Vector2(61f, 47f);

        //移動量の初期化
        this.move = new Vector2(0f, ENEMY_SPEED);

        this.bReverse = (type == GameElement.ENEMY_WOLFLEFT) ? true : false;

        this.bRockWall = false;

        //個体識別番号の設定
        this.idNo = idNo;

        this.rockCount = 0;

        this.interval = WOLF_INTERVALTIME;

        this.rockInterval = WOLF_ROCKINTERVALTIME;

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

        Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        if((pos.x + this.size.x + 100 < 0 || this.pos.x - 100 > viewSize.x) || (pos.y < 0 || pos.y + this.size.y > viewSize.y)){

            return;

        }

        //行動処理の更新
        behavior(player);

        //モーションの更新
        changeMotion();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 敵の行動処理を行う
     * @param player プレイヤー
     */
    private void behavior(Player player){

        AnotherPlayer anotherPlayer = null;

        if(player instanceof AnotherPlayer){

            anotherPlayer = (AnotherPlayer) player;

        }

        CustomRectangle prec = anotherPlayer.getRect();

        int callCount = anotherPlayer.getEnemyCall().getCallCount();

        if(this.interval <= 0){

            this.bRockWall = true;

        }else{

            this.interval -= WOLF_INTERVAL;

        }

        if(this.bRockWall){

            float rockWidth = EffectManager.getEffectManager().getSize(EffectInfo.EFFECT_ROCKWALL).x;

            float rockHeight = EffectManager.getEffectManager().getSize(EffectInfo.EFFECT_ROCKWALL).y;

            if(this.bReverse){

                rockWidth = -rockWidth;

            }

            if(this.rockInterval <= 0){

                EffectManager.getEffectManager().start(EffectInfo.EFFECT_ROCKWALL, this.pos.x + (this.rockCount + 1) * rockWidth, this.pos.y + this.size.y - rockHeight * 0.5f);

                SoundManager.getSoundManager().play(SoundInfo.SOUND_ROCKWALL);

                this.rockCount++;

                this.rockInterval = WOLF_ROCKINTERVALTIME;

            }else{

                this.rockInterval -= WOLF_ROCKINTERVAL;

            }

        }

        for(int i = 0;i < this.rockCount;i++){

            CustomRectangle rrect = EffectManager.getEffectManager().getRect(EffectInfo.EFFECT_ROCKWALL, i);

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

        if(this.rockCount == ROCK_COUNT){

            this.bRockWall = false;

            this.rockCount = 0;

            this.interval = WOLF_INTERVALTIME;

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

}
