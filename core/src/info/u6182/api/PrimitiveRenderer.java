/**
 * @file PrimitiveRenderer.java
 * @brief プリミティブ描画を行うクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import info.u6182.api.primitive.CustomRectangle;

/**
 * @class PrimitiveRenderer
 * @brief プリミティブ描画を行うクラス
 */
public class PrimitiveRenderer extends ShapeRenderer {

    /**
     * @brief コンストラクタ
     * @details ShapeRendererの初期化を行う
     */
    public PrimitiveRenderer(){

        super();

    }

    /**
     * @brief コンストラクタ
     * @param maxVertices 最大頂点数
     * @details ShapeRendererの初期化を行う
     */
    public PrimitiveRenderer(int maxVertices){

        super(maxVertices);

    }

    /**
     * @brief コンストラクタ
     * @param maxVertices 最大頂点数
     * @param defaultShader デフォルトシェーダー
     * @details ShapeRendererの初期化を行う
     */
    public PrimitiveRenderer(int maxVertices, ShaderProgram defaultShader){

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
}
