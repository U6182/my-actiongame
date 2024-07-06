/**
 * @file Mage.java
 * @brief ゲーム内の魔導士クラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.effectmanager.EffectInfo;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.stage.StageInfo;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.storyenemy.KingGhost;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.AnotherPlayer;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.Player;

import info.u6182.api.Utiility;
import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.soundmanager.SoundManager;

/**
 * @class Mage
 * @brief ゲーム内の魔導士クラス
 */
public class Mage extends Enemy {

    //! 魔法エフェクトの配列
    private final String[] MAGIC = {EffectInfo.EFFECT_MAGECURSE, EffectInfo.EFFECT_MAGETEMPTATION, EffectInfo.EFFECT_MAGEICEWALL, EffectInfo.EFFECT_MAGETHUNDERWALL};

    //! 魔法サウンドの配列
    private final String[] MAGIC_SOUND = {SoundInfo.SOUND_CURSE, SoundInfo.SOUND_MAGETEMPTATION, SoundInfo.SOUND_ICEWALL, SoundInfo.SOUND_THUNDERWALL};

    //! 魔法カウント
    private final int MAGIC_COUNT = 4;

    //! 魔導士のインターバル時間
    private final float MAGE_INTERVALTIME = 30f;

    //! 魔導士のインターバル
    private final float MAGE_INTERVAL = 0.1f;

    //! ステージ情報
    private StageInfo stageInfo;

    //! 魔法矩形
    private CustomRectangle magicRect;

    //! 魔法座標
    private Vector2 magicPos;

    //! 魔法タイプ
    private int magicType;

    //! インターバル
    private float interval;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param stageInfo ステージ情報
     * @param pos 出現座標
     * @param type 種類
     * @param idNo 個体識別番号
     */
    public Mage(Texture texture, StageInfo stageInfo, Vector2 pos, int type, int idNo){

        super(pos, type);

        this.stageInfo = stageInfo;

        //サイズの初期化
        this.size = new Vector2(32f, 32f);

        //移動量の初期化
        this.move = new Vector2(0f, ENEMY_SPEED);

        this.magicPos = new Vector2();

        this.magicRect = new CustomRectangle();

        //個体識別番号の設定
        this.idNo = idNo;

        this.interval = 0f;

        AnimeData[] animeData = {new AnimeData(texture, "待機", this.size, true, new int[][]{{0,0},{1,0},{2,0}},0,0,0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData);

    }

    /**
     * @brief 初期化処理を行う
     */
    private void initialize(){

        this.magicType = Utiility.getRandomInt(this.MAGIC_COUNT);

        magicType = 0;
        Vector2 magicSize = EffectManager.getEffectManager().getSize(this.MAGIC[this.magicType]);

        if(this.MAGIC[this.magicType].equals(EffectInfo.EFFECT_MAGEICEWALL) || this.MAGIC[this.magicType].equals(EffectInfo.EFFECT_MAGETHUNDERWALL)){

            EffectManager.getEffectManager().start(this.MAGIC[this.magicType], this.pos.x - magicSize.x * 0.5f, this.pos.y + this.size.y - magicSize.y * 0.5f);

            this.magicRect.set(EffectManager.getEffectManager().getRect(this.MAGIC[this.magicType], 0));

            this.interval = MAGE_INTERVALTIME;

            return;

        }

        Vector2 scroll = GameElement.getGameElement().getScroll();

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        int posX = (int)scroll.x;
        int posXWidth = (int)(scroll.x - magicSize.x + viewSize.x);

        int posY = (int)scroll.y;
        int posYHeight = (int)(scroll.y - magicSize.y + viewSize.y);

        this.magicPos.x = Utiility.getRandomInt(posX, posXWidth);
        this.magicPos.y = Utiility.getRandomInt(posY, posYHeight);

        this.magicRect.setCustomRect(this.magicPos.x, this.magicPos.y, this.magicPos.x + magicSize.x, this.magicPos.y + magicSize.y);

        while (magicCollisionStage()){

            this.magicPos.x = Utiility.getRandomInt(posX, posXWidth);
            this.magicPos.y = Utiility.getRandomInt(posY, posYHeight);

            this.magicRect.setCustomRect(this.magicPos.x, this.magicPos.y, this.magicPos.x + magicSize.x, this.magicPos.y + magicSize.y);

        }

        this.interval = MAGE_INTERVALTIME;

        EffectManager.getEffectManager().start(this.MAGIC[this.magicType], this.magicPos);

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

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

        //画面外に出た場合
        if(pos.y + this.size.y < 0 || pos.y > viewSize.y || pos.x > viewSize.x || pos.x + this.size.x < 0){

            return;

        }

        if(this.interval <= 0){

            initialize();

            SoundManager.getSoundManager().play(this.MAGIC_SOUND[this.magicType]);

        }

        if(!EffectManager.getEffectManager().isEffect(this.MAGIC[this.magicType])){

            this.interval -= MAGE_INTERVAL;

        }else{

            behavior(player);

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

        AnotherPlayer anotherPlayer = null;

        if(player instanceof AnotherPlayer){

            anotherPlayer = (AnotherPlayer) player;

        }

        CustomRectangle prec = anotherPlayer.getRect();

        callCollision(anotherPlayer);

        if(this.magicRect.overlaps(prec)){

            switch (this.MAGIC[this.magicType]){

                case EffectInfo.EFFECT_MAGECURSE:

                    if(anotherPlayer.getCurse() == anotherPlayer.CURSE_NOT){

                        anotherPlayer.setCurse(this.idNo);

                    }

                    break;

                case EffectInfo.EFFECT_MAGETEMPTATION:

                    anotherPlayer.setTemptation(true);

                    break;

                case EffectInfo.EFFECT_MAGEICEWALL:

                case EffectInfo.EFFECT_MAGETHUNDERWALL:

                    anotherPlayer.setDamage(true);

                    break;

            }

        }

    }

    /**
     * @brief 他の敵との衝突処理を行う
     * @param anotherPlayer プレイヤー
     */
    private void callCollision(AnotherPlayer anotherPlayer){

        for(Enemy callEnemy : anotherPlayer.getEnemyCall().getEnemyList()){

            CustomRectangle crec = callEnemy.getRect();

            if(this.magicRect.overlaps(crec)){

                switch (this.MAGIC[this.magicType]){

                    case EffectInfo.EFFECT_MAGECURSE:

                        break;

                    case EffectInfo.EFFECT_MAGETEMPTATION:

                        if(callEnemy instanceof KingGhost){

                            KingGhost kingGhost = (KingGhost)callEnemy;

                            kingGhost.setTemptation();

                        }

                        break;

                    case EffectInfo.EFFECT_MAGEICEWALL:

                    case EffectInfo.EFFECT_MAGETHUNDERWALL:

                        callEnemy.setDead(true);

                        break;

                }

            }

        }

    }

    /**
     * @brief 魔法のステージ衝突判定を行う
     * @return 衝突が発生したかどうか
     */
    private boolean magicCollisionStage(){

        boolean re = false;

        int lc = (int)this.magicRect.left / this.stageInfo.chipSize;

        int rc = (int)this.magicRect.right / this.stageInfo.chipSize;

        int tc = (int)this.magicRect.top / this.stageInfo.chipSize;

        int bc = (int)this.magicRect.buttom / this.stageInfo.chipSize;

        if(lc < 0){

            lc = 0;

        }

        if(bc < 0){

            bc = 0;

        }


        if(rc >= this.stageInfo.xCount){

            rc = this.stageInfo.xCount - 1;

        }

        if(tc >= this.stageInfo.yCount){

            tc = this.stageInfo.yCount - 1;

        }

        for(int y = bc;y <= tc;y++){

            for(int x = lc;x <= rc;x++){

                byte cn = (byte) (this.stageInfo.chipData[y * this.stageInfo.xCount + x] - 1);

                if(cn < 0){

                    continue;

                }

                //ステージ矩形
                this.stageInfo.chipRect.set(x * this.stageInfo.chipSize, y * this.stageInfo.chipSize, this.stageInfo.chipSize, this.stageInfo.chipSize);

                if(this.stageInfo.chipRect.overlaps(this.magicRect)){

                    return true;

                }

            }

        }

        return re;

    }

}
