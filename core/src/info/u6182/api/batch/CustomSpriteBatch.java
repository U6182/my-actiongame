/**
 * @file CustomSpriteBatch.java
 * @brief スプライトバッチを拡張するクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.batch;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * @class CustomSpriteBatch
 * @brief スプライトバッチを拡張するクラス
 */
public class CustomSpriteBatch extends SpriteBatch {

    /**
     * @brief コンストラクタ
     */
    public CustomSpriteBatch(){

        super();

    }

    /**
     * @brief コンストラクタ
     * @param size バッチのサイズ
     */
    public CustomSpriteBatch(int size){

        super(size);

    }

    /**
     * @brief コンストラクタ
     * @param size バッチのサイズ
     * @param defaultShader デフォルトシェーダー
     */
    public CustomSpriteBatch(int size, ShaderProgram defaultShader){

        super(size, defaultShader);

    }

    /**
     * @brief テクスチャの描画を行う
     * @param texture 描画するテクスチャ
     * @param x 描画位置のX座標
     * @param y 描画位置のY座標
     * @param bWidthReverse 横反転フラグ
     * @param bHeightReverse 縦反転フラグ
     */
    public void draw(Texture texture, float x, float y, boolean bWidthReverse, boolean bHeightReverse){

        float sizeX = texture.getWidth();
        float sizeY = texture.getHeight();

        float width = bWidthReverse ? -sizeX : sizeX;

        float offsetX = bWidthReverse ? -width : 0;

        float height = bHeightReverse ? -sizeY : sizeY;

        float offsetY = bHeightReverse ? -height : 0;

        super.draw(texture, x + offsetX, y + offsetY, width, height);

    }

}
