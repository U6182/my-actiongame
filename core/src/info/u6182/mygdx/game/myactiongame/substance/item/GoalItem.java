/**
 * @file GoalItem.java
 * @brief ゲーム内のゴールアイテムクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import info.u6182.mygdx.game.myactiongame.GameElement;

import info.u6182.api.Utiility;
import info.u6182.api.batch.CustomSpriteBatch;

/**
 * @class GoalItem
 * @brief ゲーム内のゴールアイテムクラス
 */
public class GoalItem extends Item {

    //! アルファ値
    protected float alpha;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     */
    public GoalItem(Texture texture, Vector2 pos, int type){

        super(texture, pos, type);

        //サイズの初期化
        this.size = new Vector2(128f, 128f);

        //補正
        this.pos.y -= size.y - 32;

        //表示フラグの初期化
        this.bShow = this.type == GameElement.ITEM_ASSASSINCLEARGOAL ? false : true;

        //アルファ値の初期化
        this.alpha = this.type == GameElement.ITEM_ASSASSINCLEARGOAL ? 0f : 1f;

        //名声スコアの初期化
        switch (this.type){

            case GameElement.ITEM_SILVERGOAL:

                this.fameScore = GameElement.FAMESCORE_SILVERGOAL;

                break;

            case GameElement.ITEM_GOLDGOAL:

                this.fameScore = GameElement.FAMESCORE_GOLDGOAL;

                break;

        }

    }

    /**
     * @brief 更新処理を行う
     * @param deltaTime デルタタイム
     */
    @Override
    public void update(float deltaTime){

        if(this.type != GameElement.ITEM_ASSASSINCLEARGOAL){

            return;

        }

        //ボスを倒してゲームクリアしている場合表示
        if(GameElement.getGameElement().isClearItem() && this.alpha == 0){

            this.alpha = deltaTime;

        }

        if(this.alpha == 0){

            return;

        }

        this.alpha += deltaTime;

        if(this.alpha >= 1){

            this.alpha = 1f;

            this.bShow = true;

        }

    }

    /**
     * @brief 描画処理を行う
     * @param spriteBatch スプライトバッチ
     */
    @Override
    public void render(CustomSpriteBatch spriteBatch){

        if(this.type != GameElement.ITEM_ASSASSINCLEARGOAL && !this.bShow){

            return;

        }else if(this.type == GameElement.ITEM_ASSASSINCLEARGOAL && this.alpha == 0){

            return;

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

        if(!((pos.x + this.size.x > 0 && pos.x < viewSize.x) && (pos.y + this.size.y > 0 && pos.y < viewSize.y))){

            return;

        }

        spriteBatch.setColor(1,1,1, this.alpha);

        //ゴールアイテムの描画
        spriteBatch.draw(this.item, this.pos.x, this.pos.y);

        spriteBatch.setColor(1,1,1,1);

    }
}
