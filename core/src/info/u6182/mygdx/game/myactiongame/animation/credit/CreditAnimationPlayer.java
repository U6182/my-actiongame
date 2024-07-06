/**
 * @file CreditAnimationEnemy.java
 * @brief クレジットアニメーションのプレイヤークラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.animation.credit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.anime.Anime;

import info.u6182.api.Utiility;
import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class CreditAnimationPlayer
 * @brief クレジットアニメーションに登場するプレイヤーキャラクターのクラス
 */
public class CreditAnimationPlayer extends Anime {

    //! プレイヤーテクスチャ
    private Texture texture;

    //! アナザーストーリーフラグ
    private boolean bAnotherStory;

    //! 分岐クリアフラグ
    private boolean bBranchClear;

    //! ステージ番号
    private int stageNo;

    //! 往復した数
    private int count;

    //! 時間
    private float time;

    //! 待機アニメーション番号
    protected final int ANIME_WAIT = 0;

    //! 移動アニメーション番号
    protected final int ANIME_MOVE = 1;

    /**
     * @brief コンストラクタ
     */
    public CreditAnimationPlayer(){

        this.pos = new Vector2();

        this.move = new Vector2();

        //分岐クリアフラグの初期化
        this.bBranchClear = false;

        //ステージ番号の初期化
        this.stageNo = 0;

        //往復した数の初期化
        this.count = 0;

        //時間の初期化
        this.time = 0;

    }

    /**
     * @brief プレイヤーのテクスチャの読み込みを行う
     * @param bAnotherStory アナザーストーリーフラグ
     * @param bBranchClear 分岐クリアフラグ
     */
    public void load(boolean bAnotherStory, boolean bBranchClear){

        if(bAnotherStory){

            this.texture = bBranchClear ? TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PUMPKING) : TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_OBSESSIONPUMPKING);

        }else{

            this.texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PLAYER);

        }

        this.bAnotherStory = bAnotherStory;

        this.bBranchClear = bBranchClear;

    }

    /**
     * @brief プレイヤーの初期化を行う
     * @param spriteBatch スプライトバッチ
     * @param stageNo ステージ番号
     */
    public void initialize(CustomSpriteBatch spriteBatch, int stageNo){

        this.size = new Vector2(60f, 64f);

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        if(stageNo % 2 == 0){

            this.pos.x = viewSize.x - this.size.x;

            this.move.x = -5f;

            this.bReverse = true;

        }else{

            this.pos.x = 0f;

            this.move.x = 5f;

            this.bReverse = false;

        }

        this.move.y = 0f;

        this.pos.y = 64f;

        this.stageNo = stageNo;

        this.time = 0;

        this.count = 0;

        if(this.anime == null){

            AnimeData[] animeData = {new AnimeData(texture,"移動", size,true, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0}}, 0,70,0.05f)};

            //アニメーションの初期化
            this.anime = new AnimeMotion(animeData, spriteBatch);

        }

    }

    /**
     * @brief アナザーストーリー用の初期化を行う
     * @param spriteBatch スプライトバッチ
     */
    public void anotherInitialize(CustomSpriteBatch spriteBatch){

        this.size = new Vector2(64f, 64f);

        this.pos.x = Utiility.getScreenCenterX(this.size.x);

        this.pos.y = 64f;

        this.count = 0;

        AnimeData[] animeData = {new AnimeData(texture, "待機", this.size, true, new int[][]{{0,0},{1,0},{2,0}},0,0,0.08f), new AnimeData(texture, "移動", this.size, true, new int[][]{{0,2},{1,2},{2,2}},0,0,0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief プレイヤーの更新処理を行う
     * @param bCreditInterval クレジットインターバルフラグ
     */
    public void update(boolean bCreditInterval){

        //行動処理の更新
        behavior(bCreditInterval);

        //モーションの更新
        changeMotion();

        this.anime.addTime(Gdx.graphics.getDeltaTime());

    }

    /**
     * @brief プレイヤーのモーションを変更する
     */
    public void changeMotion(){

        if(this.move.x != 0){

            if(this.move.x > 0){

                this.bReverse = false;

            }else{

                this.bReverse = true;

            }

            if(this.bAnotherStory){

                this.anime.changeAnime(ANIME_MOVE);

            }

        }else{

            if(this.bAnotherStory){

                this.anime.changeAnime(ANIME_WAIT);

            }

        }

    }

    /**
     * @brief プレイヤーの行動処理を行う
     * @param bCreditInterval クレジットインターバルフラグ
     */
    public void behavior(boolean bCreditInterval){

        if(this.stageNo == GameElement.STAGE6){

            Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

            switch (this.count){

                case 0:

                    this.move.y += GameElement.GRAVITY;

                    this.pos.add(this.move);

                    if(this.pos.x > viewSize.x){

                        this.time += Gdx.graphics.getDeltaTime();

                        if(this.time > 3){

                            this.time = 0;

                            this.count++;

                            this.pos.x = viewSize.x + 64;

                            this.bReverse = true;

                            this.move.x = -this.move.x;

                        }

                    }

                    break;

                case 1:

                    this.move.y += GameElement.GRAVITY;

                    this.pos.add(this.move);

                    if(this.pos.x < 0){

                        this.time += Gdx.graphics.getDeltaTime();

                        if(this.time > 3){

                            this.time = 0;

                            this.count++;

                            this.pos.x = -64f;

                            this.bReverse = false;

                            this.move.x = 10f;

                        }

                    }

                    break;

                case 2:

                    this.move.y += GameElement.GRAVITY;

                    this.pos.add(this.move);

                    break;

            }

        }else{

            if(this.bBranchClear && bCreditInterval && this.count == 0){

                this.count = 1;

                this.move.x = 5f;

            }

            if(this.count != 0){

                if(!this.bJump){

                    this.move.y = 3f;

                    this.bJump = true;

                }

            }

            this.move.y += GameElement.GRAVITY;

            this.pos.add(this.move);

        }

    }

    /**
     * @brief プレイヤーの描画処理を行う
     */
    public void render(){

        //プレイヤーの描画
        this.anime.render(this.pos.x, this.pos.y, this.bReverse, false);

    }

}
