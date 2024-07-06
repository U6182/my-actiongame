/**
 * @file TitleAnimationPlayer.java
 * @brief タイトルアニメーションのプレイヤークラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.animation.title;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.Anime;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class TitleAnimationPlayer
 * @brief タイトル画面に登場するプレイヤークラス
 */
public class TitleAnimationPlayer extends Anime {

    //! 待機アニメーション番号
    public static final int ANIME_WAIT = 0;

    //! 移動アニメーション番号
    public static final int ANIME_MOVE = 1;

    //! ジャンプアニメーション番号
    public static final int ANIME_JUMP = 2;

    //! 下降アニメーション番号
    public static final int ANIME_DOWN = 3;

    //! 待機フラグ
    private boolean bWait;

    //! 敵待機フラグ
    private boolean bEnemyStop;

    //! インターバルフラグ
    private boolean bInterval;

    //! アニメーション終了フラグ
    private boolean bEnd;

    //! 移動数
    private int count;

    //! 敵死亡数
    private int enemyDeadCount;

    //! インターバル
    private float interval;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public TitleAnimationPlayer(CustomSpriteBatch spriteBatch){

        //座標の初期化
        this.pos = new Vector2(0,0);

        //移動量の初期化
        this.move = new Vector2(8,0);

        //サイズの初期化
        this.size = new Vector2(60.0f, 64.0f);

        //矩形の初期化
        this.rect = new CustomRectangle(this.pos, this.size);

        //待機フラグの初期化
        this.bWait = false;

        //敵待機フラグの初期化
        this.bEnemyStop = false;

        //インターバルフラグの初期化
        this.bInterval = false;

        //アニメーション終了フラグの初期化
        this.bEnd = false;

        //移動数の初期化
        this.count = 0;

        //敵死亡数の初期化
        this.enemyDeadCount = 0;

        //インターバルの初期化
        this.interval = 20.0f;

        Texture texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PLAYER);

        AnimeData[] animeData = {new AnimeData(texture,"待機", size,true, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,0.05f),
                new AnimeData(texture,"移動", size,true, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0}}, 0,70,0.05f),
                new AnimeData(texture,"上昇", size,false, new int[][]{{0,0},{1,0},{2,0}}, 0,150,0.03f),
                new AnimeData(texture,"降下", size,false, new int[][]{{3,0},{4,0},{5,0},{0,0}}, 0,150,0.03f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief プレイヤーの初期化を行う
     */
    public void initialize(){

        //座標の初期化
        this.pos.set(0, 32 * 3);

        //移動量の初期化
        this.move.set(5, 0);

        //ジャンプフラグの初期化
        this.bJump = false;

        //反転フラグの初期化
        this.bReverse = false;

        //待機フラグの初期化
        this.bWait = false;

        //敵待機フラグの初期化
        this.bEnemyStop = false;

        //インターバルフラグの初期化
        this.bInterval = false;

        //アニメーション終了フラグの初期化
        this.bEnd = false;

        //移動数の初期化
        this.count = 0;

        //敵死亡数の初期化
        this.enemyDeadCount = 0;

        //インターバルの初期化
        this.interval = 20.0f;

    }

    /**
     * @brief プレイヤーの更新処理を行う
     * @param deltaTime 経過時間
     */
    public void update(float deltaTime){

        //行動処理の更新
        behavior();

        //モーション変更処理の更新
        motionChange();

        if(this.pos.y < 96){

            this.pos.y = 96f;

            this.move.y = 0f;

            this.bJump = false;

        }

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief プレイヤーの行動処理を行う
     */
    public void behavior(){

        if(this.bInterval){

            this.interval -= 0.1f;

            if(this.interval <= 0){

                this.bEnd = true;

                this.bInterval = false;

            }

        }

        if(this.bWait){

            this.interval--;

            if(this.interval >= 0){

                return;

            }

            this.move.x = 1;

            if(!this.bJump){

                this.move.y = 10.0f;

                this.bJump = true;

            }

            this.move.y += GameElement.GRAVITY;

            this.pos.add(this.move);

            if(this.enemyDeadCount == 3){

                this.bWait = false;

                this.move.x = 0;

                this.interval = 20;

                this.bInterval = true;

            }

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        if(this.count == 3 && this.pos.x < viewSize.x * 0.5f){

            this.move.x = 0;

            this.bReverse = false;

            this.bEnemyStop = true;

            this.bWait = true;

            return;

        }

        if(this.pos.x > viewSize.x || this.pos.x + this.size.x < 0){

            this.move.x = -this.move.x;

            this.count++;

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

    /**
     * @brief プレイヤーのモーション変更処理を行う
     */
    public void motionChange(){

        if(this.bJump && this.move.y > 0){

            this.anime.changeAnime(ANIME_JUMP);

        }else if(this.bJump && this.move.y <= 0){

            this.anime.changeAnime(ANIME_DOWN);

        }else if(this.move.x > 0){

            this.bReverse = false;

            this.anime.changeAnime(ANIME_MOVE);

        }else if(this.move.x < 0){

            this.bReverse = true;

            this.anime.changeAnime(ANIME_MOVE);

        }else{

            this.anime.changeAnime(ANIME_WAIT);

        }

    }

    /**
     * @brief プレイヤーと敵の衝突判定を行う
     * @param enemy 敵キャラクター
     * @return 衝突している場合はtrue
     */
    public boolean collisionEnemy(TitleAnimationEnemy enemy){

        if(!enemy.isShow() || enemy.isDead()){

            return false;

        }

        CustomRectangle prec = getRect();

        CustomRectangle erec = enemy.getRect();

        if(prec.overlaps(erec)){

            if(prec.top - erec.height > erec.top && prec.right > erec.left && prec.left < erec.right){

                if(this.bJump){

                    this.bJump = false;

                }

                enemy.setDead(true);

                this.enemyDeadCount++;

            }

            return true;

        }

        return false;

    }

    /**
     * @brief プレイヤーとステージの衝突処理を行う
     * @param buried 埋め込み量
     */
    public void collisionStage(Vector2 buried){

        this.pos.add(buried);

        if(buried.y < 0 && this.move.y > 0){

            this.move.y = 0.0f;

            if(this.bJump){

                this.bJump = false;

            }

        }else if(buried.y > 0 && this.move.y < 0){

            this.move.y = 0.0f;

        }

        if(buried.x < 0 && this.move.x > 0){

            this.move.x = 0.0f;

        }else if(buried.x > 0 && this.move.x < 0){

            this.move.x = 0.0f;

        }

    }

    /**
     * @brief プレイヤーの描画処理を行う
     */
    public void render(){

        this.anime.render(this.pos.x, this.pos.y, this.bReverse, false);

    }

    /**
     * @brief 敵キャラクターの停止フラグの取得を行う
     * @return 敵キャラクターが停止している場合はtrue
     */
    public boolean isEnemyStop(){

        return this.bEnemyStop;

    }

    /**
     * @brief アニメーションの終了フラグの取得を行う
     * @return アニメーションが終了している場合はtrue
     */
    public boolean isEnd(){

        return this.bEnd;

    }

    /**
     * @brief プレイヤーの移動数の取得を行う
     * @return プレイヤーの移動数
     */
    public int getCount(){

        return this.count;

    }

}
