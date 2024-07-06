/**
 * @file CreditAnimationEnemy.java
 * @brief クレジットアニメーションの敵クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.animation.credit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.Anime;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.batch.CustomSpriteBatch;

/**
 * @class CreditAnimationEnemy
 * @brief クレジットアニメーションに登場する敵キャラクターのクラス
 */
public class CreditAnimationEnemy extends Anime {

    //! 出現座標
    private Vector2 startPos;

    //! 移動終了フラグ
    private boolean bMoveEnd;

    //! ゴーストフラグ
    private boolean bGhost;

    //! 往復した数
    private int count;

    //! 時間
    private float time;

    /**
     * @brief コンストラクタ
     */
    public CreditAnimationEnemy(){

        //移動終了フラグの初期化
        this.bMoveEnd = false;

        //ゴーストフラグの初期化
        this.bGhost = false;

        //往復した数の初期化
        this.count = 0;

        //時間の初期化
        this.time = 0;

    }

    /**
     * @brief 敵の初期化を行う
     * @param spriteBatch スプライトバッチ
     * @param texture 使用するテクスチャ
     * @param pos 出現座標
     * @param type 敵の種類
     */
    public void initialize(CustomSpriteBatch spriteBatch, Texture texture, Vector2 pos, int type){

        //座標の初期化
        this.pos = new Vector2(pos);

        //移動量の初期化
        this.move = new Vector2();

        this.size = type == GameElement.ENEMY_PUMPKING ? new Vector2(64f, 64f) : new Vector2(32f, 32f);

        //表示フラグの初期化
        this.bShow = true;

        //種類の初期化
        this.type = type;

        //出現座標の初期化
        this.startPos = new Vector2(this.pos.x, this.pos.y);

        switch (this.type){

            case GameElement.ENEMY_BLACKGHOST:
            case GameElement.ENEMY_WHITEGHOST:

                this.pos.x += 64;
                this.move.x = -5;
                this.bGhost = true;
                break;

            case GameElement.ENEMY_PUMPKIN:

                this.pos.x -= 32;
                this.move.x = 5;
                break;

            case GameElement.ENEMY_KINGGHOST:

                this.pos.x += 32;
                this.move.x = -5;
                break;

            case GameElement.ENEMY_GREENPUMPKIN:

                this.pos.x -= 32;
                this.move.x = 5;
                break;

            case GameElement.ENEMY_GHOSTPUMPKIN:

                this.pos.x += 32;
                this.move.x = -5;
                this.move.y = 8;
                break;

            case GameElement.ENEMY_PUMPKING:

                this.pos.x -= 64;
                this.move.x = 5;
                this.bGhost = true;
                break;

        }

        //反転フラグの初期化
        this.bReverse = this.move.x > 0 ? false : true;

        AnimeData[] animeData = {new AnimeData(texture,"移動", size,true, new int[][]{{0,2},{1,2},{2,2}}, 0,0,0.05f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief 敵の更新処理を行う
     */
    public void update(){

        //行動処理の更新
        behavior();

        this.anime.addTime(Gdx.graphics.getDeltaTime());

    }

    /**
     * @brief 敵の行動処理を行う
     */
    public void behavior(){

        switch (this.type){

            case GameElement.ENEMY_BLACKGHOST:
            case GameElement.ENEMY_WHITEGHOST:

                ghostBehavior();

                break;

            case GameElement.ENEMY_PUMPKIN:
            case GameElement.ENEMY_KINGGHOST:

                behavior1();

                break;

            case GameElement.ENEMY_GREENPUMPKIN:

                behavior2();

                break;

            case GameElement.ENEMY_GHOSTPUMPKIN:

                behavior3();

                break;

            case GameElement.ENEMY_PUMPKING:

                bossBehavior();

                break;

        }

        if(!this.bGhost){

            Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

            if(this.move.x > 0){

                if(this.pos.x + this.size.x > viewSize.x){

                    this.bMoveEnd = true;

                }

            }else{

                if(this.pos.x + this.size.x < 0){

                    this.bMoveEnd = true;

                }

            }

        }

    }

    /**
     * @brief ゴーストの行動処理を行う
     */
    public void ghostBehavior(){

        this.pos.x += this.move.x;

        if(this.pos.x + this.size.x < 0 && this.type == GameElement.ENEMY_BLACKGHOST){

            this.bMoveEnd = true;

        }

    }

    /**
     * @brief 通常の行動処理を行う
     */
    public void behavior1(){

        this.pos.x += this.move.x;

    }

    /**
     * @brief ジャンプする行動処理を行う
     */
    public void behavior2(){

        if(!this.bJump){

            this.move.y = 10f;

            this.bJump = true;

        }

        this.move.y += GameElement.GRAVITY;

        this.pos.add(this.move);

    }

    /**
     * @brief 上下に移動する行動処理を行う
     */
    public void behavior3(){

        if(this.pos.y <= this.startPos.y - 50f || this.pos.y >= this.startPos.y + 50f){

            this.move.y = -this.move.y;

        }

        this.pos.add(this.move);

    }

    /**
     * @brief ボスの行動処理を行う
     */
    public void bossBehavior(){

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        switch (this.count){

            case 0:

                if(!this.bJump){

                    this.move.y = 10f;

                    this.bJump = true;

                }

                this.move.y += GameElement.GRAVITY;

                this.pos.add(this.move);

                if(this.pos.x > viewSize.x){

                    this.time += Gdx.graphics.getDeltaTime();

                    if(this.time > 3){

                        this.time = 0;

                        this.count++;

                        this.pos.x = viewSize.x + 128;

                        this.pos.y = 64;

                        this.bReverse = true;

                        this.bJump = false;

                        this.move.x = -this.move.x;

                    }

                }

                break;

            case 1:

                if(!this.bJump){

                    this.move.y = 20f;

                    this.bJump = true;

                }

                this.move.y += GameElement.GRAVITY;

                this.pos.add(this.move);

                if(this.pos.x < 0){

                    this.time += Gdx.graphics.getDeltaTime();

                    if(this.time > 3){

                        this.time = 0;

                        this.count++;

                        this.pos.x = -128;

                        this.pos.y = 64;

                        this.bReverse = false;

                        this.bJump = false;

                        this.move.x = 10f;

                    }

                }

                break;

            case 2:

                if(!this.bJump){

                    this.move.y = 10f;

                    this.bJump = true;

                }

                this.move.y += GameElement.GRAVITY;

                this.pos.add(this.move);

                if(this.pos.x > viewSize.x){

                    this.time += Gdx.graphics.getDeltaTime();

                    if(this.time > 3){

                        this.bMoveEnd = true;

                    }

                }

                break;

        }

    }

    /**
     * @brief 描画処理を行う
     */
    public void render(){

        if(!this.bShow){

            return;

        }

        //敵の描画
        this.anime.render(this.pos.x, this.pos.y, this.bReverse, false);

    }

    /**
     * @brief 移動終了フラグを取得を行う
     * @return 移動終了している場合はtrue
     */
    public boolean isMoveEnd(){

        return this.bMoveEnd;

    }

}
