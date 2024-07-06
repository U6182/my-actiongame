/**
 * @file ShapeRenderer.java
 * @brief シェイプ描画を行うクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import info.u6182.api.primitive.CustomRectangle;

/**
 * @class ShapeRenderer
 * @brief シェイプ描画を行うクラス
 */
public class ShapeRenderer extends com.badlogic.gdx.graphics.glutils.ShapeRenderer {

    /**
     * @brief コンストラクタ
     * @details ShapeRendererの初期化を行う
     */
    public ShapeRenderer(){

        super();

    }

    /**
     * @brief コンストラクタ
     * @param maxVertices 最大頂点数
     * @details ShapeRendererの初期化を行う
     */
    public ShapeRenderer(int maxVertices){

        super(maxVertices);

    }

    /**
     * @brief コンストラクタ
     * @param maxVertices 最大頂点数
     * @param defaultShader デフォルトシェーダー
     * @details ShapeRendererの初期化を行う
     */
    public ShapeRenderer(int maxVertices, ShaderProgram defaultShader){

        super(maxVertices, defaultShader);

    }

    /**
     * @brief renderRect
     * @param rect 描画する矩形
     * @details 矩形の描画を行う
     */
    public void renderRect(CustomRectangle rect){

        super.rect(rect.x, rect.y, rect.width, rect.height);

    }

    /**
     * @brief renderRect
     * @param rect 描画する矩形
     * @param color 描画色
     * @details 色を指定して矩形の描画を行う
     */
    public void renderRect(CustomRectangle rect, Color color){

        setColor(color);

        super.rect(rect.x, rect.y, rect.width, rect.height);

    }


}
