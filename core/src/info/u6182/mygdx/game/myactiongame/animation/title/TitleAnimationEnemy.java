/**
 * @file TitleAnimationEnemy.java
 * @brief タイトルアニメーションの敵キャラクタークラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.animation.title;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.Anime;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.primitive.CustomRectangle;

/**
 * @class TitleAnimationEnemy
 * @brief タイトル画面に登場する敵キャラクタークラス
 */
public class TitleAnimationEnemy extends Anime {

    //! インターバルフラグ
    private boolean bInterval;

    //! インターバル
    private float interval;

    /**
     * @brief コンストラクタ
     */
    public TitleAnimationEnemy(){
        ;
    }

    /**
     * @brief 敵キャラクターの初期化を行う
     * @param texture 使用するテクスチャ
     * @param pos 出現座標
     * @param size キャラクターのサイズ
     * @param interval インターバル時間
     */
    public void initialize(Texture texture, Vector2 pos, Vector2 size, float interval){

        //座標の設定
        this.pos = new Vector2(pos);

        //移動量の初期化
        this.move = new Vector2(0f,0f);

        //サイズの設定
        this.size = new Vector2(size);

        //矩形の初期化
        this.rect = new CustomRectangle();

        //表示フラグの初期化
        this.bShow = false;

        //インターバルフラグの初期化
        this.bInterval = true;

        //インターバルの初期化
        this.interval = interval;

        AnimeData[] animeData = {new AnimeData(texture,"移動", size,true, new int[][]{{0,2},{1,2},{2,2}}, 0,0,0.05f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData);

    }

    /**
     * @brief 敵キャラクターの更新処理を行う
     * @param enemyStop 敵キャラクターの停止フラグ
     * @param deltaTime 経過時間
     */
    public void update(boolean enemyStop, float deltaTime){

        //行動処理の更新
        behavior(enemyStop);

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 敵キャラクターの行動処理を行う
     * @param enemyStop 敵キャラクターの停止フラグ
     */
    public void behavior(boolean enemyStop){

        if(!this.bShow){

            return;

        }else{

            if(this.bInterval){

                this.interval -= 0.5f;

                if(this.interval <= 0){

                    this.bInterval = false;

                }

                return;

            }

        }

        if (this.bDead) {

            if(this.bJump){

                this.move.y = 10.0f;

                this.bJump = true;

            }

            this.move.y += GameElement.GRAVITY;

            this.pos.add(0.5f, this.move.y);

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        if(this.pos.y > viewSize.y){

            if(!this.bDead){

                this.bDead = true;

            }

            if(this.bDead){

                this.bShow = false;

                this.bDead = false;

            }

            return;

        }

        if(enemyStop){

            this.move.x = 0;

            return;

        }

        this.pos.x += this.move.x;

        if(this.pos.x - 16 > viewSize.x || this.pos.x + this.size.x + 16 < 0){

            this.move.x = -this.move.x;

        }

        if(this.move.x < 0){

            this.bReverse = true;

        }else if(this.move.x > 0){

            this.bReverse = false;

        }

    }

    /**
     * @brief 敵キャラクターの描画処理を行う
     */
    public void render(){

        if(!this.bShow){

            return;

        }

        this.anime.render(this.pos.x, this.pos.y, this.bReverse, this.bDead);

    }

    /**
     * @brief インターバル時間の設定を行う
     * @param interval インターバル時間
     */
    public void setInterval(float interval){

        this.interval = interval;

    }

    /**
     * @brief アニメーションの取得を行う
     * @return アニメーションオブジェクト
     */
    public AnimeMotion getAnime(){

        return this.anime;

    }

    /**
     * @brief インターバルフラグの取得を行う
     * @return インターバル中の場合はtrue
     */
    public boolean isInterval(){

        return this.bInterval;

    }

    /**
     * @brief インターバル時間の取得を行う
     * @return インターバル時間
     */
    public float getInterval(){

        return this.interval;

    }

}
