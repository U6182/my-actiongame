/**
 * @file Enemy.java
 * @brief ゲーム内の敵キャラクターを表すクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy;

import com.badlogic.gdx.math.Vector2;

import info.u6182.api.primitive.CustomRectangle;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.Anime;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.Player;

import info.u6182.api.Utiility;

/**
 * @class Enemy
 * @brief ゲーム内の敵キャラクターを表すクラス
 */
public class Enemy extends Anime {

    //! 1フレーム目の衝突判定フラグ
    protected boolean bFirstFrame;

    //! 召喚コスト
    protected int callCost;

    //! 敵のスピード
    protected final float ENEMY_SPEED = 2f;

    //! 召喚した敵のスピード
    protected final float CALLENEMY_SPEED = 3f;

    //! 敵のジャンプ力
    protected final float ENEMY_JUMP = 10f;

    /**
     * @brief コンストラクタ
     */
    public Enemy(){
        ;
    }

    /**
     * @brief コンストラクタ
     * @param pos 出現座標
     * @param type 種類
     */
    public Enemy(Vector2 pos, int type){

        //出現座標の設定
        this.startPos = new Vector2(pos);

        //座標の設定
        this.pos = new Vector2(pos);

        //矩形の初期化
        this.rect = new CustomRectangle();

        //反転フラグの初期化
        this.bReverse = true;

        //種類の初期化
        this.type = type;

    }

    /**
     * @brief コンストラクタ
     * @param pos 出現座標
     * @param size サイズ
     * @param bReverse 反転フラグ
     * @param type 種類
     */
    public Enemy(Vector2 pos, Vector2 size, boolean bReverse, int type){

        //サイズの初期化
        this.size = new Vector2(32f, 32f);

        //座標の初期化
        this.pos = new Vector2(bReverse ? pos.x - (this.size.x * 2) : pos.x + size.x + this.size.x, pos.y);

        //移動量の初期化
        this.move = new Vector2(bReverse ? -CALLENEMY_SPEED : CALLENEMY_SPEED, 0f);

        //矩形の初期化
        this.rect = new CustomRectangle();

        //1フレーム目の衝突判定フラグの初期化
        this.bFirstFrame = true;

        //表示フラグの初期化
        this.bShow = true;

        //種類の初期化
        this.type = type;

    }

    /**
     * @brief プレイヤーと敵の更新処理を行う
     * @param player プレイヤー
     * @param deltaTime デルタタイム
     */
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
     * @brief 敵の更新処理を行う
     * @param deltaTime デルタタイム
     */
    public void update(float deltaTime){

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

        //画面外に出た場合
        if(pos.y + this.size.y < 0 || pos.x > viewSize.x || pos.x + this.size.x < 0){

            this.bShow = false;

        }

        //死亡動作処理の更新
        if(this.bDead){

            deadBehavior();

            return;

        }

        //行動処理の更新
        callBehavior();

        //モーションの更新
        changeMotion();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 敵の行動処理を行う
     */
    public void behavior(){
        ;
    }

    /**
     * @brief 敵の召喚行動処理を行う
     */
    public void callBehavior(){
        ;
    }

    /**
     * @brief モーションの変更処理を行う
     */
    public void changeMotion(){

        if(this.move.x < 0){

            this.bReverse = true;

        }else if(this.move.x > 0){

            this.bReverse = false;

        }

    }

    /**
     * @brief 死亡動作処理を行う
     */
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

            }

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
     * @brief 召喚された敵のステージとの衝突処理を行う
     * @param buried 埋まり値
     */
    public void callCollisionStage(Vector2 buried){

        //埋まり値を加算
        this.pos.add(buried);

        //下辺の衝突
        if(buried.y > 0 && this.move.y < 0){

            if(this.bJump){

                this.bJump = false;

            }

            this.move.y = 0;

        //上辺の衝突
        }else if(buried.y < 0 && this.move.y > 0){

            this.move.y = 0;

        }

        //右辺の衝突
        if(buried.x < 0 && this.move.x > 0){

            this.bDead = true;

        //左辺の衝突
        }else if(buried.x > 0 && this.move.x < 0){

            this.bDead = true;

        }

    }

    /**
     * @brief 敵の描画処理を行う
     */
    public void render(){

        if(!this.bShow){

            return;

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

        if(!((pos.x + this.size.x > 0 && pos.x < viewSize.x) && (pos.y + this.size.y > 0 && pos.y < viewSize.y))){

            return;

        }

        this.anime.render(this.pos.x, this.pos.y, this.bReverse, this.bDead);

    }

    /**
     * @brief 落下矩形の取得を行う
     * @return 落下矩形を返す
     */
    public CustomRectangle fallRect(){

        if(this.bReverse){

            return this.rect.setCustomRect(this.pos.x - 31f, this.pos.y, this.pos.x, this.pos.y + this.size.y);

        }else{

            return this.rect.setCustomRect(this.pos.x + this.size.x, this.pos.y, this.pos.x + this.size.x + 31f, this.pos.y + this.size.y);

        }

    }

    /**
     * @brief 落下フラグの設定を行う
     * @param bFall 落下フラグ
     */
    public void setFall(boolean bFall){
        ;
    }

    /**
     * @brief プレイヤーとの衝突処理を行う
     */
    public void setPlayerCollision(){
        ;
    }

    /**
     * @brief ファイアボールの数を減少させる処理を行う
     */
    public void decreaseFireBallCount(){
        ;
    }

    /**
     * @brief ライフの減少処理を行う
     */
    public void decreaseLife(){
        ;
    }

    /**
     * @brief ライフの設定を行う
     */
    public void setLife(){
        ;
    }

    /**
     * @brief 1フレーム目の衝突判定フラグの設定を行う
     * @param bFirstFrame 1フレーム目の衝突判定フラグ
     */
    public void setFirstFrame(boolean bFirstFrame){

        this.bFirstFrame = bFirstFrame;

    }

    /**
     * @brief 攻撃可能かどうかの判定を行う
     * @return 攻撃可能かどうか
     */
    public boolean isAttackPossible(){

        return false;

    }

    /**
     * @brief 1フレーム目の衝突判定フラグの取得を行う
     * @return 1フレーム目の衝突判定フラグを返す
     */
    public boolean isFirstFrame(){

        return this.bFirstFrame;

    }

    /**
     * @brief 召喚コストの取得を行う
     * @return 召喚コストを返す
     */
    public int getCallCost(){

        return this.callCost;

    }

}
