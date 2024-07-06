/**
 * @file ScreenEffectManager.java
 * @brief スクリーンエフェクト管理クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.screeneffectmanager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.ScreenElement;

/**
 * @class ScreenEffectManager
 * @brief スクリーンエフェクト管理クラス
 */
public class ScreenEffectManager {

    //! 自身のインスタンス
    private static ScreenEffectManager screenEffectManager = new ScreenEffectManager();

    //! シェイプレンダラー
    private ShapeRenderer renderer = new ShapeRenderer();

    //! 矩形のX座標
    private float rectX = 0f;

    //! 矩形のY座標
    private float rectY = 0f;

    //! 矩形のサイズ
    private float rectSize = 128f;

    /**
     * @brief コンストラクタ
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    private ScreenEffectManager(){

    }

    /**
     * @brief initialize
     * @details 初期化を行う
     */
    public void initialize(){

        this.rectX = 0f;

        this.rectY = 0f;

    }

    /**
     * @brief rectOrderLined
     * @param nextScreen 次のスクリーン
     * @details 矩形の順序を並べる処理を行う
     */
    public void rectOrderLined(int nextScreen){

        if(ScreenElement.getElement().isEffect()){

            Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

            this.rectX += this.rectSize;

            if(this.rectX > viewSize.x + this.rectSize){

                this.rectX = 0f;

                this.rectY += this.rectSize;

                if(this.rectY > viewSize.y){

                    ScreenElement.getElement().setNextScreen(nextScreen);

                }

            }

        }

    }

    /**
     * @brief rectOrderLinedRender
     * @param combined 合成行列
     * @details 矩形の順序を並べる描画を行う
     */
    public void rectOrderLinedRender(Matrix4 combined){

        if(ScreenElement.getElement().isEffect()){

            Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

            this.renderer.begin(ShapeRenderer.ShapeType.Filled);

            this.renderer.setProjectionMatrix(combined);

            this.renderer.setColor(Color.BLACK);

            for(float y = 0;y <= this.rectY;y += this.rectSize){

                for(float x = 0;x < this.rectX;x += this.rectSize){

                    this.renderer.rect(x, (viewSize.y - this.rectSize) - y, this.rectSize, this.rectSize);

                }

            }

            for(float y = 0;y < this.rectY;y += this.rectSize){

                for(float x = 0;x < viewSize.x;x += this.rectSize){

                    this.renderer.rect(x, (viewSize.y - this.rectSize) - y, this.rectSize, this.rectSize);

                }

            }

            this.renderer.end();

        }

    }

    /**
     * @brief release
     * @details リソースの解放を行う
     */
    public void release(){

        this.renderer.dispose();

    }

    /**
     * @brief generateRenderer
     * @details シェイプレンダラーの生成を行う
     */
    public void generateRenderer(){

        this.renderer = new ShapeRenderer();

    }

    /**
     * @brief getScreenEffectManager
     * @return ScreenEffectManager スクリーンエフェクトマネージャのインスタンス
     * @details スクリーンエフェクトマネージャのインスタンスの取得を行う
     */
    public static ScreenEffectManager getScreenEffectManager(){

        return screenEffectManager;

    }

}
