/**
 * @file Item.java
 * @brief ゲーム内のアイテムクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.primitive.CustomRectangle;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.substance.Substance;

import info.u6182.api.Utiility;

/**
 * @class Item
 * @brief ゲーム内のアイテムクラス
 */
public class Item extends Substance {

    //! アイテムのテクスチャ
    protected Texture item;

    //! アイテム取得フラグ
    protected boolean bGetItem;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     */
    public Item(Texture texture, Vector2 pos, int type){

        this.item = texture;

        this.rect = new CustomRectangle();

        this.pos = new Vector2(pos);

        this.bGetItem = false;

        this.type = type;

    }

    /**
     * @brief 更新処理を行う
     * @param deltaTime デルタタイム
     */
    public void update(float deltaTime){
        ;
    }

    /**
     * @brief 描画処理を行う
     * @param spriteBatch スプライトバッチ
     */
    public void render(CustomSpriteBatch spriteBatch){

        if(!this.bShow){

            return;

        }

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Vector2 pos = Utiility.getScrollSubPos(this.pos, GameElement.getGameElement().getScroll());

        if(!((pos.x + this.size.x > 0 && pos.x < viewSize.x) && (pos.y + this.size.y > 0 && pos.y < viewSize.y))){

            return;

        }

        //アイテムの描画
        spriteBatch.draw(this.item, this.pos.x, this.pos.y);

    }

    /**
     * @brief アイテム取得フラグの設定を行う
     * @param bGetItem アイテム取得フラグ
     */
    public void setGetItem(boolean bGetItem){

        this.bGetItem = bGetItem;

    }

    /**
     * @brief アイテム取得フラグの取得を行う
     * @return アイテム取得フラグ
     */
    public boolean isGetItem(){

        return this.bGetItem;

    }

}
