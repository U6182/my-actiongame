/**
 * @file Obsession.java
 * @brief ゲーム内の強迫観念クラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.obsession;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.Player;

import info.u6182.api.Utiility;
import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class Obsession
 * @brief ゲーム内の強迫観念クラス
 */
public class Obsession extends Player {

    //! プレイヤー座標
    private Vector2 playerPos;

    //! 衝突フラグ
    private boolean bCollision;

    //! 落下状態
    private int fall;

    //! 落下状態ではない
    public final int OBSESSION_FALLNOT = 0;

    //! 落下状態
    public final int OBSESSION_FALL = 1;

    //! 行動開始フラグ
    private boolean bStart;

    //! インターバル
    private float interval;

    //! 強迫観念のスピード
    private final float OBSESSION_SPEED = 3f;

    //! 強迫観念のインターバルタイム
    private final float OBSESSION_INTERVALTIME = 200f;

    //! 強迫観念のプレイヤーを発見しジャンプする距離
    private final float OBSESSION_JUMPDISTANCE = 50f;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     * @param startPos 出現座標
     */
    public Obsession(CustomSpriteBatch spriteBatch, Vector2 startPos){

        //座標の初期化
        this.pos = new Vector2(startPos);

        //プレイヤー座標の初期化
        this.playerPos = new Vector2(startPos);

        //移動量の初期化
        this.move = new Vector2(OBSESSION_SPEED, 0f);

        //サイズの初期化
        this.size = new Vector2(60f, 64f);

        //矩形の初期化
        this.rect = new CustomRectangle();

        //衝突フラグの初期化
        this.bCollision = false;

        //落下状態の初期化
        this.fall = OBSESSION_FALLNOT;

        //行動開始フラグの初期化
        this.bStart = true;

        //インターバルの初期化
        this.interval = OBSESSION_INTERVALTIME;

        Texture texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_OBSESSION);

        AnimeData[] animeData = {new AnimeData(texture,"待機", size,true, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,0.05f),
                new AnimeData(texture,"移動", size,true, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0}}, 0,70,0.05f),
                new AnimeData(texture,"上昇", size,false, new int[][]{{0,0},{1,0},{2,0}}, 0,150,0.03f),
                new AnimeData(texture,"降下", size,false, new int[][]{{3,0},{4,0},{5,0},{0,0}}, 0,150,0.03f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief 更新を行う
     * @param player プレイヤー
     * @param chipSize チップサイズ
     * @param deltaTime デルタタイム
     */
    public void update(Player player, int chipSize, float deltaTime){

        //行動開始
        if(this.bStart){

            start(player.getPos());

            return;

        }

        //強迫観念中
        if(player.isObsession()){

            this.bObsession = true;

            this.bShow = false;

            return;

        }

        //強迫観念の終了
        if(!this.bShow && this.bObsession){

            if(!player.isObsession()){

                this.interval--;

                //復活
                if(this.interval <= 0){

                    returntoDeath(player.getPos());

                }

            }

        }

        //落下などで死亡した場合
        if(!this.bShow && this.bDead){

            this.interval--;

            //復活
            if(this.interval <= 0){

                returntoDeath(player.getPos());

            }

            return;

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

        //画面下に出た場合死亡
        if(pos.y + this.size.y < 0){

            this.bDead = true;

            this.bShow = false;

        }

        //動作処理の更新
        behavior(player.getPos(), chipSize);

        //モーションの更新
        changeMotion();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief プレイヤーの位置に復活する処理を行う
     * @param pos プレイヤーの座標
     */
    public void returntoDeath(Vector2 pos){

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        //画面外左から出る
        this.pos.set(pos.x - viewSize.x, pos.y);

        //プレイヤーの初期座標より小さい場合は初期座標を座標にする
        if(this.pos.x < this.playerPos.x){

            this.pos.x = this.playerPos.x;

        }

        if(this.bDead){

            this.bDead = false;

        }

        if(this.bObsession){

            this.bObsession = false;

        }

        this.bShow = true;

        this.interval = OBSESSION_INTERVALTIME;

    }

    /**
     * @brief 行動開始処理を行う
     * @param pos プレイヤーの座標
     */
    public void start(Vector2 pos){

        //プレイヤーが初期座標より200進んだらスタート
        if(pos.x > this.playerPos.x + 200f){

            this.bShow = true;

            this.bStart = false;

        }

    }

    /**
     * @brief 行動処理を行う
     * @param pos プレイヤーの座標
     * @param chipSize チップサイズ
     */
    public void behavior(Vector2 pos, int chipSize){

        if(!this.bShow){

            return;

        }

        //プレイヤーが強迫観念より前方にいるか後方にいるかによって移動量を変え追跡する
        if(this.pos.x + this.size.x < pos.x){

            if(this.move.x < 0){

                this.move.x = -this.move.x;

            }

        }else if(this.pos.x > pos.x + this.size.x){

            if(this.move.x > 0){

                this.move.x = -this.move.x;

            }

        }

        //プレイヤーが認識範囲に入っていて自分よりも高くにいる場合ジャンプする
        if(this.pos.x + this.size.x > pos.x - OBSESSION_JUMPDISTANCE && this.pos.x < pos.x + this.size.x + OBSESSION_JUMPDISTANCE){

            if(pos.y > this.pos.y + chipSize && !this.bJump){

                this.move.y = PLAYER_JUMP;

                this.bJump = true;

            }

        //ステージに衝突した場合ジャンプする
        }else if(!this.bJump && this.bCollision){

            this.move.y = PLAYER_JUMP;

            this.bJump = true;

        //前方に地面がない場合ジャンプする
        }else if(!this.bJump && this.fall == OBSESSION_FALL){

            this.move.y = PLAYER_JUMP;

            this.bJump = true;

            this.fall = OBSESSION_FALL + 1;

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

    /**
     * @brief モーション変更処理を行う
     */
    @Override
    public void changeMotion(){

        //ジャンプ上昇時に上昇モーション
        if(this.bJump && this.move.y > 0){

            this.anime.changeAnime(ANIME_JUMP);

        }else if(this.bJump && this.move.y <= 0){

            this.anime.changeAnime(ANIME_DOWN);

        }else if(this.move.x < 0){

            this.anime.changeAnime(ANIME_MOVE);

            this.bReverse = true;

        }else if(this.move.x > 0){

            this.anime.changeAnime(ANIME_MOVE);

            this.bReverse = false;

        }

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

            if(this.bCollision){

                this.bCollision = false;

            }

            if(this.fall == OBSESSION_FALL + 1){

                this.fall = OBSESSION_FALLNOT;

            }

        //上辺の衝突
        }else if(buried.y < 0 && this.move.y > 0){

            this.move.y = 0f;

        }

        //右辺の衝突
        if(buried.x < 0 && this.move.x > 0){

            if(!this.bJump && !this.bCollision){

                this.bCollision = true;

            }

        //左辺の衝突
        }else if(buried.x > 0 && this.move.x < 0){

            if(!this.bJump && !this.bCollision){

                this.bCollision = true;

            }

        }

    }

    /**
     * @brief 描画処理を行う
     */
    public void render(){

        if(!this.bShow){

            return;

        }
        //強迫観念の描画
        this.anime.render(this.pos.x, this.pos.y, this.bReverse, false);

    }

    /**
     * @brief 強迫観念状態の設定を行う
     */
    public void setObsession(){

        this.bObsession = true;

    }

    /**
     * @brief 落下状態の設定を行う
     * @param fall 落下状態
     */
    public void setFall(int fall){

        this.fall = fall;

    }

    /**
     * @brief 矩形情報の取得を行う
     * @return 矩形情報
     */
    @Override
    public CustomRectangle getRect(){

        return this.rect.set(this.pos, this.size);

    }

    /**
     * @brief 落下判定の矩形情報の取得を行う
     * @return 矩形情報
     */
    public CustomRectangle fallRect(){

        if(this.bReverse){

            return this.rect.setCustomRect(this.pos.x - 31f, this.pos.y, this.pos.x, this.pos.y + this.size.y);

        }else{

            return this.rect.setCustomRect(this.pos.x + this.size.x, this.pos.y, this.pos.x + this.size.x + 31f, this.pos.y + this.size.y);

        }

    }

}
