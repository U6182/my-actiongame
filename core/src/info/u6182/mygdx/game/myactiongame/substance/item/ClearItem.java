/**
 * @file ClearItem.java
 * @brief ゲーム内のクリアアイテムクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.mygdx.game.myactiongame.GameElement;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;

/**
 * @class ClearItem
 * @brief ゲーム内のクリアアイテムクラス
 */
public class ClearItem extends Item {

    //! アニメーション
    private AnimeMotion anime;

    //! ゲームクリアフラグ
    private boolean bClear;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     */
    public ClearItem(CustomSpriteBatch spriteBatch, Texture texture, Vector2 pos, int type){

        super(texture, pos, type);

        //サイズの初期化
        this.size = new Vector2(160f, 120f);

        //ゲームクリアフラグの初期化
        this.bClear = false;

        //名声スコアの初期化
        this.fameScore = GameElement.FAMESCORE_CLEARGOAL;

        AnimeData[] animeData = {new AnimeData(this.item, "ゲームクリア", this.size, true, new int[][]{{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},{0,8},{0,9},{0,10}}, 0, 0, 0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData, spriteBatch);

    }

    /**
     * @brief 更新処理を行う
     * @param deltaTime デルタタイム
     */
    @Override
    public void update(float deltaTime){

        //ボスを倒しゲームクリアしている場合表示
        if(GameElement.getGameElement().getStoryClear() == GameElement.STORY_CLEAR && !this.bClear){

            this.bShow = true;

            this.bClear = false;

        }

        if(!this.bShow){

            return;

        }

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 描画処理を行う
     * @param spriteBatch スプライトバッチ
     */
    @Override
    public void render(CustomSpriteBatch spriteBatch){

        if(!this.bShow){

            return;

        }

        //クリアアイテムの描画
        this.anime.render(this.pos.x, this.pos.y);

    }

}
